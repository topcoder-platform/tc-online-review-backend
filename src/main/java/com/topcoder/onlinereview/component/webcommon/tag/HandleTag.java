package com.topcoder.onlinereview.component.webcommon.tag;

import com.topcoder.onlinereview.component.shared.dataaccess.Request;
import com.topcoder.onlinereview.component.webcommon.ApplicationServer;
import com.topcoder.onlinereview.component.webcommon.CachedDataAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import java.util.Map;

import static com.topcoder.onlinereview.component.util.CommonUtils.getInt;
import static com.topcoder.onlinereview.component.util.CommonUtils.getString;
import static com.topcoder.onlinereview.component.util.SpringUtils.getOltpJdbcTemplate;

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
        CachedDataAccess da = new CachedDataAccess(getOltpJdbcTemplate());

        Request r = new Request();
        r.setContentHandle("coder_all_ratings");
        r.setProperty("cr", String.valueOf(coderId));

        Map<String, List<Map<String, Object>>> m = da.getData(r);

        StringBuffer output = new StringBuffer();

        List<Map<String, Object>> rsc = m.get("coder_all_ratings");
        if (rsc.isEmpty()) {
            if(handle != null && handle.trim().length() != 0) {
                output.append(handle);
            } else {
                output.append("UNKNOWN USER");
            }
        } else if (rsc.get(0).get("coder_id") == null) {
            output.append(getString(rsc.get(0), "handle"));
        } else {

            //check for css override
            boolean bCSSOverride = false;
            if (cssclass != null && !cssclass.equals("")) {
                bCSSOverride = true;
            }
            output.append("<a href=\"");
            String handleFromDB = getString(rsc.get(0), "handle");
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
                if (context.trim().equalsIgnoreCase(ALGORITHM) && getIntNotNull(rsc.get(0), "algorithm_rating") > 0) {
                    output.append("&amp;tab=alg");
                } else if (context.trim().equalsIgnoreCase(HS_ALGORITHM) && getIntNotNull(rsc.get(0), "hs_algorithm_rating") > 0) {
                    output.append("&amp;tab=hs");
                } else if (context.trim().equalsIgnoreCase(MARATHON_MATCH) && getIntNotNull(rsc.get(0), "marathon_match_rating") > 0) {
                    output.append("&amp;tab=long");
                } else if (context.trim().equalsIgnoreCase(DESIGN) && getIntNotNull(rsc.get(0), "design_rating") > 0) {
                    output.append("&amp;tab=des");
                } else if (context.trim().equalsIgnoreCase(DEVELOPMENT) && getIntNotNull(rsc.get(0), "development_rating") > 0) {
                    output.append("&amp;tab=dev");
                } else if (context.trim().equalsIgnoreCase(CONCEPTUALIZATION) && getIntNotNull(rsc.get(0), "conceptualization_rating") > 0) {
                    output.append("&amp;tab=concept");
                } else if (context.trim().equalsIgnoreCase(SPECIFICATION) && getIntNotNull(rsc.get(0), "specification_rating") > 0) {
                    output.append("&amp;tab=spec");
                } else if (context.trim().equalsIgnoreCase(ARCHITECTURE) && getIntNotNull(rsc.get(0), "architecture_rating") > 0) {
                    output.append("&amp;tab=arch");
                } else if (context.trim().equalsIgnoreCase(ASSEMBLY) && getIntNotNull(rsc.get(0), "assembly_rating") > 0) {
                    output.append("&amp;tab=assembly");
                } else if (context.trim().equalsIgnoreCase(TEST_SUITES) && getIntNotNull(rsc.get(0), "test_suites_rating") > 0) {
                    output.append("&amp;tab=test");
                } else if (context.trim().equalsIgnoreCase(TEST_SCENARIOS) && getIntNotNull(rsc.get(0), "test_scenarios_rating") > 0) {
                    output.append("&amp;tab=test_scenarios");
                } else if (context.trim().equalsIgnoreCase(UI_PROTOTYPE) && getIntNotNull(rsc.get(0), "ui_prototype_rating") > 0) {
                    output.append("&amp;tab=ui_prototype");
                } else if (context.trim().equalsIgnoreCase(RIA_BUILD) && getIntNotNull(rsc.get(0), "ria_build_rating") > 0) {
                    output.append("&amp;tab=ria_build");
                } else if (context.trim().equalsIgnoreCase(CONTENT_CREATION) && getIntNotNull(rsc.get(0), "content_creation_rating") > 0) {
                    output.append("&amp;tab=content_creation");
                } else if (context.trim().equalsIgnoreCase(REPORTING) && getIntNotNull(rsc.get(0), "reporting_rating") > 0) {
                    output.append("&amp;tab=reporting");
                } else if (context.trim().equalsIgnoreCase(COMPONENT)) {
                    if (getIntNotNull(rsc.get(0), "design_rating") >= getIntNotNull(rsc.get(0), "development_rating")) {
                        if (getIntNotNull(rsc.get(0), "design_rating") > 0) {
                            output.append("&amp;tab=des");
                        }
                    } else {
                        if (getIntNotNull(rsc.get(0), "development_rating") > 0) {
                            output.append("&amp;tab=dev");
                        }
                    }
                } else if (context.trim().equalsIgnoreCase(HS_OR_ALGORITHM)) {
                    if (getIntNotNull(rsc.get(0), "algorithm_rating") >= getIntNotNull(rsc.get(0), "hs_algorithm_rating")) {
                        if (getIntNotNull(rsc.get(0), "algorithm_rating") > 0) {
                            output.append("&amp;tab=alg");
                        }
                    } else {
                        if (getIntNotNull(rsc.get(0), "hs_algorithm_rating") > 0) {
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
                if (getIntNotNull(rsc.get(0), "algorithm_rating") < 0) {
                    rating = getIntNotNull(rsc.get(0), "algorithm_rating");
                } else {
                    if (context == null) {
                        rating = max(getIntNotNull(rsc.get(0), "algorithm_rating"),
                                getIntNotNull(rsc.get(0), "hs_algorithm_rating"),
                                getIntNotNull(rsc.get(0), "marathon_match_rating"),
                                getIntNotNull(rsc.get(0), "design_rating"),
                                getIntNotNull(rsc.get(0), "development_rating"),
                                getIntNotNull(rsc.get(0), "conceptualization_rating"),
                                getIntNotNull(rsc.get(0), "specification_rating"),
                                getIntNotNull(rsc.get(0), "architecture_rating"),
                                getIntNotNull(rsc.get(0), "assembly_rating"),
                                getIntNotNull(rsc.get(0), "test_suites_rating"),
                                getIntNotNull(rsc.get(0), "test_scenarios_rating"),
                                getIntNotNull(rsc.get(0), "ui_prototype_rating"),
                                getIntNotNull(rsc.get(0), "ria_build_rating"),
                                getIntNotNull(rsc.get(0), "content_creation_rating"),
                                getIntNotNull(rsc.get(0), "reporting_rating"));
                    } else if (context.trim().equalsIgnoreCase(ALGORITHM)) {
                        rating = getIntNotNull(rsc.get(0), "algorithm_rating");
                    } else if (context.trim().equalsIgnoreCase(HS_ALGORITHM)) {
                        rating = getIntNotNull(rsc.get(0), "hs_algorithm_rating");
                    } else if (context.trim().equalsIgnoreCase(MARATHON_MATCH)) {
                        rating = getIntNotNull(rsc.get(0), "marathon_match_rating");
                    } else if (context.trim().equalsIgnoreCase(DESIGN)) {
                        rating = getIntNotNull(rsc.get(0), "design_rating");
                    } else if (context.trim().equalsIgnoreCase(DEVELOPMENT)) {
                        rating = getIntNotNull(rsc.get(0), "development_rating");
                    } else if (context.trim().equalsIgnoreCase(CONCEPTUALIZATION)) {
                        rating = getIntNotNull(rsc.get(0), "conceptualization_rating");
                    } else if (context.trim().equalsIgnoreCase(SPECIFICATION)) {
                        rating = getIntNotNull(rsc.get(0), "specification_rating");
                    } else if (context.trim().equalsIgnoreCase(ARCHITECTURE)) {
                        rating = getIntNotNull(rsc.get(0), "architecture_rating");
                    } else if (context.trim().equalsIgnoreCase(ASSEMBLY)) {
                        rating = getIntNotNull(rsc.get(0), "assembly_rating");
                    } else if (context.trim().equalsIgnoreCase(TEST_SUITES)) {
                        rating = getIntNotNull(rsc.get(0), "test_suites_rating");
                    } else if (context.trim().equalsIgnoreCase(TEST_SCENARIOS)) {
                        rating = getIntNotNull(rsc.get(0), "test_scenarios_rating");
                    } else if (context.trim().equalsIgnoreCase(UI_PROTOTYPE)) {
                        rating = getIntNotNull(rsc.get(0), "ui_prototype_rating");
                    } else if (context.trim().equalsIgnoreCase(RIA_BUILD)) {
                        rating = getIntNotNull(rsc.get(0), "ria_build_rating");
                    } else if (context.trim().equalsIgnoreCase(CONTENT_CREATION)) {
                        rating = getIntNotNull(rsc.get(0), "content_creation_rating");
                    } else if (context.trim().equalsIgnoreCase(REPORTING)) {
                        rating = getIntNotNull(rsc.get(0), "reporting_rating");
                    } else if (context.trim().equalsIgnoreCase(COMPONENT)) {
                        rating = max(getIntNotNull(rsc.get(0), "design_rating"),
                                getIntNotNull(rsc.get(0), "development_rating"));
                    } else if (context.trim().equalsIgnoreCase(HS_OR_ALGORITHM)) {
                        rating = max(getIntNotNull(rsc.get(0), "hs_algorithm_rating"),
                                getIntNotNull(rsc.get(0), "algorithm_rating"));
                    } else {
                        rating = max(getIntNotNull(rsc.get(0), "algorithm_rating"),
                                getIntNotNull(rsc.get(0), "hs_algorithm_rating"),
                                getIntNotNull(rsc.get(0), "marathon_match_rating"),
                                getIntNotNull(rsc.get(0), "design_rating"),
                                getIntNotNull(rsc.get(0), "development_rating"),
                                getIntNotNull(rsc.get(0), "conceptualization_rating"),
                                getIntNotNull(rsc.get(0), "specification_rating"),
                                getIntNotNull(rsc.get(0), "architecture_rating"),
                                getIntNotNull(rsc.get(0), "assembly_rating"),
                                getIntNotNull(rsc.get(0), "test_suites_rating"),
                                getIntNotNull(rsc.get(0), "test_scenarios_rating"),
                                getIntNotNull(rsc.get(0), "ui_prototype_rating"),
                                getIntNotNull(rsc.get(0), "ria_build_rating"),
                                getIntNotNull(rsc.get(0), "content_creation_rating"),
                                getIntNotNull(rsc.get(0), "reporting_rating"));
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
    
    private static int getIntNotNull(Map<String, Object> row, String key) {
        Integer i = getInt(row, key);
        if (i == null) {
            return 0;
        }
        return i;
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
