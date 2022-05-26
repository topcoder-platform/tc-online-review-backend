/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.payment;

import com.topcoder.onlinereview.component.exception.ExceptionData;

/**
 * This exception is thrown by implementations of persistence interfaces when error ocuurs while
 * accessing the persistence.
 *
 * <p><strong>Thread Safety: </strong> This class is not thread safe because its base class is not
 * thread safe.
 *
 * @author maksimilian, sparemax
 * @version 1.0
 */
public class ProjectPaymentManagementPersistenceException
    extends ProjectPaymentManagementException {
  /** The serial version ID. */
  private static final long serialVersionUID = 6531347059055353714L;

  /**
   * Constructor with error message.
   *
   * @param message the explanation of the error. Can be empty string or <code>null</code> (useless,
   *     but allowed).
   */
  public ProjectPaymentManagementPersistenceException(String message) {
    super(message);
  }

  /**
   * Constructor with error message and inner cause.
   *
   * @param message the explanation of the error. Can be empty string or <code>null</code> (useless,
   *     but allowed).
   * @param cause the underlying cause of the error. Can be <code>null</code>, which means that
   *     initial exception is nonexistent or unknown.
   */
  public ProjectPaymentManagementPersistenceException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor with error message and exception data.
   *
   * @param message the explanation of the error. Can be empty string or <code>null</code> (useless,
   *     but allowed).
   * @param data the additional data to attach to the exception. If this is <code>null</code>, a new
   *     ExceptionData() will be automatically used instead.
   */
  public ProjectPaymentManagementPersistenceException(String message, ExceptionData data) {
    super(message, data);
  }

  /**
   * Constructor with error message, inner cause and exception data.
   *
   * @param message the explanation of the error. Can be empty string or <code>null</code> (useless,
   *     but allowed).
   * @param cause the underlying cause of the error. Can be <code>null</code>, which means that
   *     initial exception is nonexistent or unknown.
   * @param data the additional data to attach to the exception. If this is <code>null</code>, a new
   *     ExceptionData() will be automatically used instead.
   */
  public ProjectPaymentManagementPersistenceException(
      String message, Throwable cause, ExceptionData data) {
    super(message, cause, data);
  }
}
