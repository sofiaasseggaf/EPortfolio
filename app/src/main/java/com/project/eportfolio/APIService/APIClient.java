package com.project.eportfolio.APIService;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.eportfolio.utility.StringConverter;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 1/10/2018.
 */

public class APIClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {


     Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain
                        .request()
                        .newBuilder()
                        .addHeader("X-Api-Key", "7826470ABBA476706DB24D47C6A6ED64")
//                        .addHeader("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7ImlkIjoiMSJ9LCJpYXQiOjE1ODAwMTI5MTAsImV4cCI6MTU4MDAxNDcxMH0.ZNIc9vEGe2I_toLGN7rJ_e9HZY895dqt-gs-uxGXBvQ")
                        .build();
                return chain.proceed(newRequest);
            }
        };


        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);*/
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(String.class, new StringConverter());
        Gson gson = gb.create();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://eportofolio.id/")
                .addConverterFactory(GsonConverterFactory.create(gson))

                .client(client)
                .build();

        return retrofit;
    }
}
