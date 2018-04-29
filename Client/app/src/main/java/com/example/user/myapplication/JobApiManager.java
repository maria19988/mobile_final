package com.example.user.myapplication;

import com.example.user.myapplication.models.Job;
import com.example.user.myapplication.models.User;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobApiManager {
    private Retrofit retrofit;
    private OkHttpClient client;
    private JobApi jobApi;

    private static JobApiManager jobApiManager;

    private JobApiManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jobApi = retrofit.create(JobApi.class);
    }

    public static JobApiManager getInstance() {
        if (jobApiManager == null) {
            jobApiManager = new JobApiManager();
        }
        return jobApiManager;
    }

    public Call<Job> addJob(Job job) {
        return jobApi.addJob(job);
    }



}
