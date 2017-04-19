package com.jspark.android.kardoc.domain;

import java.net.URL;

/**
 * Created by jsPark on 2017. 4. 19..
 */

public class Estimation {
    private URL url;
    private String owner;
    private String pk;
    private String targetrequest;
    private String noninsurancecost;
    private String insurancecost;
    private String detail;
    private Boolean completed;

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getTargetrequest() {
        return targetrequest;
    }

    public void setTargetrequest(String targetrequest) {
        this.targetrequest = targetrequest;
    }

    public String getNoninsurancecost() {
        return noninsurancecost;
    }

    public void setNoninsurancecost(String noninsurancecost) {
        this.noninsurancecost = noninsurancecost;
    }

    public String getInsurancecost() {
        return insurancecost;
    }

    public void setInsurancecost(String insurancecost) {
        this.insurancecost = insurancecost;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
