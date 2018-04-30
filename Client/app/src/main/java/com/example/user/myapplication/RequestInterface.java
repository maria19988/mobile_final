package com.example.user.myapplication;

import com.example.user.myapplication.models.JSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("android/jsonandroid")
    Call<JSONResponse> getJSON();
}
