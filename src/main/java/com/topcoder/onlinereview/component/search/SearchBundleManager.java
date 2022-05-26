/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.search;

import com.topcoder.onlinereview.component.datavalidator.NotValidator;
import com.topcoder.onlinereview.component.datavalidator.NullValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * This class manages a collection of SearchBundle objects. It provides APIs to allow user
 * application to manage the collection of SearchBundle objects.. Including set the collection of
 * SearchBundle,remove one SearchBundle,add one SearchBundle. Also clear the SearchBundles.The
 * SearchBundles in the manager in initialized in the constructor, in which all the SearchBundles
 * are loaded from the configration.
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.3
 */
@Component
public class SearchBundleManager {
  private Map<String, SearchBundle> searchBundleMap = new HashMap<>();
  @Autowired private SearchBundleConfig searchBundleConfigList;
  @Autowired private SearchStrategy searchStrategy;

  @PostConstruct
  public void postRun() {
    for (SearchBundleConfig.ConfigData sbc : searchBundleConfigList.getData()) {
      searchBundleMap.put(
          sbc.getName(),
          new SearchBundle(
              sbc.getName(),
              sbc.getFields().stream()
                  .collect(toMap(k -> k, k -> new NotValidator(new NullValidator()))),
              sbc.getAlias(),
              sbc.getContext(),
              searchStrategy));
    }
  }
  /**
   * Get the SearchBundle object with given name via the searchBundleMap.
   *
   * @param name the name of BundleSearch intended to retrieve
   * @return SearchBundle object
   * @throws IllegalArgumentException if any parameter is Null
   * @throws IllegalArgumentException if any parameter is empty
   */
  public synchronized SearchBundle getSearchBundle(String name) {
    if (name == null) {
      throw new IllegalArgumentException("The name should not be null to getSearchBundle.");
    }

    if (name.trim().length() == 0) {
      throw new IllegalArgumentException("The name should not be empty to getSearchBundle.");
    }

    return searchBundleMap.get(name);
  }

  /**
   * Get all the names of the SearchBundles in the manager. return as a list format.
   *
   * @return a list containing names of all SearchBundle objects
   */
  public synchronized List<String> getSearchBundleNames() {
    return new ArrayList(searchBundleMap.keySet());
  }
}
