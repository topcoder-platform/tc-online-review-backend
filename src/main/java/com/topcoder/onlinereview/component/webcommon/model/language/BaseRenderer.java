package com.topcoder.onlinereview.component.webcommon.model.language;

abstract class BaseRenderer implements ElementRenderer {
    static String[] XML_ONLY_TAGS = new String[]{"tctype", "list", "fontstyle", "heading", "special", "inline", "type", "block", "flow", "type", "problem", "signature", "intro", "spec", "notes", "note", "constraints", "user-constraint", "test-cases", "test-case", "input", "output", "annotation", "example", "name"};
    static String[] HTML_ONLY_TAGS = new String[]{"ul", "ol", "li", "tt", "i", "b", "h1", "h2", "h3", "h4", "h5", "a", "img", "br", "sub", "sup", "p", "pre", "hr", "list"};
    protected String tdClass;

    BaseRenderer() {
    }

    public abstract String toHTML(Language var1) throws InstantiationException, IllegalAccessException, ClassNotFoundException, Exception;

    public abstract String toPlainText(Language var1) throws InstantiationException, IllegalAccessException, ClassNotFoundException, Exception;

    static String encodeHTML(String text) {
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

    ElementRenderer getRenderer(Element element) throws Exception {
        String elementClassName = element.getClass().getName().substring(element.getClass().getName().lastIndexOf(".") + 1);
        String rendererClassName = elementClassName + "Renderer";
        String rendererPackage = ProblemRenderer.class.getName().substring(0, ProblemRenderer.class.getName().lastIndexOf("."));
        Class rendererClass = null;

        try {
            rendererClass = Class.forName(rendererPackage + "." + rendererClassName);
            ElementRenderer ret = (ElementRenderer)rendererClass.newInstance();
            ret.setElement(element);
            return ret;
        } catch (ClassNotFoundException var8) {
            throw new Exception("Could not find class: " + rendererPackage + rendererClassName);
        } catch (InstantiationException var9) {
            throw new Exception("Could not instantiate: " + rendererPackage + rendererClassName);
        } catch (IllegalAccessException var10) {
            throw new Exception("Illegal Access: " + rendererPackage + rendererClassName);
        }
    }

    static String removeHtmlTags(String text) {
        StringBuffer buf = new StringBuffer(text);

        for(int i = 0; i < HTML_ONLY_TAGS.length; ++i) {
            boolean clear = false;

            while(!clear) {
                int beginIndex = buf.toString().indexOf("<" + HTML_ONLY_TAGS[i] + ">");
                int endIndex1 = buf.toString().indexOf("</" + HTML_ONLY_TAGS[i] + ">");
                int endIndex2 = buf.toString().indexOf("<" + HTML_ONLY_TAGS[i] + "/>");
                clear = beginIndex < 0 && endIndex1 < 0 && endIndex2 < 0;
                if (beginIndex > -1) {
                    buf.delete(beginIndex, beginIndex + HTML_ONLY_TAGS[i].length() + 2);
                }

                if (endIndex1 > -1) {
                    buf.delete(endIndex1, endIndex1 + HTML_ONLY_TAGS[i].length() + 3);
                }

                if (endIndex2 > -1) {
                    buf.delete(endIndex2, endIndex2 + HTML_ONLY_TAGS[i].length() + 3);
                }
            }
        }

        return buf.toString();
    }

    public String getTdClass() {
        return this.tdClass;
    }

    public void setTdClass(String tdClass) {
        this.tdClass = tdClass;
    }
}
