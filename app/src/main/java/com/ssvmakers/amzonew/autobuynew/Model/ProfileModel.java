package com.ssvmakers.amzonew.autobuynew.Model;

/**
 * Created by Shubham on 11/10/2017.
 */

public class ProfileModel {

    private String picUrl;
    private String name;
    private String phone;
    private String fburl;
    private String waUrl;

    public ProfileModel() {
    }

    public ProfileModel(String picUrl, String name, String phone, String fburl, String waUrl) {
        this.picUrl = picUrl;
        this.name = name;
        this.phone = phone;
        this.fburl = fburl;
        this.waUrl = waUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFburl() {
        return fburl;
    }

    public void setFburl(String fburl) {
        this.fburl = fburl;
    }

    public String getWaUrl() {
        return waUrl;
    }

    public void setWaUrl(String waUrl) {
        this.waUrl = waUrl;
    }
}
