package com.topcoder.onlinereview.component.webcommon.model;

import java.io.Serializable;
import java.util.HashMap;

public class SortInfo implements Serializable {
    public static final String REQUEST_KEY = "sortInfo";
    private HashMap map = new HashMap();

    public SortInfo() {
    }

    public void addDefault(int col, String dir) {
        this.map.put(new Integer(col), dir);
    }

    public String getDefault(int col) {
        return (String)this.map.get(new Integer(col));
    }
}
