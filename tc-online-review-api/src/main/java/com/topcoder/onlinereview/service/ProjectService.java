package com.topcoder.onlinereview.service;

import com.topcoder.onlinereview.dto.ProjectCategoryDto;
import com.topcoder.onlinereview.dto.ProjectDto;
import com.topcoder.onlinereview.dto.ProjectStatusDto;
import com.topcoder.onlinereview.dto.ProjectTypeDto;
import com.topcoder.onlinereview.entity.ids.ProjectCategory;
import com.topcoder.onlinereview.entity.ids.ProjectInfoType;
import com.topcoder.onlinereview.entity.ids.ProjectStatus;
import com.topcoder.onlinereview.entity.ids.ProjectType;
import com.topcoder.onlinereview.repository.ids.ProjectCategoryRepository;
import com.topcoder.onlinereview.repository.ids.ProjectInfoTypeRepository;
import com.topcoder.onlinereview.repository.ids.ProjectStatusRepository;
import com.topcoder.onlinereview.repository.ids.ProjectTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.topcoder.onlinereview.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.util.Constants.QUERY_COMMAND_LIST_PROJECT;
import static com.topcoder.onlinereview.util.Constants.QUERY_COMMAND_LIST_PROJECT_INFO;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class ProjectService {
  @Autowired private ProjectStatusRepository projectStatusRepository;
  @Autowired private ProjectInfoTypeRepository projectInfoTypeRepository;
  @Autowired private ProjectCategoryRepository projectCategoryRepository;
  @Autowired private ProjectTypeRepository projectTypeRepository;
  @Autowired private QueryService queryService;

  public List<ProjectDto> getProjects(String stid) {
    var projectStatus =
        projectStatusRepository.findAll().stream().collect(toMap(ProjectStatus::getId, p -> p));
    var projectInfoType =
        projectInfoTypeRepository.findAll().stream().collect(toMap(ProjectInfoType::getId, p -> p));
    var projectCategory =
        projectCategoryRepository.findAll().stream().collect(toMap(ProjectCategory::getId, p -> p));
    var projectType =
        projectTypeRepository.findAll().stream().collect(toMap(ProjectType::getId, p -> p));
    var queryParams = new HashMap<String, String>();
    queryParams.put("stid", stid);
    var projectRaw = queryService.executeCommand(QUERY_COMMAND_LIST_PROJECT, queryParams);
    var projectProperties =
        projectRaw.get(QUERY_COMMAND_LIST_PROJECT_INFO).stream()
            .collect(groupingBy(m -> getLong(m, "project_id")));
    var projects =
        projectRaw.get(QUERY_COMMAND_LIST_PROJECT).stream()
            .map(
                m -> {
                  var category = projectCategory.get(getLong(m, "project_category_id"));
                  var status = projectStatus.get(getLong(m, "project_status_id"));
                  var type = projectType.get(category.getProjectTypeId());
                  var properties = projectProperties.get(getLong(m, "project_id"));
                  var dto = new ProjectDto();
                  dto.setProjectId(getLong(m, "project_id"));
                  dto.setProjectStatus(new ProjectStatusDto(status));
                  dto.setProjectCategory(new ProjectCategoryDto(category));
                  dto.setProjectType(new ProjectTypeDto(type));
                  dto.setCreationDate((Date) m.get("create_date"));
                  dto.setCreateUser(m.get("create_user").toString());
                  dto.setModifyDate((Date) m.get("modify_date"));
                  dto.setModifyUser(m.get("modify_user").toString());
                  dto.setProperties(
                      properties.stream()
                          .collect(
                              toMap(
                                  p ->
                                      projectInfoType
                                          .get(getLong(p, "project_info_type_id"))
                                          .getName(),
                                  p -> p.get("value").toString())));
                  return dto;
                })
            .collect(toList());
    return projects;
  }
}
