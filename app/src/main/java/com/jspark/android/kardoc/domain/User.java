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
    private String CustomID;
    private String password;

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

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getCustomID() {
        return CustomID;
    }

    public void setCustomID(String customID) {
        CustomID = customID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
