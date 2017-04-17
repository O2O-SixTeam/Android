package com.jspark.android.kardoc.server;

import com.jspark.android.kardoc.domain.Estimation;
import com.jspark.android.kardoc.domain.Result;
import com.jspark.android.kardoc.domain.Shop;
import com.jspark.android.kardoc.domain.User;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by jsPark on 2017. 4. 3..
 */

public interface ApiServices {
    // 유저 생성
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
    Call<Shop> createShop(
            @Header("Authorization") String token,
            @Body Shop shop
    );

    // 견적요청서 생성
    @POST("request/")
    Call<ResponseBody> request(
            @Header("Authorization") String token,
            @Body Estimation request
    );

    // 이미지 전송
    @POST("shop/")
    Call<ResponseBody> uploadImg(
            @Header("Authorization") String token,
            @Part MultipartBody.Part image1,
            @Part MultipartBody.Part image2,
            @Part MultipartBody.Part image3
    );

    @PUT("shop/{shopId}/")
    Call<Shop> uploadmovie(
            @Header("Authorization") String token,
            //ShopName은 필수값입니다.
            @Field("shopname") String shopName,
            @Field("video") String url
    );

}
