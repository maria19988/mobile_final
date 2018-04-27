package com.example.user.myapplication.authenticated;

//import com.example.myevents.models.Event;
import com.example.user.myapplication.models.User;
import com.example.user.myapplication.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface AuthenticatedApi {

    @GET("profile")
    Call<User> getProfile();

    /*@POST("events")
    Call<List<Event>> createNewEvent(@Body Event event);

    @GET("events")
    Call<List<Event>> getEvents();*/
}
