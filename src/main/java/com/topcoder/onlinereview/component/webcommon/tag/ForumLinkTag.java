package com.topcoder.onlinereview.component.webcommon.tag;

import com.topcoder.onlinereview.component.webcommon.ApplicationServer;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;

/* 
 * @author mtong
 * @date 8/26/05
 * 
 * Returns a link to the given forum, displaying its message count.
 */

public class ForumLinkTag extends TagSupport {
    private long forumID = -1;
    private String message = "";

    public int doStartTag() throws JspException {
        StringBuffer ret = new StringBuffer(150);

        ret.append("<a");
        StringBuffer url = new StringBuffer();
        url.append("http://").append(ApplicationServer.FORUMS_SERVER_NAME).append("/");
        url.append("?module=ThreadList&amp;").append("forumID").append("=").append(forumID);
        ret.append(" href=\"").append(url).append("\"");
        ret.append(">");

        /*
        ForumFactory forumFactory = ForumFactory.getInstance(AuthFactory.getAnonymousAuthToken());
        try {
            Forum forum = forumFactory.getForum(forumID);
            ret.append(this.message).append(" (").append(forum.getMessageCount());
            if (forum.getMessageCount() == 1) {
                ret.append(" comment)");
            } else {
                ret.append(" comments)");
            }
        } catch (Exception ignored) {}
        */
        ret.append(this.message);

        ret.append("</a>");

        try {
            pageContext.getOut().print(ret.toString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    public void setForumID(long forumID) {
        this.forumID = forumID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    protected void init() {
        forumID = -1;
        message = "Discuss this";
    }

    public int doEndTag() throws JspException {
        forumID = -1;
        message = "";
        return super.doEndTag();
    }
}
