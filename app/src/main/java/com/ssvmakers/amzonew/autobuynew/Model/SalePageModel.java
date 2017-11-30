package com.ssvmakers.amzonew.autobuynew.Model;

/**
 * Created by Shubham on 11/6/2017.
 */

public class SalePageModel {
    private String imageurl;
    private String pageurl;
    private String modelname;

    public SalePageModel() {
    }

    public SalePageModel(String imageurl, String pageurl, String modelname) {
        this.imageurl = imageurl;
        this.pageurl = pageurl;
        this.modelname = modelname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPageurl() {
        return pageurl;
    }

    public void setPageurl(String pageurl) {
        this.pageurl = pageurl;
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }
}
