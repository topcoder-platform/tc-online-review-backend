package com.topcoder.onlinereview.component.webcommon.tag;

import com.topcoder.onlinereview.component.grpcclient.GrpcHelper;
import com.topcoder.onlinereview.component.grpcclient.webcommon.WebCommonServiceRpc;
import com.topcoder.onlinereview.component.webcommon.ApplicationServer;
import com.topcoder.onlinereview.grpc.webcommon.proto.CoderRatingsProto;
import com.topcoder.onlinereview.grpc.webcommon.proto.GetCoderAllRatingsResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.TagSupport;

public class HandleTag extends TagSupport {
    private static final Logger log = LoggerFactory.getLogger(HandleTag.class);
    private long coderId;
    private String link = "";
    private String cssclass = "";
    private boolean darkBG = false;
    private String handle = null;
    /*
        private boolean algorithm = false;
        private boolean hsAlgorithm = false;
        private boolean design = false;
        private boolean development = false;
        private boolean component = false;
        private boolean hsOrAlgorithm = false;
    */
    private String context = null;

    public final static String DEFAULT_LINK = "/members/";

    public final static String ALGORITHM = "algorithm";
    public final static String HS_ALGORITHM = "hs_algorithm";
    public final static String DESIGN = "design";
    public final static String DEVELOPMENT = "development";
    public final static String CONCEPTUALIZATION = "conceptualization";
    public final static String SPECIFICATION = "specification";
    public final static String ARCHITECTURE = "architecture";
    public final static String ASSEMBLY = "assembly";
    public final static String TEST_SUITES = "test_suites";
    public final static String TEST_SCENARIOS = "test_scenarios";
    public final static String UI_PROTOTYPE = "ui_prototype";
    public final static String RIA_BUILD = "ria_build";
    public final static String CONTENT_CREATION = "content_creation";
    public final static String REPORTING = "reporting";
    public final static String COMPONENT = "component";
    public final static String HS_OR_ALGORITHM = "hs_or_algorithm";
    public final static String MARATHON_MATCH = "marathon_match";

    private static final String[] lightStyles =
            {"coderTextOrange", "coderTextWhite", "coderTextGray",
                    "coderTextGreen", "coderTextBlue", "coderTextYellow", "coderTextRed"};

    private static final String[] darkStyles =
            {"coderTextOrange", "coderTextBlack", "coderTextGray",
                    "coderTextGreen", "coderTextBlue", "coderTextYellow", "coderTextRed"};

    public void setCoderId(long coderId) {
        //log.debug("setting coderid " + coderId);
        this.coderId = coderId;
    }

    public void setCoderId(int coderId) {
        //log.debug("setting coderid " + coderId);
        this.coderId = coderId;
    }


