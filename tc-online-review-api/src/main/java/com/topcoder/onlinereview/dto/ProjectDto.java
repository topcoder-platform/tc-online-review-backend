package com.topcoder.onlinereview.dto;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ProjectDto {
  private Long projectId;
  private long id;
  private ProjectCategoryDto projectCategory;
  private ProjectStatusDto projectStatus;
  private ProjectTypeDto projectType;
  private Map<String, String> properties;
  private Date creationDate;
  private String createUser;
  private Date modifyDate;
  private String modifyUser;
}
