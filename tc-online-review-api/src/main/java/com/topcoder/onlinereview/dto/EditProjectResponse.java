package com.topcoder.onlinereview.dto;

import com.topcoder.onlinereview.component.or.model.ClientProject;
import com.topcoder.onlinereview.component.or.model.CockpitProject;
import com.topcoder.onlinereview.component.or.model.DefaultScorecard;
import com.topcoder.onlinereview.component.or.model.PhaseGroup;
import com.topcoder.onlinereview.component.project.management.Project;
import com.topcoder.onlinereview.component.project.management.ProjectCategory;
import com.topcoder.onlinereview.component.project.management.ProjectStatus;
import com.topcoder.onlinereview.component.project.management.ProjectType;
import com.topcoder.onlinereview.component.project.payment.ProjectPayment;
import com.topcoder.onlinereview.component.project.phase.PhaseType;
import com.topcoder.onlinereview.component.resource.ResourceRole;
import com.topcoder.onlinereview.component.scorecard.Scorecard;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
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
  private Scorecard[] screeningScorecards;
  private Scorecard[] reviewScorecards;
  private Scorecard[] approvalScorecards;
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
  private int[] phaseGroupIndexes;
  private PhaseGroup[] phaseGroups;
  private Integer activeTabIdx;
  private Boolean isManager;
  private Boolean isAllowedToPerformScreening;
  private Boolean isAllowedToPerformCheckpointScreening;
  private Boolean isAllowedToPerformCheckpointReview;
  private Boolean isAllowedToViewScreening;
  private Boolean isAllowedToViewCheckpointScreening;
  private Boolean isAllowedToViewCheckpointReview;
  private Boolean isAllowedToUploadTC;
  private Boolean isAllowedToPerformAggregation;
  private Boolean isAllowedToPerformAggregationReview;
  private Boolean isAllowedToUploadFF;
  private Boolean isAllowedToPerformFinalReview;
  private Boolean isAllowedToPerformApproval;
  private Boolean isAllowedToPerformPortMortemReview;
  private Boolean canEditContestPrize;
  private Boolean canEditCheckpointPrize;
  private Boolean allowCockpitProjectEdit;
  private Boolean allowBillingEdit;
  private List<ProjectPayment> allPayments;
  private List<ClientProject> billingProjects;
  private List<CockpitProject> cockpitProjects;

  public void set(String key, Object value) {
    if (modelValues == null) {
      modelValues = new HashMap<>();
    }
    if (value != null) {
      modelValues.put(key, value.toString());
    }
  }

  public void set(String key, int index, Object value) {
    if (modelArrayValues == null) {
      modelArrayValues = new HashMap<>();
    }
    if (!modelArrayValues.containsKey(key)) {
      modelArrayValues.put(key, new ArrayList<>());
    }
    if (value != null) {
      if (index > modelArrayValues.get(key).size()) {
        modelArrayValues.get(key).add(value.toString());
      } else {
        modelArrayValues.get(key).add(index, value.toString());
      }
    }
  }
}
