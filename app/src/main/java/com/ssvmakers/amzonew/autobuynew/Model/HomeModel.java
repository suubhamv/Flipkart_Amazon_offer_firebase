package com.ssvmakers.amzonew.autobuynew.Model;

/**
 * Created by Shubham on 11/4/2017.
 */

public class HomeModel {
    private String imageUrl;
    private String title;
    private String url;

    public HomeModel(String imageUrl, String title, String url) {
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public HomeModel() {

    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getTitle() {
        return title;
    }
}
