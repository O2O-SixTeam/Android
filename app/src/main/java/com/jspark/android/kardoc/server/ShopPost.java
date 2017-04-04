package com.jspark.android.kardoc.server;

import com.jspark.android.kardoc.domain.Result;
import com.jspark.android.kardoc.domain.Shop;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Songmoo on 2017-04-04.
 */

public interface ShopPost {
    @Headers("Content-Type:application/json")
    @POST("shop/")
    Call<Result> createUser(
            @Body Shop shop
    );
}
