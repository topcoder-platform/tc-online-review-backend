package com.topcoder.onlinereview.component.webcommon;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public interface TCRequest extends HttpServletRequest {
    public Cookie getCookie(String name);
}
