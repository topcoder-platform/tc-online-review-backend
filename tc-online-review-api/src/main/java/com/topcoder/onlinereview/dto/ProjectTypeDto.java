package com.topcoder.onlinereview.dto;

import com.topcoder.onlinereview.entity.ids.ProjectType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectTypeDto {
  private long id;
  private String name;
  private String description;
  private boolean generic;

  public ProjectTypeDto(ProjectType type) {
    this.id = type.getId();
    this.name = type.getName();
    this.description = type.getDescription();
    this.generic = type.getGeneric();
  }
}
