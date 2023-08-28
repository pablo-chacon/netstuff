package com.example.netstuff.model;

public class CrawlerModel {
    private String rootURL;
    private int breakPoint;

    public CrawlerModel(String rootURL, int breakPoint) {
        this.rootURL = rootURL;
        this.breakPoint = breakPoint;
    }

    public String getRootURL() {
        return rootURL;
    }

    public void setRootURL(String rootURL) {
        this.rootURL = rootURL;
    }

    public int getBreakPoint() {
        return breakPoint;
    }

    public void setBreakPoint(int breakPoint) {
        this.breakPoint = breakPoint;
    }
}
