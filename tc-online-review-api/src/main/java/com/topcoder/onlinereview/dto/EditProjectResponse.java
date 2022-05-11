package com.topcoder.onlinereview.dto;

import com.topcoder.onlinereview.component.project.management.Project;
import com.topcoder.onlinereview.component.project.management.ProjectStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@Data
public class EditProjectResponse {
  private Boolean arePhaseDependenciesEditable;
  private Boolean newProject;
  private ProjectStatus[] projectStatuses;
  private Project project;
  private Map<String, String> modelValues;
  private Map<String, ArrayList<String>> modelArrayValues;

  public void set(String key, Object value) {
    modelValues.put(key, value.toString());
  }

  public void set(String key, int index, Object value) {
    if (!modelArrayValues.containsKey(key)) {
      modelArrayValues.put(key, new ArrayList<>());
    }
    modelArrayValues.get(key).set(index, value.toString());
  }
}
