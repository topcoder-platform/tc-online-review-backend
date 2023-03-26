/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.contest;

import lombok.Data;

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
public class ContestEligibility implements Serializable {

  /** The serial version UID. */
  private static final long serialVersionUID = -7716443412438952646L;

  /** Represents the id of the entity. */
  private Long id;

  /** Represents the id of the contest. */
  private Long contestId;

  /** Represents the flag to indicate whether it is used for studio. */
  private Boolean studio;

  /** It is a non-persistent flag to indicate a 'to be deleted' eligibility. */
  private boolean deleted;
}
