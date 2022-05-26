/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.contest;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Represents the bass entity class for contest eligibility.
 *
 * <p><strong>Thread Safety</strong>: This class is not thread safe since it is mutable.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@Data
@Entity
@Table(name = "contest_eligibility")
public class ContestEligibility implements Serializable {

  /** The serial version UID. */
  private static final long serialVersionUID = -7716443412438952646L;

  /** Represents the id of the entity. */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(
      name = "CONTEST_ELIGIBILITY_SEQ",
      sequenceName = "CONTEST_ELIGIBILITY_SEQ",
      allocationSize = 1)
  @Column(name = "contest_eligibility_id")
  private Long id;

  /** Represents the id of the contest. */
  @Column(name = "contest_id")
  private Long contestId;

  /** Represents the flag to indicate whether it is used for studio. */
  @Column(name = "is_studio")
  private Boolean studio;

  /** It is a non-persistent flag to indicate a 'to be deleted' eligibility. */
  @Transient private boolean deleted;
}
