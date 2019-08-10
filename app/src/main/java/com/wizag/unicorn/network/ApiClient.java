package com.wizag.unicorn.network;

import android.content.Context;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;


public class ApiClient {
    public static final String BASE_URL = "https://unicorn.wizag.co.ke/api/";

    /**
     * This client class i added some codes to increase the connection time out
     * */
    private static OkHttpClient getClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
        return builder.build();
    }

    /**
     * This return the retrofit instance
     * */
    public static Retrofit getRetrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient());
        return builder.build();
    }
}