package com.topcoder.onlinereview.component.webcommon.model;

import java.io.Serializable;

public class ImageInfo implements Serializable {
    private int width = -1;
    private int height = -1;
    private String src = null;
    private String link = null;

    public ImageInfo() {
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSrc() {
        return this.src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
