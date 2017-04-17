package com.jspark.android.kardoc.domain;

import retrofit2.http.Url;

/**
 * Created by jsPark on 2017. 4. 17..
 */

public class Estimation {
    private String brand;
    private String model;
    private String carnumber;
    private String broken1;
    private String broken2;
    private String broken3;
    private String detail;
    private String extra;
    private String number;
    private Boolean completed;
    private Boolean insurancerepait;
    private Boolean rentcar;
    private Boolean pickup;
    private String carid;
    private Url estimate;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }

    public String getBroken1() {
        return broken1;
    }

    public void setBroken1(String broken1) {
        this.broken1 = broken1;
    }

    public String getBroken2() {
        return broken2;
    }

    public void setBroken2(String broken2) {
        this.broken2 = broken2;
    }

    public String getBroken3() {
        return broken3;
    }

    public void setBroken3(String broken3) {
        this.broken3 = broken3;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Url getEstimate() {
        return estimate;
    }

    public void setEstimate(Url estimate) {
        this.estimate = estimate;
    }

    public Boolean getInsurancerepait() {
        return insurancerepait;
    }

    public void setInsurancerepait(Boolean insurancerepait) {
        this.insurancerepait = insurancerepait;
    }

    public Boolean getRentcar() {
        return rentcar;
    }

    public void setRentcar(Boolean rentcar) {
        this.rentcar = rentcar;
    }

    public Boolean getPickup() {
        return pickup;
    }

    public void setPickup(Boolean pickup) {
        this.pickup = pickup;
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }
}
