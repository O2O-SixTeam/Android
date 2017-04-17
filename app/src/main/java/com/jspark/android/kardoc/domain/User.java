package com.jspark.android.kardoc.domain;

/**
 * Created by jsPark on 2017. 4. 3..
 */

public class User {
    private String name;
    private String phone;
    private String gender;
    private String email;
    private String birth;
    private String username;
    private String password;
    private Shop[] shop;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public Shop[] getShop() { return shop; }

    public void setShop(Shop[] shop) { this.shop = shop; }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
