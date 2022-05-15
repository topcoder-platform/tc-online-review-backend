package com.topcoder.onlinereview.dto;

import com.topcoder.onlinereview.entity.ids.ProjectCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectCategoryDto {
  private long id;
  private String name;
  private String description;

  public ProjectCategoryDto(ProjectCategory category) {
    this.id = category.getId();
    this.name = category.getName();
    this.description = category.getDescription();
  }
}
