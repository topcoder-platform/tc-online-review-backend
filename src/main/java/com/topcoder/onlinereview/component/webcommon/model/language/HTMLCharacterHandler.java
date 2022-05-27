package com.topcoder.onlinereview.component.webcommon.model.language;

import java.util.HashMap;
import java.util.Map;

public class HTMLCharacterHandler
{
    private static final int MAX_UNICODE_VALUE = 1114111;
    private static final Map nameToUnicodeValue;

    public static String decode(final String text) {
        int pos = text.indexOf(38);
        if (pos == -1) {
            return text;
        }
        final StringBuffer result = new StringBuffer(text.length());
        int lstPos;
        for (lstPos = 0; pos > -1; pos = text.indexOf(38, lstPos)) {
            Character encodedChar = null;
            result.append(text.substring(lstPos, pos));
            final int endPos = text.indexOf(59, pos);
            if (endPos > pos) {
                if (text.charAt(pos + 1) == '#') {
                    if (text.charAt(pos + 2) == 'x') {
                        encodedChar = resolveValueCharacter(text.substring(pos + 3, endPos), 16);
                    }
                    else {
                        encodedChar = resolveValueCharacter(text.substring(pos + 2, endPos), 10);
                    }
                }
                else {
                    encodedChar = resolveEntityValueCharacter(text.substring(pos + 1, endPos));
                }
            }
            if (encodedChar == null) {
                result.append("&");
                ++pos;
            }
            else {
                result.append(encodedChar);
                pos = endPos + 1;
            }
            lstPos = pos;
        }
        result.append(text.substring(lstPos));
        return result.toString();
    }

    public static Character resolveEntityValueCharacter(final String name) {
        return (Character)HTMLCharacterHandler.nameToUnicodeValue.get(name);
    }

    private static Character resolveValueCharacter(final String strValue, final int radix) {
        try {
            final int value = Integer.parseInt(strValue, radix);
            if (isValidUnicode(value)) {
                return getUnicodeCharacter(value);
            }
        }
        catch (Exception ex) {}
        return null;
    }

    public static boolean isValidUnicode(final int value) {
        return value >= 0 && value <= 1114111;
    }

    private static Character getUnicodeCharacter(final int charValue) {
        return new Character((char)charValue);
    }

    public static String encodeSimple(final String text) {
        final StringBuffer buf = new StringBuffer(text.length());
        for (int i = 0; i < text.length(); ++i) {
            switch (text.charAt(i)) {
                case '&': {
                    buf.append("&amp;");
                    break;
                }
                case '<': {
                    buf.append("&lt;");
                    break;
                }
                case '>': {
                    buf.append("&gt;");
                    break;
                }
                case '\"': {
                    buf.append("&quot;");
                    break;
                }
                default: {
                    buf.append(text.charAt(i));
                    break;
                }
            }
        }
        return buf.toString();
    }

