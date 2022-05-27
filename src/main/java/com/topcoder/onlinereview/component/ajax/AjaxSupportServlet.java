/*
 * Copyright (C) 2006-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.ajax;

import com.topcoder.onlinereview.component.ajax.handlers.LoadTimelineTemplateHandler;
import com.topcoder.onlinereview.component.ajax.handlers.PlaceAppealHandler;
import com.topcoder.onlinereview.component.ajax.handlers.ResolveAppealHandler;
import com.topcoder.onlinereview.component.ajax.handlers.SetScorecardStatusHandler;
import com.topcoder.onlinereview.component.ajax.handlers.SetTimelineNotificationHandler;
import com.topcoder.onlinereview.component.webcommon.SSOCookieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Main servlet class of the component.
 * This class extends HttpServlet class in order to process Ajax requests and produce Ajax responses.
 *
 * This class keeps a map of all Ajax request handlers,
 * and when the doPost method is called it follows these steps to process the request:
 * <ol>
 * <li>Get the user ID from the HttpSession.</li>
 * <li>Parse the Ajax request XML stream to produce an AjaxRequest object.</li>
 * <li>Pass the user id and the Ajax request to the correct request handler.</li>
 * <li>Write back the Ajax response XML produced by the request handler.</li>
 * </ol>
 *
 * When the Ajax request is incorrect, invalid or the target handler was not found,
 * then this servlet will return an error response to the client,
 * and log that error using the Logging Wrapper component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong>
 * This class is immutable and thread safe. all accesses to its internal state are read only once.
 * </p>
 *
 * @author topgear
 * @author assistant
 * @author George1
 * @version 1.1
 */
public final class AjaxSupportServlet extends HttpServlet {
    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(AjaxSupportServlet.class.getName());

    /**
     * <p>An <code>AuthCookieManager</code> to be used for user authentication based on cookies.</p>
     *
     * @since 1.1
     */
    private static SSOCookieService ssoCookieService = new SSOCookieService();

   
    /**
     * <p>
     * The Ajax request handlers map, as defined in the configuration.
     * This variable is immutable, both the variable and its content.
     * It is filled by the "init" method with handlers data. The "destroy" method must clear its content.<br><br>
     * <ul>
     * <li>Map Keys - are of type String, they can't be null, or empty strings,
     *                they represents the operation name handled by the handler</li>
     * <li>Map Values - are of type AjaxRequestHandler, they can't be null</li>
     * </ul>
     * </p>
     */
    private final Map<String, AjaxRequestHandler> handlers = new HashMap<String, AjaxRequestHandler>();

    /**
     * <p>
     * Creates an instance of this class.
     * </p>
     */
    public AjaxSupportServlet() {
        // do nothing
    }

    /**
     * <p>
     * Initialize the state of this servlet with configuration data.
     * </p>
     *
     * @param config the initial configuration of the servlet
     * @throws ServletException if an exception was caught
     */
    public void init(ServletConfig config) throws ServletException {
    	logger.debug("Init Ajax Support Servlet");
        super.init(config);
        handlers.put("SetScorecardStatus", new SetScorecardStatusHandler());
        handlers.put("LoadTimelineTemplate", new LoadTimelineTemplateHandler());
        handlers.put("SetTimelineNotification", new SetTimelineNotificationHandler());
        handlers.put("PlaceAppeal", new PlaceAppealHandler());
        handlers.put("ResolveAppeal", new ResolveAppealHandler());
    }

    /**
     * <p>
     * Destroy the state of this servlet.
     * </p>
     */
    public void destroy() {
        this.handlers.clear();
    }

    /**
     * <p>
     * Process an Ajax request by forwarding it to the appropriate Ajax request handler.
     * </p>
     *
     * @param request an HttpServletRequest object that contains the request the client has made of the servlet
     * @param response an HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException if an input or output error is detected when the servlet handles the request
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        Long userId;
        try {
            userId = ssoCookieService.getUserIdFromSSOCookie(request);
        } catch (Exception e) {
            logger.error("Could not retrieve user ID from SSO cookie.", e);
            throw new ServletException("Could not retrieve user ID from SSO cookie.", e);
        }

        // get the reader from the request
        Reader reader = request.getReader();

        try {
            // parse the content from the reader
            AjaxRequest ajaxRequest = AjaxRequest.parse(reader);

            // get the handler from the map
            AjaxRequestHandler handler = handlers.get(ajaxRequest.getType());

            // if the handler is null, response it with status "request error"
            if (handler == null) {
                AjaxSupportHelper.responseAndLogError(ajaxRequest.getType(), "Request error",
                        "There is no corresponding handler : " + ajaxRequest.getType(), response);
                return;
            }

            // serve the request and get the response
            AjaxResponse resp = handler.service(ajaxRequest, userId);
            
            if (resp == null) {
                AjaxSupportHelper.responseAndLogError(ajaxRequest.getType(),
                        "Server error", "Server can't satisfy this request", response);
                return;
            }
            if (!"success".equalsIgnoreCase(resp.getStatus())) {
            	StringBuilder buf = new StringBuilder();
                for (Object param : ajaxRequest.getAllParameterNames()) {
                    String paramName = (String) param;
                    buf.append(',')
                            .append(paramName)
                            .append(" = ")
                            .append(ajaxRequest.getParameter(paramName));
                }
            	logger.warn("problem handling request, status: " + resp.getStatus() +
            			"\ntype: " + resp.getType() +
            			"\nparams: " + (buf.length() == 0 ? "" : buf.substring(1)) + 
            			"\nuserId: " + userId +
            			"\nencoding: " + request.getCharacterEncoding());
            }
            // response at last, this is the normal condition
            AjaxSupportHelper.doResponse(response, resp);

        } catch (RequestParsingException e) {
            // if there is a parsing error then create an AjaxResponse containing the error message,
            // use the status "invalid request error"
            AjaxSupportHelper.responseAndLogError("Unknown", "invalid request error", e.getMessage(), response, e);
        }
    }

}