    public void setCoderId(String coderId) {
        //log.debug("setting coderid " + coderId);
        this.coderId = Long.parseLong(coderId);
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setStyleClass(String cssclass) {
        this.cssclass = cssclass;
    }

    public void setDarkBG(String s) {
        this.darkBG = (s.toLowerCase().trim().equals("true"));
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public void setContext(String s) {
/*
        if (s.toLowerCase().trim().equals(ALGORITHM)) algorithm = true;
        if (s.toLowerCase().trim().equals(HS_ALGORITHM)) hsAlgorithm = true;
        if (s.toLowerCase().trim().equals(DESIGN)) design = true;
        if (s.toLowerCase().trim().equals(DEVELOPMENT)) development = true;
        if (s.toLowerCase().trim().equals(COMPONENT)) component = true;
        if (s.toLowerCase().trim().equals(HS_OR_ALGORITHM)) hsOrAlgorithm = true;
*/
        this.context = s;
    }

    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print(getLink(coderId, cssclass, link, pageContext, context, handle, lightStyles,
                    darkStyles, darkBG));
        } catch (Exception e) {
            log.error("on coder id " + coderId);
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    /**
     * <p>
     * Build the handle link tag of the given coder id, using the given parameters.
     * </p>
     *
	 
     * <p>
     * Update in contest - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab 2
     * - Add parameter handle.
     * </p>
     *
     * @since Member Profile Enhancement assembly
     */
    public static String getLink(long coderId, String cssclass, String link,
                                 PageContext pageContext, String context,
                                 String[] lightStyles, String[] darkStyles, boolean darkBG) throws Exception {
        return getLink(coderId, cssclass, link, pageContext, context, null, lightStyles,
                    darkStyles, darkBG); 
    }
    /**
     * <p>
     * Build the handle link tag of the given coder id, using the given parameters.
     * </p>
     *
     * @since Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab 2
     */
    public static String getLink(long coderId, String cssclass, String link,
                                 PageContext pageContext, String context, String handle,
                                 String[] lightStyles, String[] darkStyles, boolean darkBG) throws Exception {

        //lookup ratings from cache
        WebCommonServiceRpc webCommonServiceRpc = GrpcHelper.getWebCommonServiceRpc();
        GetCoderAllRatingsResponse result = webCommonServiceRpc.getCoderAllRatings(coderId);

        StringBuffer output = new StringBuffer();

        if (!result.hasCoderRatings()) {
            if(handle != null && handle.trim().length() != 0) {
                output.append(handle);
            } else {
                output.append("UNKNOWN USER");
            }
        } else if (!result.getCoderRatings().hasCoderId()) {
            output.append(result.getCoderRatings().hasHandle() ? result.getCoderRatings().getHandle() : null);
        } else {
            CoderRatingsProto ratings = result.getCoderRatings();
            //check for css override
            boolean bCSSOverride = false;
            if (cssclass != null && !cssclass.equals("")) {
                bCSSOverride = true;
            }
            output.append("<a href=\"");
            String handleFromDB = ratings.hasHandle() ? ratings.getHandle() : null;
            if (link == null || link.equals("")) {
                StringBuffer buf = new StringBuffer(100);
                if (pageContext != null && pageContext.getRequest().getServerName().indexOf(ApplicationServer.SERVER_NAME) >= 0) {
                    link = buf.append(DEFAULT_LINK).append(handleFromDB).toString();
                } else {
                    link = buf.append("https://").append(ApplicationServer.SERVER_NAME).append(DEFAULT_LINK).append(handleFromDB).toString();
                }

            }
            output.append(link);
            if (context != null) {
                if (context.trim().equalsIgnoreCase(ALGORITHM) && ratings.hasAlgorithmRating()) {
                    output.append("&amp;tab=alg");
                } else if (context.trim().equalsIgnoreCase(HS_ALGORITHM) && ratings.hasHsAlgorithmRating()) {
                    output.append("&amp;tab=hs");
                } else if (context.trim().equalsIgnoreCase(MARATHON_MATCH) && ratings.hasMarathonMatchRating()) {
                    output.append("&amp;tab=long");
                } else if (context.trim().equalsIgnoreCase(DESIGN) && ratings.hasDesignRating()) {
                    output.append("&amp;tab=des");
                } else if (context.trim().equalsIgnoreCase(DEVELOPMENT) && ratings.hasDevelopmentRating()) {
                    output.append("&amp;tab=dev");
                } else if (context.trim().equalsIgnoreCase(CONCEPTUALIZATION) && ratings.hasConceptualizationRating()) {
                    output.append("&amp;tab=concept");
                } else if (context.trim().equalsIgnoreCase(SPECIFICATION) && ratings.hasSpecificationRating()) {
                    output.append("&amp;tab=spec");
                } else if (context.trim().equalsIgnoreCase(ARCHITECTURE) && ratings.hasArchitectureRating()) {
                    output.append("&amp;tab=arch");
                } else if (context.trim().equalsIgnoreCase(ASSEMBLY) && ratings.hasAssemblyRating()) {
                    output.append("&amp;tab=assembly");
                } else if (context.trim().equalsIgnoreCase(TEST_SUITES) && ratings.hasTestSuitesRating()) {
                    output.append("&amp;tab=test");
                } else if (context.trim().equalsIgnoreCase(TEST_SCENARIOS) && ratings.hasTestScenariosRating()) {
                    output.append("&amp;tab=test_scenarios");
                } else if (context.trim().equalsIgnoreCase(UI_PROTOTYPE) && ratings.hasUiPrototypeRating()) {
                    output.append("&amp;tab=ui_prototype");
                } else if (context.trim().equalsIgnoreCase(RIA_BUILD) && ratings.hasRiaBuildRating()) {
                    output.append("&amp;tab=ria_build");
                } else if (context.trim().equalsIgnoreCase(CONTENT_CREATION) && ratings.hasContentCreationRating()) {
                    output.append("&amp;tab=content_creation");
                } else if (context.trim().equalsIgnoreCase(REPORTING) && ratings.hasReportingRating()) {
                    output.append("&amp;tab=reporting");
                } else if (context.trim().equalsIgnoreCase(COMPONENT)) {
                    if (ratings.getDesignRating() >= ratings.getDevelopmentRating()) {
                        if (ratings.hasDesignRating()) {
                            output.append("&amp;tab=des");
                        }
                    } else {
                        if (ratings.hasDevelopmentRating()) {
                            output.append("&amp;tab=dev");
                        }
                    }
                } else if (context.trim().equalsIgnoreCase(HS_OR_ALGORITHM)) {
                    if (ratings.getAlgorithmRating() >= ratings.getHsAlgorithmRating()) {
                        if (ratings.hasAlgorithmRating()) {
                            output.append("&amp;tab=alg");
                        }
                    } else {
                        if (ratings.hasHsAlgorithmRating()) {
                            output.append("&amp;tab=hs");
                        }
                    }
                }
            }
            output.append("\" class=\"");

            if (bCSSOverride) {
                output.append(cssclass);
            } else {
                int rating = 0;
                // special case for admins
                if ( ratings.getAlgorithmRating() < 0) {
                    rating = ratings.getAlgorithmRating();
                } else {
                    if (context == null) {
                        rating = max(ratings.getAlgorithmRating(),
                                ratings.getHsAlgorithmRating(),
                                ratings.getMarathonMatchRating(),
                                ratings.getDesignRating(),
                                ratings.getDevelopmentRating(),
                                ratings.getConceptualizationRating(),
                                ratings.getSpecificationRating(),
                                ratings.getArchitectureRating(),
                                ratings.getAssemblyRating(),
                                ratings.getTestSuitesRating(),
                                ratings.getTestScenariosRating(),
                                ratings.getUiPrototypeRating(),
                                ratings.getRiaBuildRating(),
                                ratings.getContentCreationRating(),
                                ratings.getReportingRating());
                    } else if (context.trim().equalsIgnoreCase(ALGORITHM)) {
                        rating = ratings.getAlgorithmRating();
                    } else if (context.trim().equalsIgnoreCase(HS_ALGORITHM)) {
                        rating = ratings.getHsAlgorithmRating();
                    } else if (context.trim().equalsIgnoreCase(MARATHON_MATCH)) {
                        rating = ratings.getMarathonMatchRating();
                    } else if (context.trim().equalsIgnoreCase(DESIGN)) {
                        rating = ratings.getDesignRating();
                    } else if (context.trim().equalsIgnoreCase(DEVELOPMENT)) {
                        rating = ratings.getDevelopmentRating();
                    } else if (context.trim().equalsIgnoreCase(CONCEPTUALIZATION)) {
                        rating = ratings.getConceptualizationRating();
                    } else if (context.trim().equalsIgnoreCase(SPECIFICATION)) {
                        rating = ratings.getSpecificationRating();
                    } else if (context.trim().equalsIgnoreCase(ARCHITECTURE)) {
                        rating = ratings.getArchitectureRating();
                    } else if (context.trim().equalsIgnoreCase(ASSEMBLY)) {
                        rating = ratings.getAssemblyRating();
                    } else if (context.trim().equalsIgnoreCase(TEST_SUITES)) {
                        rating = ratings.getTestSuitesRating();
                    } else if (context.trim().equalsIgnoreCase(TEST_SCENARIOS)) {
                        rating = ratings.getTestScenariosRating();
                    } else if (context.trim().equalsIgnoreCase(UI_PROTOTYPE)) {
                        rating = ratings.getUiPrototypeRating();
                    } else if (context.trim().equalsIgnoreCase(RIA_BUILD)) {
                        rating = ratings.getRiaBuildRating();
                    } else if (context.trim().equalsIgnoreCase(CONTENT_CREATION)) {
                        rating = ratings.getContentCreationRating();
                    } else if (context.trim().equalsIgnoreCase(REPORTING)) {
                        rating = ratings.getReportingRating();
                    } else if (context.trim().equalsIgnoreCase(COMPONENT)) {
                        rating = max(ratings.getDesignRating(),ratings.getDevelopmentRating());
                    } else if (context.trim().equalsIgnoreCase(HS_OR_ALGORITHM)) {
                        rating = max(ratings.getHsAlgorithmRating(),ratings.getAlgorithmRating());
                    } else {
                        rating = max(ratings.getAlgorithmRating(),
                                ratings.getHsAlgorithmRating(),
                                ratings.getMarathonMatchRating(),
                                ratings.getDesignRating(),
                                ratings.getDevelopmentRating(),
                                ratings.getConceptualizationRating(),
                                ratings.getSpecificationRating(),
                                ratings.getArchitectureRating(),
                                ratings.getAssemblyRating(),
                                ratings.getTestSuitesRating(),
                                ratings.getTestScenariosRating(),
                                ratings.getUiPrototypeRating(),
                                ratings.getRiaBuildRating(),
                                ratings.getContentCreationRating(),
                                ratings.getReportingRating());
                    }
                }

                //log.debug("rating: " + rating + " rsc: " + rsc.toString());
                output.append(getRatingCSS(rating, lightStyles, darkStyles, darkBG));
            }

            output.append("\">");

            output.append(handleFromDB);

            output.append("</a>");
        }
        return output.toString();
    }

    private static String getRatingCSS(int rating, String[] lightStyles, String[] darkStyles, boolean darkBG) {
        if (rating < 0)
            return darkBG ? lightStyles[0] : darkStyles[0];
        else if (rating == 0)
            return darkBG ? lightStyles[1] : darkStyles[1];
        else if (rating > 0 && rating < 900)
            return darkBG ? lightStyles[2] : darkStyles[2];
        else if (rating > 899 && rating < 1200)
            return darkBG ? lightStyles[3] : darkStyles[3];
        else if (rating > 1199 && rating < 1500)
            return darkBG ? lightStyles[4] : darkStyles[4];
        else if (rating > 1499 && rating < 2200)
            return darkBG ? lightStyles[5] : darkStyles[5];
        else if (rating > 2199) return darkBG ? lightStyles[6] : darkStyles[6];
        return "";
    }

    private static int max(int a, int b, int c, int d, int e, int i, int j, int k, int l, int m, int n, int o, int p, int q, int r) {
        return max(max(max(max(max(max(max(max(max(max(max(max(max(max(a, b), c), d), e), i), j), k), l), m), n), o), p), q), r);
    }

    private static int max(int a, int b) {
        if (a >= b) return a;
        return b;
    }

    /**
     * Because the app server (JBoss) is caching the tag,
     * we have to clear out all the instance variables at the
     * end of execution.
     */
    public int doEndTag() throws JspException {
        coderId = 0;
        link = "";
        cssclass = "";
        darkBG = false;
/*
        algorithm = false;
        hsAlgorithm = false;
        design = false;
        development = false;
        component = false;
        hsOrAlgorithm = false;
*/
        context = null;
        return super.doEndTag();
    }


}
