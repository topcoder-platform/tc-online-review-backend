package com.topcoder.onlinereview.entity.oltp;

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
@Table(name = "project")
public class Project {
  @Id
  @Column(name = "project_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "project_desc")
  private String projectDesc;

  @Column(name = "start_date")
  private Date startDate;

  @Column(name = "deadline_date")
  private Date deadlineDate;

  @Column(name = "status_id")
  private Integer statusId;

  @Column(name = "creation_date")
  private Date creationDate;

  @Column(name = "modify_date")
  private Date modifyDate;

  @Column(name = "completion_date")
  private Date completionDate;

  @Column(name = "tech_lead")
  private Long techLead;
}
