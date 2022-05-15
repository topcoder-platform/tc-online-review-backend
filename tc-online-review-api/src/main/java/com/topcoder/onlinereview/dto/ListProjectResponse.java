package com.topcoder.onlinereview.dto;

import com.topcoder.onlinereview.component.deliverable.Deliverable;
import com.topcoder.onlinereview.component.project.management.Project;
import com.topcoder.onlinereview.component.project.management.ProjectCategory;
import com.topcoder.onlinereview.component.project.management.ProjectType;
import com.topcoder.onlinereview.component.project.phase.Phase;
import lombok.Data;

import java.util.Date;

@Data
public class ListProjectResponse {
  private ProjectType[] projectTypes;
  private ProjectCategory[] projectCategories;
  private Deliverable[] allMyDeliverables;
  private Project[][] projects;
  private Phase[][][] phases;
  private Date[][] phaseEndDates;
  private Date[][] projectEndDates;
  private int[] typeCounts;
  private int[] categoryCounts;
  private int totalProjectsCount;
  private String[][] myRoles;
}
