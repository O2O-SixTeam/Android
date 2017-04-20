package com.jspark.android.kardoc.server;

import com.jspark.android.kardoc.domain.Request;
import com.jspark.android.kardoc.domain.Result;
import com.jspark.android.kardoc.domain.Shop;
import com.jspark.android.kardoc.domain.User;

import java.util.List;
import java.util.Map;

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
import retrofit2.http.QueryMap;

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
    Call<ResponseBody> createShop(
            @Header("Authorization") String token,
            @Body Shop shop
    );

    // 견적요청서 생성
    @FormUrlEncoded
    @POST("request/")
    Call<ResponseBody> request(
            @Header("Authorization") String token,
            @Field("brand") String brand,
            @Field("model") String model,
            @Field("carnumber") String carnumber,
            @Field("broken1") String broken1,
            @Field("broken2") String broken2,
            @Field("broken3") String broken3,
            @Field("detail") String detail,
            @Field("number") String number,
            @Field("completed") Boolean completed,
            @Field("insurancerepair") Boolean insurancerepair,
            @Field("rentcar") Boolean rentcar,
            @Field("pickup") Boolean pickup,
            @Field("carid") String carvin
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

    // 견적 요청서 열람
    @GET("request/")
    Call<List<Request>> loadRequests();

    // 파손 부위별 견적 요청서 열람
    @GET("request/")
    Call<List<Request>> loadCase(
            @QueryMap Map<String, String> options
    );
}
