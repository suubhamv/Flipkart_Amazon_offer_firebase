package com.ssvmakers.amzonew.autobuynew.Model;

/**
 * Created by Shubham on 11/20/2017.
 */

public class SiteModel {
    private String title;
    private String url;
    private String imageUrl;

    public SiteModel() {
    }

    public SiteModel(String title, String url, String imageUrl) {
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
