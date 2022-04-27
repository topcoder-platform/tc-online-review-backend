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
@Table(name = "input_lu")
public class Input {
  @Id
  @Column(name = "input_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "input_code")
  private String inputCode;

  @Column(name = "data_type_id")
  private Long dataTypeId;

  @Column(name = "input_desc")
  private String inputDesc;
}
