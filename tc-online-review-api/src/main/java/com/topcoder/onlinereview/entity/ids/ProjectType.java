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
@Table(name = "project_type_lu")
public class ProjectType {
  @Id
  @Column(name = "project_type_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "is_generic")
  private Boolean generic;

  @Column(name = "create_user")
  private String createUser;

  @Column(name = "create_date")
  private Date createDate;

  @Column(name = "modify_user")
  private String modifyUser;

  @Column(name = "modify_date")
  private Date modifyDate;

  @Column(name = "version")
  private Double version;
}
