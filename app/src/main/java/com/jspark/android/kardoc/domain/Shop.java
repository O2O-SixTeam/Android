package com.jspark.android.kardoc.domain;

import retrofit2.http.Url;

/**
 * Created by Songmoo on 2017-04-04.
 */

public class Shop {
    private Url url;
    private String shopname;
    private String address;
    private String zone;
    private String detail;
    private String number;
    private String longitude;
    private String latitude;
    private String bnumber;

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getBnumber() {
        return bnumber;
    }

    public void setBnumber(String bnumber) {
        this.bnumber = bnumber;
    }

    public Url getUrl() { return url; }

    public void setUrl(Url url) { this.url = url; }
}
