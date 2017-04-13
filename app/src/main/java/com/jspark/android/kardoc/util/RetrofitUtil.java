package com.jspark.android.kardoc.util;

import com.jspark.android.kardoc.server.ApiServices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jsPark on 2017. 4. 13..
 */

public class RetrofitUtil {

    private static RetrofitUtil instance = null;

    private static ApiServices apiServices = null;

    private RetrofitUtil() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.kardoc.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiServices = retrofit.create(ApiServices.class);
    }

    public static RetrofitUtil getInstance() {
        if(instance==null) {
            instance = new RetrofitUtil();
        }
        return instance;
    }

    public static ApiServices getApiServices() {
        return apiServices;
    }


}
