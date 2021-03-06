package com.example.user.myapplication;

import com.example.user.myapplication.models.JSONResponse;
import com.example.user.myapplication.models.Job;
import com.example.user.myapplication.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JobApi {
    @POST("addJob")
    Call<Job> addJob(@Body Job job);
    @GET("getJobs")
    Call<List<Job>> getJobs();
}
