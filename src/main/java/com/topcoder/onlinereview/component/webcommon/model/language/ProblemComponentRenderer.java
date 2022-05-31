package com.topcoder.onlinereview.component.webcommon.model.language;

public class ProblemComponentRenderer extends BaseRenderer implements ElementRenderer {
    private String SECTION_HEADER = "h3";
    private ProblemComponent problemComponent;
    private String LEFT_MARGIN = "&#160;&#160;&#160;&#160;";

    public ProblemComponentRenderer() {
        this.problemComponent = null;
    }

    public ProblemComponentRenderer(ProblemComponent problemComponent) {
        this.problemComponent = problemComponent;
    }

    public void setElement(Element element) throws Exception {
        this.problemComponent = (ProblemComponent)element;
    }

    public String toHTML(Language language) throws Exception {
        StringBuffer buf = new StringBuffer(4096);
        buf.append("<table>");
        buf.append("<tr>");
        buf.append("<td colspan=\"2\"");
        if (this.getTdClass() != null) {
            buf.append(" class=\"");
            buf.append(this.getTdClass());
            buf.append("\"");
        }

        buf.append(">");
        appendTag(buf, this.SECTION_HEADER, "Problem Statement");
        buf.append("</td>");
        buf.append("</tr>");
        if (this.problemComponent.getIntro() != null) {
            buf.append("<tr>");
            buf.append("<td");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">");
            buf.append(this.LEFT_MARGIN);
            buf.append("</td>");
            buf.append("<td");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">");
            buf.append(super.getRenderer(this.problemComponent.getIntro()).toHTML(language));
            buf.append("</td>");
            buf.append("</tr>");
        }

        buf.append("<tr><td colspan=\"2\"");
        if (this.getTdClass() != null) {
            buf.append(" class=\"");
            buf.append(this.getTdClass());
            buf.append("\"");
        }

        buf.append(">&#160;</td></tr>");
        buf.append("<tr>");
        buf.append("<td colspan=\"2\"");
        if (this.getTdClass() != null) {
            buf.append(" class=\"");
            buf.append(this.getTdClass());
            buf.append("\"");
        }

        buf.append(">");
        appendTag(buf, this.SECTION_HEADER, "Definition");
        buf.append("</td>");
        buf.append("</tr>");
        buf.append("<tr>");
        buf.append("<td");
        if (this.getTdClass() != null) {
            buf.append(" class=\"");
            buf.append(this.getTdClass());
            buf.append("\"");
        }

        buf.append(">");
        buf.append(this.LEFT_MARGIN);
        buf.append("</td>");
        buf.append("<td");
        if (this.getTdClass() != null) {
            buf.append(" class=\"");
            buf.append(this.getTdClass());
            buf.append("\"");
        }

        buf.append(">");
        buf.append("<table>");
        buf.append("<tr>");
        buf.append("<td");
        if (this.getTdClass() != null) {
            buf.append(" class=\"");
            buf.append(this.getTdClass());
            buf.append("\"");
        }

        buf.append(">Class:</td>");
        buf.append("<td");
        if (this.getTdClass() != null) {
            buf.append(" class=\"");
            buf.append(this.getTdClass());
            buf.append("\"");
        }

        buf.append(">");
        buf.append(this.problemComponent.getClassName());
        buf.append("</td>");
        buf.append("</tr>");
        int methodCount = this.problemComponent.getAllMethodNames().length;

        for(int i = methodCount > 1 ? 1 : 0; i < methodCount; ++i) {
            buf.append("<tr><td");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">Method:</td>");
            buf.append("<td");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">");
            buf.append(this.problemComponent.getAllMethodNames()[i]);
            buf.append("</td>");
            buf.append("</tr>");
            buf.append("<tr><td");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">Parameters:</td>");
            buf.append("<td");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">");
            DataType[] paramTypes = this.problemComponent.getAllParamTypes()[i];

            for(i = 0; i < paramTypes.length; ++i) {
                if (i > 0) {
                    buf.append(", ");
                }

                buf.append((new DataTypeRenderer(paramTypes[i])).toHTML(language));
            }

            buf.append("</td>");
            buf.append("</tr>");
            buf.append("<tr><td");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">Returns:</td>");
            buf.append("<td");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">");
            buf.append((new DataTypeRenderer(this.problemComponent.getAllReturnTypes()[i])).toHTML(language));
            buf.append("</td>");
            buf.append("</tr>");
            buf.append("<tr><td");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">Method signature:</td>");
            buf.append("<td");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">");
            buf.append(encodeHTML(language.getMethodSignature(this.problemComponent.getAllMethodNames()[i], this.problemComponent.getAllReturnTypes()[i], this.problemComponent.getAllParamTypes()[i], this.problemComponent.getAllParamNames()[i])));
            buf.append("</td>");
            buf.append("</tr>");
            buf.append("<tr><td colspan=\"2\"");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            if (i == methodCount - 1) {
                buf.append(">(be sure your method");
                if (methodCount > 2) {
                    buf.append("s are");
                } else {
                    buf.append(" is");
                }

                buf.append(" public)</td></tr>");
            }

            if (methodCount > 2 && i < methodCount - 1) {
                buf.append("<tr><td>&nbsp;</td></tr>");
            }
        }

        buf.append("</table>");
        buf.append("</td>");
        buf.append("</tr>");
        if (this.problemComponent.getSpec() != null) {
            buf.append("<tr>");
            buf.append("<td");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">");
            buf.append(this.LEFT_MARGIN);
            buf.append("</td>");
            buf.append("</tr>");
            buf.append("<tr>");
            buf.append("<td");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">");
            buf.append(super.getRenderer(this.problemComponent.getSpec()).toHTML(language));
            buf.append("</td>");
            buf.append("</tr>");
        }

        Element[] notes = this.problemComponent.getNotes();
        if (notes != null && notes.length > 0) {
            buf.append("<tr><td colspan=\"2\"");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">&#160;</td></tr>");
            buf.append("<tr>");
            buf.append("<td colspan=\"2\"");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">");
            appendTag(buf, this.SECTION_HEADER, "Notes");
            buf.append("</td>");
            buf.append("</tr>");

            for(int i = 0; i < notes.length; ++i) {
                buf.append("<tr>");
                buf.append("<td align=\"center\" valign=\"top\"");
                if (this.getTdClass() != null) {
                    buf.append(" class=\"");
                    buf.append(this.getTdClass());
                    buf.append("\"");
                }

                buf.append(">");
                buf.append("-");
                buf.append("</td>");
                buf.append("<td");
                if (this.getTdClass() != null) {
                    buf.append(" class=\"");
                    buf.append(this.getTdClass());
                    buf.append("\"");
                }

                buf.append(">");
                buf.append(super.getRenderer(notes[i]).toHTML(language));
                buf.append("</td>");
                buf.append("</tr>");
            }
        }

        Element[] constraints = this.problemComponent.getConstraints();
        if (constraints != null && constraints.length > 0) {
            buf.append("<tr><td colspan=\"2\"");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">&#160;</td></tr>");
            buf.append("<tr>");
            buf.append("<td colspan=\"2\"");
            if (this.getTdClass() != null) {
                buf.append(" class=\"");
                buf.append(this.getTdClass());
                buf.append("\"");
            }

            buf.append(">");
            appendTag(buf, this.SECTION_HEADER, "Constraints");
            buf.append("</td>");
            buf.append("</tr>");

            for(int i = 0; i < constraints.length; ++i) {
                buf.append("<tr>");
                buf.append("<td align=\"center\" valign=\"top\"");
                if (this.getTdClass() != null) {
                    buf.append(" class=\"");
                    buf.append(this.getTdClass());
                    buf.append("\"");
                }

                buf.append(">");
                buf.append("-");
                buf.append("</td>");
                buf.append("<td");
                if (this.getTdClass() != null) {
                    buf.append(" class=\"");
                    buf.append(this.getTdClass());
                    buf.append("\"");
                }

                buf.append(">");
                buf.append(super.getRenderer(constraints[i]).toHTML(language));
                buf.append("</td>");
                buf.append("</tr>");
            }
        }

        TestCase[] testCases = this.problemComponent.getTestCases();
        if (testCases != null && testCases.length > 0) {
            boolean hasExamples = false;

            int count;
            for(count = 0; count < testCases.length && !hasExamples; ++count) {
                hasExamples = testCases[count].isExample();
            }

            if (hasExamples) {
                buf.append("<tr><td colspan=\"2\"");
                if (this.getTdClass() != null) {
                    buf.append(" class=\"");
                    buf.append(this.getTdClass());
                    buf.append("\"");
                }

                buf.append(">&#160;</td></tr>");
                buf.append("<tr>");
                buf.append("<td colspan=\"2\"");
                if (this.getTdClass() != null) {
                    buf.append(" class=\"");
                    buf.append(this.getTdClass());
                    buf.append("\"");
                }

                buf.append(">");
                appendTag(buf, this.SECTION_HEADER, "Examples");
                buf.append("</td>");
                buf.append("</tr>");
                count = 0;
                TestCaseRenderer tcr = null;

                for(int i = 0; i < testCases.length; ++i) {
                    if (testCases[i].isExample()) {
                        buf.append("<tr>");
                        buf.append("<td align=\"center\" nowrap=\"true\"");
                        if (this.getTdClass() != null) {
                            buf.append(" class=\"");
                            buf.append(this.getTdClass());
                            buf.append("\"");
                        }

                        buf.append(">");
                        buf.append(count + ")");
                        buf.append("</td>");
                        buf.append("<td");
                        if (this.getTdClass() != null) {
                            buf.append(" class=\"");
                            buf.append(this.getTdClass());
                            buf.append("\"");
                        }

                        buf.append(">");
                        buf.append("</td>");
                        buf.append("</tr>");
                        buf.append("<tr>");
                        buf.append("<td");
                        if (this.getTdClass() != null) {
                            buf.append(" class=\"");
                            buf.append(this.getTdClass());
                            buf.append("\"");
                        }

                        buf.append(">");
                        buf.append(this.LEFT_MARGIN);
                        buf.append("</td>");
                        buf.append("<td");
                        if (this.getTdClass() != null) {
                            buf.append(" class=\"");
                            buf.append(this.getTdClass());
                            buf.append("\"");
                        }

                        buf.append(">");
                        tcr = new TestCaseRenderer(testCases[i]);
                        tcr.setTdClass(this.getTdClass());
                        buf.append(tcr.toHTML(language));
                        buf.append("</td>");
                        buf.append("</tr>");
                        ++count;
                    }
                }
            }
        }

        buf.append("</table>");
        return buf.toString();
    }

    static void appendTag(StringBuffer buf, String tag, String content) {
        buf.append('<');
        buf.append(tag);
        buf.append('>');
        buf.append(content);
        buf.append("</");
        buf.append(tag);
        buf.append('>');
    }

    public static String encodeHTML(String text) {
        StringBuffer buf = new StringBuffer(text.length());

        for(int i = 0; i < text.length(); ++i) {
            switch(text.charAt(i)) {
                case '"':
                    buf.append("&quot;");
                    break;
                case '&':
                    buf.append("&amp;");
                    break;
                case '<':
                    buf.append("&lt;");
                    break;
                case '>':
                    buf.append("&gt;");
                    break;
                default:
                    buf.append(text.charAt(i));
            }
        }

        return buf.toString();
    }

    public String toPlainText(Language language) throws Exception {
        StringBuffer text = new StringBuffer(4000);
        text.append("PROBLEM STATEMENT\n");
        if (this.problemComponent.getIntro() != null) {
            text.append(super.getRenderer(this.problemComponent.getIntro()).toPlainText(language));
        }

        text.append("\n\nDEFINITION");
        text.append("\nClass:");
        text.append(this.problemComponent.getClassName());
        int methodCount = this.problemComponent.getAllMethodNames().length;

        int j;
        for(int i = methodCount > 1 ? 1 : 0; i < methodCount; ++i) {
            text.append("\nMethod:");
            text.append(this.problemComponent.getAllMethodNames()[i]);
            text.append("\nParameters:");
            DataType[] paramTypes = this.problemComponent.getAllParamTypes()[i];

            for(j = 0; j < paramTypes.length; ++j) {
                if (j > 0) {
                    text.append(", ");
                }

                text.append((new DataTypeRenderer(paramTypes[j])).toPlainText(language));
            }

            text.append("\nReturns:");
            text.append((new DataTypeRenderer(this.problemComponent.getAllReturnTypes()[i])).toPlainText(language));
            text.append("\nMethod signature:");
            text.append(language.getMethodSignature(this.problemComponent.getAllMethodNames()[i], this.problemComponent.getAllReturnTypes()[i], this.problemComponent.getAllParamTypes()[i], this.problemComponent.getAllParamNames()[i]));
            text.append("\n");
        }

        if (this.problemComponent.getSpec() != null) {
            text.append(super.getRenderer(this.problemComponent.getSpec()).toPlainText(language));
        }

        Element[] notes = this.problemComponent.getNotes();
        if (notes != null && notes.length > 0) {
            text.append("\n\nNOTES\n");

            for(int k = 0; k < notes.length; ++k) {
                text.append("-");
                text.append(super.getRenderer(notes[k]).toPlainText(language));
                text.append("\n");
            }
        }

        Element[] constraints = this.problemComponent.getConstraints();
        if (constraints != null && constraints.length > 0) {
            text.append("\n\nCONSTRAINTS\n");

            for(j = 0; j < constraints.length; ++j) {
                text.append("-");
                text.append(super.getRenderer(constraints[j]).toPlainText(language));
                text.append("\n");
            }
        }

        TestCase[] testCases = this.problemComponent.getTestCases();
        if (testCases != null && testCases.length > 0) {
            text.append("\n\nEXAMPLES\n");
            int count = 0;

            for(int k = 0; k < testCases.length; ++k) {
                if (testCases[k].isExample()) {
                    text.append("\n" + count + ")\n");
                    text.append((new TestCaseRenderer(testCases[k])).toPlainText(language));
                    text.append("\n");
                    ++count;
                }
            }
        }

        return BaseRenderer.removeHtmlTags(text.toString());
    }
}
