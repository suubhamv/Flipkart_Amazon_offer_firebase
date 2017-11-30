package com.ssvmakers.amzonew.autobuynew.Model;

/**
 * Created by Shubham on 11/5/2017.
 */

public class HomeOfferModel {
    private String productname;
    private String proprice;
    private String oldprice;
    private String newprice;
    private String imageurl;
    private String pageurl;
    private String src;
    private String off;

    public HomeOfferModel() {
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProprice() {
        return proprice;
    }

    public void setProprice(Object proprice) {
        if (!(proprice instanceof String)) {
            this.proprice = String.valueOf(proprice);
        } else {
            this.proprice = String.valueOf(proprice);
        }
    }

    public String getOldprice() {
        return oldprice;
    }

    public void setOldprice(Object oldprice) {
        if (!(oldprice instanceof String)) {
            this.oldprice = String.valueOf(oldprice);
        } else {
            this.oldprice = String.valueOf(oldprice);
        }
    }

    public String getNewprice() {
        return newprice;
    }

    public void setNewprice(Object newprice) {
        if (!(proprice instanceof String)) {
            this.newprice = String.valueOf(newprice);
        } else {
            this.newprice = String.valueOf(newprice);
        }
    }

    public String getimageurl() {
        return imageurl;
    }

    public void setimageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPageurl() {
        return pageurl;
    }

    public void setPageurl(String pageurl) {
        this.pageurl = pageurl;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getOff() {
        return off;
    }

    public void setOff(String off) {
        this.off = off;
    }
}
