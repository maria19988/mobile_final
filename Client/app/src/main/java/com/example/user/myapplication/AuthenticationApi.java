package com.example.user.myapplication;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationApi
{
    @POST("register")
    Call<User> register(@Body User user);

    @POST("login")
    Call<User> login(@Body User user);

}
