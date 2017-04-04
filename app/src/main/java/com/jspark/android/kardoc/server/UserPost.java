package com.jspark.android.kardoc.server;

import com.jspark.android.kardoc.domain.Result;
import com.jspark.android.kardoc.domain.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by jsPark on 2017. 4. 3..
 */

public interface UserPost {
    @Headers("Content-Type:application/json")
    @POST("user/")
    Call<Result> createUser(
            @Body User user
    );
}
