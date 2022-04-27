package com.topcoder.onlinereview.service;

import com.topcoder.onlinereview.entity.ids.Command;
import com.topcoder.onlinereview.entity.ids.CommandQueryXref;
import com.topcoder.onlinereview.entity.ids.Input;
import com.topcoder.onlinereview.entity.ids.QueryInputXref;
import com.topcoder.onlinereview.repository.ids.CommandQueryXrefRepository;
import com.topcoder.onlinereview.repository.ids.CommandRepository;
import com.topcoder.onlinereview.repository.ids.InputRepository;
import com.topcoder.onlinereview.repository.ids.QueryInputXrefRepository;
import com.topcoder.onlinereview.repository.ids.QueryRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.util.Constants.DATE_FORMAT;
import static com.topcoder.onlinereview.util.Constants.DATE_INPUT_TYPE;
import static com.topcoder.onlinereview.util.Constants.DECIMAL_INPUT_TYPE;
import static com.topcoder.onlinereview.util.Constants.INPUT_DELIMITER;
import static com.topcoder.onlinereview.util.Constants.INTEGER_INPUT_TYPE;
import static com.topcoder.onlinereview.util.Constants.SORT_DIRECTION_INPUT_TYPE;
import static com.topcoder.onlinereview.util.Constants.SPECIAL_DEFAULT_MARKER;
import static com.topcoder.onlinereview.util.Constants.STRING_INPUT_TYPE;
import static java.lang.String.join;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class QueryService {
  @Autowired private QueryRepository queryRepository;
  @Autowired private QueryInputXrefRepository queryInputXrefRepository;
  @Autowired private CommandRepository commandRepository;
  @Autowired private CommandQueryXrefRepository commandQueryXrefRepository;
  @Autowired private InputRepository inputRepository;

  @Autowired
  @Qualifier("idsEntityManager")
  private EntityManager entityManager;

  public HashMap<String, List<Map<String, Object>>> executeCommand(
      String commandDesc, Map<String, String> inputMap) {
    var res = new HashMap<String, List<Map<String, Object>>>();
    var commandIds =
        commandRepository.findAllByCommandDesc(commandDesc).stream()
            .map(Command::getId)
            .collect(toList());
    var queryIds =
        commandQueryXrefRepository.findAllByCommandIdIn(commandIds).stream()
            .sorted(comparingLong(x -> Optional.ofNullable(x.getSortOrder()).orElse(0L)))
            .map(CommandQueryXref::getQueryId)
            .collect(toList());
    var queries = queryRepository.findAllById(queryIds);
    var queryInputRes =
        queryInputXrefRepository.findAllByQueryIdIn(queryIds).stream()
            .collect(groupingBy(QueryInputXref::getQueryId));
    var inputs =
        inputRepository
            .findAllById(
                queryInputRes.values().stream()
                    .flatMap(x -> x.stream().map(QueryInputXref::getInputId))
                    .distinct()
                    .collect(toList()))
            .stream()
            .collect(toMap(Input::getId, i -> i));
    for (var query : queries) {
      var querySql = query.getText();
      var queryParams =
          queryInputRes.getOrDefault(query.getId(), newArrayList()).stream()
              .sorted(comparingLong(x -> Optional.ofNullable(x.getSortOrder()).orElse(0L)))
              .collect(toList());
      for (var param : queryParams) {
        var inputEntity = inputs.get(param.getInputId());
        var code = inputEntity.getInputCode();
        var value = getInputValue(inputEntity, param, inputMap);
        querySql = querySql.replaceAll(join(code, INPUT_DELIMITER, INPUT_DELIMITER), value);
      }
      var nativeQuery = entityManager.createNativeQuery(querySql).unwrap(Query.class);
      nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
      List<Map<String, Object>> result = nativeQuery.getResultList();
      res.put(query.getName(), result);
    }
    return res;
  }

  private String getInputValue(
          Input input, QueryInputXref inputXref, Map<String, String> inputMap) {
    var value = inputMap.get(input.getInputCode());
    if (value == null) {
      if (inputXref.getOptional()) {
        var defaultValue = inputXref.getDefaultValue();
        if (defaultValue.startsWith(SPECIAL_DEFAULT_MARKER)) {
          value = runDefaultInputQuery(defaultValue, inputMap);
        } else {
          value = defaultValue;
        }
        inputMap.put(input.getInputCode(), value);
      } else {
        throw new RuntimeException("Missing required query input: " + input.getInputCode());
      }
    }
    // validate value
    value = value.trim();
    if (input.getDataTypeId().equals(STRING_INPUT_TYPE)) {
      return value.replace("'", "''");
    } else if (input.getDataTypeId().equals(INTEGER_INPUT_TYPE)) {
      if (StringUtils.isNumeric(value)) {
        return value;
      }
    } else if (input.getDataTypeId().equals(DECIMAL_INPUT_TYPE)) {
      if (StringUtils.isNumeric(value)) {
        return value;
      }
    } else if (input.getDataTypeId().equals(DATE_INPUT_TYPE)) {
      try {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        sdf.parse(value, new ParsePosition(0));
        return value;
      } catch (Exception e) {
      }
    } else if (input.getDataTypeId().equals(SORT_DIRECTION_INPUT_TYPE)) {
      if (value.equalsIgnoreCase("asc") || value.equalsIgnoreCase("desc")) {
        return value;
      }
    }
    throw new RuntimeException("Invalid data for input " + input.getInputCode() + ": " + value);
  }

  private String runDefaultInputQuery(String value, Map<String, String> inputMap) {
    if (inputMap.containsKey(value)) {
      return inputMap.get(value);
    }
    var query =
        queryRepository
            .findById(Long.parseLong(value.substring(1)))
            .orElseThrow(
                () ->
                    new RuntimeException("Query text for query ID " + value + " missing from DB"));
    var specialQuery = query.getText();
    while (specialQuery.indexOf(INPUT_DELIMITER) >= 0) {
      int start = specialQuery.indexOf(INPUT_DELIMITER);
      int end = specialQuery.indexOf(INPUT_DELIMITER, start + 1);
      if (end < 0) {
        throw new RuntimeException("Unterminated input in default input query " + value);
      }
      var inputCode = specialQuery.substring(start + 1, end);
      String inputValue = inputMap.get(inputCode);
      if (inputValue == null) {
        throw new RuntimeException(
            "Missing required input " + inputCode + " for default input query " + value);
      }
      String oldStr = specialQuery.substring(start, end + 1);
      specialQuery = specialQuery.replaceAll(oldStr, inputValue);
    }

    var result = entityManager.createNativeQuery(specialQuery).getResultList();
    if (result == null || result.isEmpty()) {
      throw new RuntimeException("Default input query " + value + " did not return a value");
    }
    if (result.get(0) instanceof Object[]) {
      inputMap.put(value, ((Object[]) result.get(0))[0].toString());
    } else {
      inputMap.put(value, result.get(0).toString());
    }
    return inputMap.get(value);
  }
}
