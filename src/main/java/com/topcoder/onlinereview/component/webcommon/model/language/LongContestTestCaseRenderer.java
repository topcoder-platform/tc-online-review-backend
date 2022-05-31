package com.topcoder.onlinereview.component.webcommon.model.language;

public class LongContestTestCaseRenderer extends BaseRenderer implements ElementRenderer {
    private TestCase testCase;

    public LongContestTestCaseRenderer() {
        this.testCase = null;
    }

    public LongContestTestCaseRenderer(TestCase testCase) {
        this.testCase = testCase;
    }

    public void setElement(Element element) throws Exception {
        this.testCase = (TestCase)element;
    }

    public String toHTML(Language language) throws Exception {
        StringBuffer buf = new StringBuffer(256);
        buf.append("<table>");
        buf.append("<tr><td");
        if (this.getTdClass() != null) {
            buf.append(" class=\"");
            buf.append(this.getTdClass());
            buf.append("\"");
        }

        buf.append(">");
        buf.append("<pre>");
        String output = this.testCase.getOutput();
        buf.append(output.substring(1, output.length() - 1));
        buf.append("</pre>");
        buf.append("</td></tr>");
        buf.append("<tr><td");
        if (this.getTdClass() != null) {
            buf.append(" class=\"");
            buf.append(this.getTdClass());
            buf.append("\"");
        }

        buf.append(">");
        if (this.testCase.getAnnotation() != null) {
            buf.append("<table>");
            buf.append("<tr><td colspan=\"2\"");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">");
            buf.append(super.getRenderer(this.testCase.getAnnotation()).toHTML(language));
            buf.append("</td></tr>");
            buf.append("</table>");
        }

        buf.append("</td></tr>");
        buf.append("</table>");
        return buf.toString();
    }

    public String toPlainText(Language language) throws Exception {
        StringBuffer buf = new StringBuffer(256);
        buf.append(this.testCase.getOutput());
        if (this.testCase.getAnnotation() != null) {
            buf.append("\n\n");
            buf.append(super.getRenderer(this.testCase.getAnnotation()).toPlainText(language));
        }

        return buf.toString();
    }

    private String breakIt(String s) {
        int breakLen = 80;
        StringBuffer out = new StringBuffer(s);
        if (s.length() > breakLen) {
            out = new StringBuffer(s.length());
            out.append("\n");

            for(int i = 0; i < s.length(); ++i) {
                if (s.charAt(i) == '"' && s.length() > i + 1 && s.charAt(i + 1) == ',') {
                    out.append("\",\n");
                    i += 2;
                } else {
                    out.append(s.charAt(i));
                }
            }
        }

        return out.toString();
    }
}
