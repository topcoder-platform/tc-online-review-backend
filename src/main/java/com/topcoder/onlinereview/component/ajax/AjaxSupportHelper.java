/*
 * Copyright (C) 2006-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.topcoder.onlinereview.component.util.SpringUtils.getBean;

/**
 * <p>
 * A helper class for this component.
 * </p>
 *
 * It will do the following things:
 * <ul>
 * <li> Create object factory. </li>
 * <li> Write an ajax response. </li>
 * </ul>
 *
 * <strong>Thread safety:</strong>
 * This class has no state, so it is thread safe.
 *
 * @author topgear
 * @author assistant
 * @version 1.1
 */
public final class AjaxSupportHelper {

	/**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(AjaxSupportHelper.class.getName());
    
    /**
     * Represents the data format.
     */
    private static final String DATE_FORMAT = "MM.dd.yyyy h:mm a";

    /**
     * Make this class uninstantiatable.
     */
    private AjaxSupportHelper() {
        // do nothing
    }

    /**
     * Create an object with type.
     *
     * @param type the type of the object
     * @return the object created
     * @throws ConfigurationException if any error happens
     */
    public static Object createObject(Class type) throws ConfigurationException {
        return getBean(type);
    }

    /**
     * Response and log the error.
     *
     * @param type the response type
     * @param status the response status
     * @param message the error message
     * @param response the http servlet response object
     * @throws IOException if error happens when writing the response
     * @throws IllegalArgumentException if any parameter is null(except message) or type/status is empty
     */
    public static void responseAndLogError(String type, String status, String message, HttpServletResponse response)
        throws IOException {
        responseAndLogError(type, status, message, response, null);
    }

    /**
     * Response and log the error.
     *
     * @param type the response type
     * @param status the response status
     * @param message the error message
     * @param response the http servlet response object
     * @param error the original cause of error. May be null
     * @throws IOException if error happens when writing the response
     * @throws IllegalArgumentException if any parameter is null(except message) or type/status is empty
     */
    public static void responseAndLogError(String type, String status, String message, HttpServletResponse response,
                                           Throwable error)
        throws IOException {
        if (response == null) {
            throw new IllegalArgumentException("The response should not be null.");
        }

        AjaxResponse resp = createAndLogError(type, status, message, null, error);

        // response it
        doResponse(response, resp);
    }

    /**
     * Create the response and log the error.
     *
     * @param type the response type
     * @param status the response status
     * @param message the error message
     * @param misc misc message
     * @return The created ajax response object
     * @throws IllegalArgumentException if type/status is null/empty
     */
    public static AjaxResponse createAndLogError(String type, String status, String message, Object misc) {
        return createAndLogError(type, status, message, misc, null);
    }

    /**
     * Create the response and log the error.
     *
     * @param type the response type
     * @param status the response status
     * @param message the error message
     * @param misc misc message
     * @param error the original cause or error. May be null
     * @return The created ajax response object
     * @throws IllegalArgumentException if type/status is null/empty
     */
    public static AjaxResponse createAndLogError(String type, String status, String message, Object misc,
                                                 Throwable error) {
        if (type == null) {
            throw new IllegalArgumentException("The type should not be null.");
        }
        if (type.trim().length() == 0) {
            throw new IllegalArgumentException("The type should not be empty.");
        }
        if (status == null) {
            throw new IllegalArgumentException("The status should not be null.");
        }
        if (status.trim().length() == 0) {
            throw new IllegalArgumentException("The status should not be empty.");
        }

        // create the response object
        AjaxResponse resp = new AjaxResponse(type, status, null);

        // log it
        logger.error(type + " : " + status);
        logger.error(type + " : " + message);
        if (misc != null) {
            logger.error(type + " : " + misc);
        }
        // ISV : Log the exception stack trace if it is provided
        if (error != null) {
            StringWriter sw = new StringWriter();
            error.printStackTrace(new PrintWriter(sw));
            logger.error(type + " : Exception : \n" + sw.getBuffer().toString());
        }
        return resp;
    }

    /**
     * Create the response and log the error.
     *
     * @param type the response type
     * @param status the response status
     * @param message the error message
     * @param data the data to return to the client
     * @param misc misc message
     * @return The created ajax response object
     * @throws IllegalArgumentException if type/status is null/empty
     */
    public static AjaxResponse createAndLogSuccess(String type, String status,
            String message, Object data, Object misc) {
        if (type == null) {
            throw new IllegalArgumentException("The type should not be null.");
        }
        if (type.trim().length() == 0) {
            throw new IllegalArgumentException("The type should not be empty.");
        }
        if (status == null) {
            throw new IllegalArgumentException("The status should not be null.");
        }
        if (status.trim().length() == 0) {
            throw new IllegalArgumentException("The status should not be empty.");
        }

        // create the response object
        AjaxResponse resp = new AjaxResponse(type, status, data);

        // log it
        logger.info(type + " : " + status);
        logger.info(type + " : " + message);
        logger.info(type + " : " + misc);

        return resp;
    }

    /**
     * Write the ajax response back to the client.
     *
     * @param response the http response object
     * @param ajax the ajax response object
     * @throws IOException if error happens when writing the response
     */
    public static void doResponse(HttpServletResponse response, AjaxResponse ajax) throws IOException {
        if (response == null) {
            throw new IllegalArgumentException("The response should not be null.");
        }
        if (ajax == null) {
            throw new IllegalArgumentException("The ajax response should not be null.");
        }

        // Set the content type of the response parameter to "text/xml"
        response.setContentType("text/xml");

        // Get the character writer from the response object
        Writer writer = response.getWriter();

        // Generate the XML representation of the AjaxResponse
        String xml = ajax.toXml(true);

        // Write the xml representation string to the character writer, and flush it
        writer.write(xml);
        writer.flush();

        writer.close();
    }

    /**
     * Convert a date object to string.
     *
     * @param date the date to convert
     * @return the string representation
     * @throws IllegalArgumentException if date is null
     */
    public static String dateToString(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date should not be null.");
        }
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

        return formatter.format(date);
    }
    /**
     * Return the exception stack trace string.
     *
     * @param cause the exception to be recorded
     *
     * @return stack trace
     */
    public static String getExceptionStackTrace(Throwable cause) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        cause.printStackTrace(new PrintStream(out));

        return out.toString();
    }
}
