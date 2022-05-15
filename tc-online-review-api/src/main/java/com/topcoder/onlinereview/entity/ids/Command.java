package com.topcoder.onlinereview.entity.ids;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "command")
public class Command {
  @Id
  @Column(name = "command_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "command_desc")
  private String commandDesc;

  @Column(name = "command_group_id")
  private String commandGroupId;
}
