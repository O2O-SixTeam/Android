package com.jspark.android.kardoc.domain;

/**
 * Created by jsPark on 2017. 4. 3..
 */

public class Result {
    String token;
    String non_field_errors;
    public String getToken(){
        return token;
    }
    public String getNon_field_errors() { return non_field_errors; }
}
