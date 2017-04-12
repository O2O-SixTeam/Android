package com.jspark.android.kardoc.server;

import com.jspark.android.kardoc.domain.Request;
import com.jspark.android.kardoc.domain.Result;
import com.jspark.android.kardoc.domain.Shop;
import com.jspark.android.kardoc.domain.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by jsPark on 2017. 4. 3..
 */

public interface ApiServices {
    // 유저 생성
    @Headers("Content-Type:application/json")
    @POST("user/")
    Call<ResponseBody> createUser(
            @Body User user
    );

    // 로그인
    @FormUrlEncoded
    @POST("token-auth/")
    Call<Result> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );

    // 공업사 생성
    @POST("shop/")
    Call<ResponseBody> createShop(
            @Header("Authorization") String token,
            @Body Shop shop
    );

    // 견적요청서 생성
    @Headers("Authorization: Token {token}")
    @POST("request/")
    Call<ResponseBody> request(
            @Path("token") String token,
            @Body Request request
    );
}
