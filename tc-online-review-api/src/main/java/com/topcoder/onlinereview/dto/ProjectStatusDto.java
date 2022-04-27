package com.topcoder.onlinereview.dto;

import com.topcoder.onlinereview.entity.ids.ProjectStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectStatusDto {
  private long id;
  private String name;
  private String description;

  public ProjectStatusDto(ProjectStatus status) {
    this.id = status.getId();
    this.name = status.getName();
    this.description = status.getDescription();
  }
}
