package com.topcoder.onlinereview.entity.ids;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "project_category_lu")
public class ProjectCategory {
  @Id
  @Column(name = "project_category_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "project_type_id")
  private Long projectTypeId;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "create_user")
  private String createUser;

  @Column(name = "create_date")
  private Date createDate;

  @Column(name = "modify_user")
  private String modifyUser;

  @Column(name = "modify_date")
  private Date modifyDate;

  @Column(name = "display")
  private Boolean display;

  @Column(name = "display_order")
  private Long displayOrder;

  @Column(name = "project_catalog_id")
  private Long projectCatalogId;

  @Column(name = "version")
  private Double version;
}
