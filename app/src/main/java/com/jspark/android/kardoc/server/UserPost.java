package com.jspark.android.kardoc.server;

import com.jspark.android.kardoc.domain.Result;
import com.jspark.android.kardoc.domain.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by jsPark on 2017. 4. 3..
 */

public interface UserPost {
    @Headers("Content-Type:application/json")
    @POST("user/")
    Call<ResponseBody> createUser(
            @Body User user
    );

    @FormUrlEncoded
    @POST("token-auth/")
    Call<Result> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );
}