    static {
        (nameToUnicodeValue = new HashMap(512)).put("AElig", new Character('\u00c6'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Aacute", new Character('\u00c1'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Acirc", new Character('\u00c2'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Agrave", new Character('\u00c0'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Alpha", new Character('\u0391'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Aring", new Character('\u00c5'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Atilde", new Character('\u00c3'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Auml", new Character('\u00c4'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Beta", new Character('\u0392'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Ccedil", new Character('\u00c7'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Chi", new Character('\u03a7'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Dagger", new Character('\u2021'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Delta", new Character('\u0394'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ETH", new Character('\u00d0'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Eacute", new Character('\u00c9'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Ecirc", new Character('\u00ca'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Egrave", new Character('\u00c8'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Epsilon", new Character('\u0395'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Eta", new Character('\u0397'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Euml", new Character('\u00cb'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Gamma", new Character('\u0393'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Iacute", new Character('\u00cd'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Icirc", new Character('\u00ce'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Igrave", new Character('\u00cc'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Iota", new Character('\u0399'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Iuml", new Character('\u00cf'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Kappa", new Character('\u039a'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Lambda", new Character('\u039b'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Mu", new Character('\u039c'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Ntilde", new Character('\u00d1'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Nu", new Character('\u039d'));
        HTMLCharacterHandler.nameToUnicodeValue.put("OElig", new Character('\u0152'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Oacute", new Character('\u00d3'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Ocirc", new Character('\u00d4'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Ograve", new Character('\u00d2'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Omega", new Character('\u03a9'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Omicron", new Character('\u039f'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Oslash", new Character('\u00d8'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Otilde", new Character('\u00d5'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Ouml", new Character('\u00d6'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Phi", new Character('\u03a6'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Pi", new Character('\u03a0'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Prime", new Character('\u2033'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Psi", new Character('\u03a8'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Rho", new Character('\u03a1'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Scaron", new Character('\u0160'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Sigma", new Character('\u03a3'));
        HTMLCharacterHandler.nameToUnicodeValue.put("THORN", new Character('\u00de'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Tau", new Character('\u03a4'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Theta", new Character('\u0398'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Uacute", new Character('\u00da'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Ucirc", new Character('\u00db'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Ugrave", new Character('\u00d9'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Upsilon", new Character('\u03a5'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Uuml", new Character('\u00dc'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Xi", new Character('\u039e'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Yacute", new Character('\u00dd'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Yuml", new Character('\u0178'));
        HTMLCharacterHandler.nameToUnicodeValue.put("Zeta", new Character('\u0396'));
        HTMLCharacterHandler.nameToUnicodeValue.put("aacute", new Character('\u00e1'));
        HTMLCharacterHandler.nameToUnicodeValue.put("acirc", new Character('\u00e2'));
        HTMLCharacterHandler.nameToUnicodeValue.put("acute", new Character('´'));
        HTMLCharacterHandler.nameToUnicodeValue.put("aelig", new Character('\u00e6'));
        HTMLCharacterHandler.nameToUnicodeValue.put("agrave", new Character('\u00e0'));
        HTMLCharacterHandler.nameToUnicodeValue.put("alefsym", new Character('\u2135'));
        HTMLCharacterHandler.nameToUnicodeValue.put("alpha", new Character('\u03b1'));
        HTMLCharacterHandler.nameToUnicodeValue.put("amp", new Character('&'));
        HTMLCharacterHandler.nameToUnicodeValue.put("and", new Character('\u2227'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ang", new Character('\u2220'));
        HTMLCharacterHandler.nameToUnicodeValue.put("aring", new Character('\u00e5'));
        HTMLCharacterHandler.nameToUnicodeValue.put("asymp", new Character('\u2248'));
        HTMLCharacterHandler.nameToUnicodeValue.put("atilde", new Character('\u00e3'));
        HTMLCharacterHandler.nameToUnicodeValue.put("auml", new Character('\u00e4'));
        HTMLCharacterHandler.nameToUnicodeValue.put("bdquo", new Character('\u201e'));
        HTMLCharacterHandler.nameToUnicodeValue.put("beta", new Character('\u03b2'));
        HTMLCharacterHandler.nameToUnicodeValue.put("brvbar", new Character('¦'));
        HTMLCharacterHandler.nameToUnicodeValue.put("bull", new Character('\u2022'));
        HTMLCharacterHandler.nameToUnicodeValue.put("cap", new Character('\u2229'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ccedil", new Character('\u00e7'));
        HTMLCharacterHandler.nameToUnicodeValue.put("cedil", new Character('¸'));
        HTMLCharacterHandler.nameToUnicodeValue.put("cent", new Character('¢'));
        HTMLCharacterHandler.nameToUnicodeValue.put("chi", new Character('\u03c7'));
        HTMLCharacterHandler.nameToUnicodeValue.put("circ", new Character('\u02c6'));
        HTMLCharacterHandler.nameToUnicodeValue.put("clubs", new Character('\u2663'));
        HTMLCharacterHandler.nameToUnicodeValue.put("cong", new Character('\u2245'));
        HTMLCharacterHandler.nameToUnicodeValue.put("copy", new Character('©'));
        HTMLCharacterHandler.nameToUnicodeValue.put("crarr", new Character('\u21b5'));
        HTMLCharacterHandler.nameToUnicodeValue.put("cup", new Character('\u222a'));
        HTMLCharacterHandler.nameToUnicodeValue.put("curren", new Character('¤'));
        HTMLCharacterHandler.nameToUnicodeValue.put("dArr", new Character('\u21d3'));
        HTMLCharacterHandler.nameToUnicodeValue.put("dagger", new Character('\u2020'));
        HTMLCharacterHandler.nameToUnicodeValue.put("darr", new Character('\u2193'));
        HTMLCharacterHandler.nameToUnicodeValue.put("deg", new Character('°'));
        HTMLCharacterHandler.nameToUnicodeValue.put("delta", new Character('\u03b4'));
        HTMLCharacterHandler.nameToUnicodeValue.put("diams", new Character('\u2666'));
        HTMLCharacterHandler.nameToUnicodeValue.put("divide", new Character('\u00f7'));
        HTMLCharacterHandler.nameToUnicodeValue.put("eacute", new Character('\u00e9'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ecirc", new Character('\u00ea'));
        HTMLCharacterHandler.nameToUnicodeValue.put("egrave", new Character('\u00e8'));
        HTMLCharacterHandler.nameToUnicodeValue.put("empty", new Character('\u2205'));
        HTMLCharacterHandler.nameToUnicodeValue.put("emsp", new Character('\u2003'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ensp", new Character('\u2002'));
        HTMLCharacterHandler.nameToUnicodeValue.put("epsilon", new Character('\u03b5'));
        HTMLCharacterHandler.nameToUnicodeValue.put("equiv", new Character('\u2261'));
        HTMLCharacterHandler.nameToUnicodeValue.put("eta", new Character('\u03b7'));
        HTMLCharacterHandler.nameToUnicodeValue.put("eth", new Character('\u00f0'));
        HTMLCharacterHandler.nameToUnicodeValue.put("euml", new Character('\u00eb'));
        HTMLCharacterHandler.nameToUnicodeValue.put("euro", new Character('\u20ac'));
        HTMLCharacterHandler.nameToUnicodeValue.put("exist", new Character('\u2203'));
        HTMLCharacterHandler.nameToUnicodeValue.put("fnof", new Character('\u0192'));
        HTMLCharacterHandler.nameToUnicodeValue.put("forall", new Character('\u2200'));
        HTMLCharacterHandler.nameToUnicodeValue.put("frac12", new Character('½'));
        HTMLCharacterHandler.nameToUnicodeValue.put("frac14", new Character('¼'));
        HTMLCharacterHandler.nameToUnicodeValue.put("frac34", new Character('¾'));
        HTMLCharacterHandler.nameToUnicodeValue.put("frasl", new Character('\u2044'));
        HTMLCharacterHandler.nameToUnicodeValue.put("gamma", new Character('\u03b3'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ge", new Character('\u2265'));
        HTMLCharacterHandler.nameToUnicodeValue.put("gt", new Character('>'));
        HTMLCharacterHandler.nameToUnicodeValue.put("hArr", new Character('\u21d4'));
        HTMLCharacterHandler.nameToUnicodeValue.put("harr", new Character('\u2194'));
        HTMLCharacterHandler.nameToUnicodeValue.put("hearts", new Character('\u2665'));
        HTMLCharacterHandler.nameToUnicodeValue.put("hellip", new Character('\u2026'));
        HTMLCharacterHandler.nameToUnicodeValue.put("iacute", new Character('\u00ed'));
        HTMLCharacterHandler.nameToUnicodeValue.put("icirc", new Character('\u00ee'));
        HTMLCharacterHandler.nameToUnicodeValue.put("iexcl", new Character('¡'));
        HTMLCharacterHandler.nameToUnicodeValue.put("igrave", new Character('\u00ec'));
        HTMLCharacterHandler.nameToUnicodeValue.put("image", new Character('\u2111'));
        HTMLCharacterHandler.nameToUnicodeValue.put("infin", new Character('\u221e'));
        HTMLCharacterHandler.nameToUnicodeValue.put("int", new Character('\u222b'));
        HTMLCharacterHandler.nameToUnicodeValue.put("iota", new Character('\u03b9'));
        HTMLCharacterHandler.nameToUnicodeValue.put("iquest", new Character('¿'));
        HTMLCharacterHandler.nameToUnicodeValue.put("isin", new Character('\u2208'));
        HTMLCharacterHandler.nameToUnicodeValue.put("iuml", new Character('\u00ef'));
        HTMLCharacterHandler.nameToUnicodeValue.put("kappa", new Character('\u03ba'));
        HTMLCharacterHandler.nameToUnicodeValue.put("lArr", new Character('\u21d0'));
        HTMLCharacterHandler.nameToUnicodeValue.put("lambda", new Character('\u03bb'));
        HTMLCharacterHandler.nameToUnicodeValue.put("lang", new Character('\u2329'));
        HTMLCharacterHandler.nameToUnicodeValue.put("laquo", new Character('«'));
        HTMLCharacterHandler.nameToUnicodeValue.put("larr", new Character('\u2190'));
        HTMLCharacterHandler.nameToUnicodeValue.put("lceil", new Character('\u2308'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ldquo", new Character('\u201c'));
        HTMLCharacterHandler.nameToUnicodeValue.put("le", new Character('\u2264'));
        HTMLCharacterHandler.nameToUnicodeValue.put("lfloor", new Character('\u230a'));
        HTMLCharacterHandler.nameToUnicodeValue.put("lowast", new Character('\u2217'));
        HTMLCharacterHandler.nameToUnicodeValue.put("loz", new Character('\u25ca'));
        HTMLCharacterHandler.nameToUnicodeValue.put("lrm", new Character('\u200e'));
        HTMLCharacterHandler.nameToUnicodeValue.put("lsaquo", new Character('\u2039'));
        HTMLCharacterHandler.nameToUnicodeValue.put("lsquo", new Character('\u2018'));
        HTMLCharacterHandler.nameToUnicodeValue.put("lt", new Character('<'));
        HTMLCharacterHandler.nameToUnicodeValue.put("macr", new Character('¯'));
        HTMLCharacterHandler.nameToUnicodeValue.put("mdash", new Character('\u2014'));
        HTMLCharacterHandler.nameToUnicodeValue.put("micro", new Character('µ'));
        HTMLCharacterHandler.nameToUnicodeValue.put("middot", new Character('·'));
        HTMLCharacterHandler.nameToUnicodeValue.put("minus", new Character('\u2212'));
        HTMLCharacterHandler.nameToUnicodeValue.put("mu", new Character('\u03bc'));
        HTMLCharacterHandler.nameToUnicodeValue.put("nabla", new Character('\u2207'));
        HTMLCharacterHandler.nameToUnicodeValue.put("nbsp", new Character(' '));
        HTMLCharacterHandler.nameToUnicodeValue.put("ndash", new Character('\u2013'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ne", new Character('\u2260'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ni", new Character('\u220b'));
        HTMLCharacterHandler.nameToUnicodeValue.put("not", new Character('¬'));
        HTMLCharacterHandler.nameToUnicodeValue.put("notin", new Character('\u2209'));
        HTMLCharacterHandler.nameToUnicodeValue.put("nsub", new Character('\u2284'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ntilde", new Character('\u00f1'));
        HTMLCharacterHandler.nameToUnicodeValue.put("nu", new Character('\u03bd'));
        HTMLCharacterHandler.nameToUnicodeValue.put("oacute", new Character('\u00f3'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ocirc", new Character('\u00f4'));
        HTMLCharacterHandler.nameToUnicodeValue.put("oelig", new Character('\u0153'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ograve", new Character('\u00f2'));
        HTMLCharacterHandler.nameToUnicodeValue.put("oline", new Character('\u203e'));
        HTMLCharacterHandler.nameToUnicodeValue.put("omega", new Character('\u03c9'));
        HTMLCharacterHandler.nameToUnicodeValue.put("omicron", new Character('\u03bf'));
        HTMLCharacterHandler.nameToUnicodeValue.put("oplus", new Character('\u2295'));
        HTMLCharacterHandler.nameToUnicodeValue.put("or", new Character('\u2228'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ordf", new Character('ª'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ordm", new Character('º'));
        HTMLCharacterHandler.nameToUnicodeValue.put("oslash", new Character('\u00f8'));
        HTMLCharacterHandler.nameToUnicodeValue.put("otilde", new Character('\u00f5'));
        HTMLCharacterHandler.nameToUnicodeValue.put("otimes", new Character('\u2297'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ouml", new Character('\u00f6'));
        HTMLCharacterHandler.nameToUnicodeValue.put("para", new Character('¶'));
        HTMLCharacterHandler.nameToUnicodeValue.put("part", new Character('\u2202'));
        HTMLCharacterHandler.nameToUnicodeValue.put("permil", new Character('\u2030'));
        HTMLCharacterHandler.nameToUnicodeValue.put("perp", new Character('\u22a5'));
        HTMLCharacterHandler.nameToUnicodeValue.put("phi", new Character('\u03c6'));
        HTMLCharacterHandler.nameToUnicodeValue.put("pi", new Character('\u03c0'));
        HTMLCharacterHandler.nameToUnicodeValue.put("piv", new Character('\u03d6'));
        HTMLCharacterHandler.nameToUnicodeValue.put("plusmn", new Character('±'));
        HTMLCharacterHandler.nameToUnicodeValue.put("pound", new Character('£'));
        HTMLCharacterHandler.nameToUnicodeValue.put("prime", new Character('\u2032'));
        HTMLCharacterHandler.nameToUnicodeValue.put("prod", new Character('\u220f'));
        HTMLCharacterHandler.nameToUnicodeValue.put("prop", new Character('\u221d'));
        HTMLCharacterHandler.nameToUnicodeValue.put("psi", new Character('\u03c8'));
        HTMLCharacterHandler.nameToUnicodeValue.put("quot", new Character('\"'));
        HTMLCharacterHandler.nameToUnicodeValue.put("rArr", new Character('\u21d2'));
        HTMLCharacterHandler.nameToUnicodeValue.put("radic", new Character('\u221a'));
        HTMLCharacterHandler.nameToUnicodeValue.put("rang", new Character('\u232a'));
        HTMLCharacterHandler.nameToUnicodeValue.put("raquo", new Character('»'));
        HTMLCharacterHandler.nameToUnicodeValue.put("rarr", new Character('\u2192'));
        HTMLCharacterHandler.nameToUnicodeValue.put("rceil", new Character('\u2309'));
        HTMLCharacterHandler.nameToUnicodeValue.put("rdquo", new Character('\u201d'));
        HTMLCharacterHandler.nameToUnicodeValue.put("real", new Character('\u211c'));
        HTMLCharacterHandler.nameToUnicodeValue.put("reg", new Character('®'));
        HTMLCharacterHandler.nameToUnicodeValue.put("rfloor", new Character('\u230b'));
        HTMLCharacterHandler.nameToUnicodeValue.put("rho", new Character('\u03c1'));
        HTMLCharacterHandler.nameToUnicodeValue.put("rlm", new Character('\u200f'));
        HTMLCharacterHandler.nameToUnicodeValue.put("rsaquo", new Character('\u203a'));
        HTMLCharacterHandler.nameToUnicodeValue.put("rsquo", new Character('\u2019'));
        HTMLCharacterHandler.nameToUnicodeValue.put("sbquo", new Character('\u201a'));
        HTMLCharacterHandler.nameToUnicodeValue.put("scaron", new Character('\u0161'));
        HTMLCharacterHandler.nameToUnicodeValue.put("sdot", new Character('\u22c5'));
        HTMLCharacterHandler.nameToUnicodeValue.put("sect", new Character('§'));
        HTMLCharacterHandler.nameToUnicodeValue.put("shy", new Character('\u00ad'));
        HTMLCharacterHandler.nameToUnicodeValue.put("sigma", new Character('\u03c3'));
        HTMLCharacterHandler.nameToUnicodeValue.put("sigmaf", new Character('\u03c2'));
        HTMLCharacterHandler.nameToUnicodeValue.put("sim", new Character('\u223c'));
        HTMLCharacterHandler.nameToUnicodeValue.put("spades", new Character('\u2660'));
        HTMLCharacterHandler.nameToUnicodeValue.put("sub", new Character('\u2282'));
        HTMLCharacterHandler.nameToUnicodeValue.put("sube", new Character('\u2286'));
        HTMLCharacterHandler.nameToUnicodeValue.put("sum", new Character('\u2211'));
        HTMLCharacterHandler.nameToUnicodeValue.put("sup", new Character('\u2283'));
        HTMLCharacterHandler.nameToUnicodeValue.put("sup1", new Character('¹'));
        HTMLCharacterHandler.nameToUnicodeValue.put("sup2", new Character('²'));
        HTMLCharacterHandler.nameToUnicodeValue.put("sup3", new Character('³'));
        HTMLCharacterHandler.nameToUnicodeValue.put("supe", new Character('\u2287'));
        HTMLCharacterHandler.nameToUnicodeValue.put("szlig", new Character('\u00df'));
        HTMLCharacterHandler.nameToUnicodeValue.put("tau", new Character('\u03c4'));
        HTMLCharacterHandler.nameToUnicodeValue.put("there4", new Character('\u2234'));
        HTMLCharacterHandler.nameToUnicodeValue.put("theta", new Character('\u03b8'));
        HTMLCharacterHandler.nameToUnicodeValue.put("thetasym", new Character('\u03d1'));
        HTMLCharacterHandler.nameToUnicodeValue.put("thinsp", new Character('\u2009'));
        HTMLCharacterHandler.nameToUnicodeValue.put("thorn", new Character('\u00fe'));
        HTMLCharacterHandler.nameToUnicodeValue.put("tilde", new Character('\u02dc'));
        HTMLCharacterHandler.nameToUnicodeValue.put("times", new Character('\u00d7'));
        HTMLCharacterHandler.nameToUnicodeValue.put("trade", new Character('\u2122'));
        HTMLCharacterHandler.nameToUnicodeValue.put("uArr", new Character('\u21d1'));
        HTMLCharacterHandler.nameToUnicodeValue.put("uacute", new Character('\u00fa'));
        HTMLCharacterHandler.nameToUnicodeValue.put("uarr", new Character('\u2191'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ucirc", new Character('\u00fb'));
        HTMLCharacterHandler.nameToUnicodeValue.put("ugrave", new Character('\u00f9'));
        HTMLCharacterHandler.nameToUnicodeValue.put("uml", new Character('¨'));
        HTMLCharacterHandler.nameToUnicodeValue.put("upsih", new Character('\u03d2'));
        HTMLCharacterHandler.nameToUnicodeValue.put("upsilon", new Character('\u03c5'));
        HTMLCharacterHandler.nameToUnicodeValue.put("uuml", new Character('\u00fc'));
        HTMLCharacterHandler.nameToUnicodeValue.put("weierp", new Character('\u2118'));
        HTMLCharacterHandler.nameToUnicodeValue.put("xi", new Character('\u03be'));
        HTMLCharacterHandler.nameToUnicodeValue.put("yacute", new Character('\u00fd'));
        HTMLCharacterHandler.nameToUnicodeValue.put("yen", new Character('¥'));
        HTMLCharacterHandler.nameToUnicodeValue.put("yuml", new Character('\u00ff'));
        HTMLCharacterHandler.nameToUnicodeValue.put("zeta", new Character('\u03b6'));
        HTMLCharacterHandler.nameToUnicodeValue.put("zwj", new Character('\u200d'));
        HTMLCharacterHandler.nameToUnicodeValue.put("zwnj", new Character('\u200c'));
    }
}
