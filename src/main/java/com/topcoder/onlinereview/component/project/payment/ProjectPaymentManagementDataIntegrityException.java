/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.payment;

import com.topcoder.onlinereview.component.exception.ExceptionData;

/**
 * This exception is thrown if project payment integrity is broken, for example if project payment
 * is being persisted with payment type that is not present in DB.
 *
 * <p><strong>Thread Safety: </strong> This class is not thread safe because its base class is not
 * thread safe.
 *
 * @author maksimilian, sparemax
 * @version 1.0
 */
public class ProjectPaymentManagementDataIntegrityException
    extends ProjectPaymentManagementException {
  /** The serial version ID. */
  private static final long serialVersionUID = 4654922313478025652L;

  /**
   * Constructor with error message.
   *
   * @param message the explanation of the error. Can be empty string or <code>null</code> (useless,
   *     but allowed).
   */
  public ProjectPaymentManagementDataIntegrityException(String message) {
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
  public ProjectPaymentManagementDataIntegrityException(String message, Throwable cause) {
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
  public ProjectPaymentManagementDataIntegrityException(String message, ExceptionData data) {
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
  public ProjectPaymentManagementDataIntegrityException(
      String message, Throwable cause, ExceptionData data) {
    super(message, cause, data);
  }
}
