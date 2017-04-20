package com.jspark.android.kardoc.domain;

import java.net.URL;

/**
 * Created by jsPark on 2017. 4. 17..
 */

public class Request {
    private String requestedby;
    private String brand;
    private String model;
    private String carnumber;
    private String broken1;
    private String broken2;
    private String broken3;
    private URL photo1;
    private URL photo2;
    private URL photo3;
    private String detail;
    private String number;
    private Boolean completed;
    private Estimation estimation;
    private Boolean insurancerepair;
    private Boolean rentcar;
    private Boolean pickup;
    private String carid;

    public String getRequestedby() {
        return requestedby;
    }

    public void setRequestedby(String requestedby) {
        this.requestedby = requestedby;
    }

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

    public Boolean getInsurancerepair() {
        return insurancerepair;
    }

    public void setInsurancerepair(Boolean insurancerepair) {
        this.insurancerepair = insurancerepair;
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

    public URL getPhoto1() {
        return photo1;
    }

    public void setPhoto1(URL photo1) {
        this.photo1 = photo1;
    }

    public URL getPhoto2() {
        return photo2;
    }

    public void setPhoto2(URL photo2) {
        this.photo2 = photo2;
    }

    public URL getPhoto3() {
        return photo3;
    }

    public void setPhoto3(URL photo3) {
        this.photo3 = photo3;
    }

    public Estimation getEstimation() {
        return estimation;
    }

    public void setEstimation(Estimation estimation) {
        this.estimation = estimation;
    }
}
