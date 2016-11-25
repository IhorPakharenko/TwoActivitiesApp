package com.example.isao.twoactivities2;

import com.example.isao.twoactivities2.model.GoogleUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GoogleRequestInterface {
    @GET("plus/v1/people/{user}?key=AIzaSyC0y5Lh1Qlhmyb04F4jtcSX4MGEJEgbuBw")
    Call<GoogleUser> getJSON(@Path("user") String user);
}