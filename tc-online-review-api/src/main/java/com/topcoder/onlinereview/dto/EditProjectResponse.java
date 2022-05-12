package com.topcoder.onlinereview.dto;

import com.topcoder.onlinereview.component.or.model.DefaultScorecard;
import com.topcoder.onlinereview.component.project.management.Project;
import com.topcoder.onlinereview.component.project.management.ProjectCategory;
import com.topcoder.onlinereview.component.project.management.ProjectStatus;
import com.topcoder.onlinereview.component.project.management.ProjectType;
import com.topcoder.onlinereview.component.project.phase.PhaseType;
import com.topcoder.onlinereview.component.resource.ResourceRole;
import com.topcoder.onlinereview.component.scorecard.Scorecard;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class EditProjectResponse {
  private Boolean arePhaseDependenciesEditable;
  private Boolean newProject;
  private Boolean isAdmin;
  private ProjectStatus[] projectStatuses;
  private ProjectType[] projectTypes;
  private ProjectCategory[] projectCategories;
  private Project project;
  private Map<String, String> modelValues;
  private Map<String, ArrayList<String>> modelArrayValues;
  private ResourceRole[] resourceRoles;
  private Set<String> disabledResourceRoles;
  private List<ResourceRole> allowedResourceRoles;
  private PhaseType[] phaseTypes;
  private Scorecard[] screeningScorecards ;
  private Scorecard[] reviewScorecards ;
  private Scorecard[] approvalScorecards ;
  private Scorecard[] postMortemScorecards;
  private Scorecard[] specificationReviewScorecards;
  private Scorecard[] checkpointScreeningScorecards;
  private Scorecard[] checkpointReviewScorecards;
  private Scorecard[] iterativeReviewScorecards;
  private List<DefaultScorecard> defaultScorecards;
  private List<String> phaseTemplateNames;
  private Map<Integer, Boolean> trueSubmitters;
  private Map<Integer, Boolean> trueReviewers;
  private Map<Long, Boolean> resourcePaid;

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
