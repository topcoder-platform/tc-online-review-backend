package com.topcoder.onlinereview.component.webcommon.model.language;

import java.awt.*;

public class ProblemRenderer extends BaseRenderer implements ElementRenderer {
    private Problem problem;
    private Color backgroundColor = null;
    private Color foregroundColor = null;
    private static final String LEGAL = "This problem statement is the exclusive and proprietary property of TopCoder, Inc.  Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited.  (c)2010, TopCoder, Inc.  All rights reserved.  ";

    public ProblemRenderer() {
        this.problem = null;
    }

    public ProblemRenderer(Problem problem) {
        this.problem = problem;
    }

    public void setElement(Element element) throws Exception {
        this.problem = (Problem)element;
    }

    public String toHTML(Language language) throws Exception {
        StringBuffer html = new StringBuffer(1000);
        if (!this.problem.getProblemText().equals("")) {
            html.append(this.problem.getProblemText());
            html.append("<hr>");
        }

        ProblemComponent[] problemComponents = this.problem.getProblemComponents();
        int i;
        if (this.problem.getProblemTypeID() == 3) {
            for(i = 0; i < problemComponents.length && problemComponents[i] != null; ++i) {
                LongContestComponentRenderer pcr = new LongContestComponentRenderer(problemComponents[i]);
                pcr.setTdClass(this.getTdClass());
                html.append(pcr.toHTML(language));
                html.append("<hr>");
            }
        } else {
            for(i = 0; i < problemComponents.length && problemComponents[i] != null; ++i) {
                ProblemComponentRenderer pcr = new ProblemComponentRenderer(problemComponents[i]);
                pcr.setTdClass(this.getTdClass());
                html.append(pcr.toHTML(language));
                html.append("<hr>");
            }
        }

        html.append("<p>");
        html.append("This problem statement is the exclusive and proprietary property of TopCoder, Inc.  Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited.  (c)2010, TopCoder, Inc.  All rights reserved.  ");
        html.append("</p>");
        return html.toString();
    }

    public String toPlainText(Language language) throws Exception {
        StringBuffer text = new StringBuffer(1000);
        if (!this.problem.getProblemText().equals("")) {
            text.append(this.problem.getProblemText());
            text.append("\n\n\n");
        }

        ProblemComponent[] problemComponents = this.problem.getProblemComponents();

        for(int i = 0; i < problemComponents.length; ++i) {
            text.append((new ProblemComponentRenderer(problemComponents[i])).toPlainText(language));
            text.append("\n\n\n");
        }

        text.append("This problem statement is the exclusive and proprietary property of TopCoder, Inc.  Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited.  (c)2010, TopCoder, Inc.  All rights reserved.  ");
        return text.toString();
    }

    private String rgbColor(Color c) {
        String red = this.lpad(Integer.toString(c.getRed(), 16), '0', 2);
        String green = this.lpad(Integer.toString(c.getGreen(), 16), '0', 2);
        String blue = this.lpad(Integer.toString(c.getBlue(), 16), '0', 2);
        return red + green + blue;
    }

    private String lpad(String s, char c, int len) {
        StringBuffer buf = new StringBuffer(len);

        for(int i = 0; i < len - s.length(); ++i) {
            buf.append(c);
        }

        buf.append(s);
        return buf.toString();
    }

    /** @deprecated */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /** @deprecated */
    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }
}
