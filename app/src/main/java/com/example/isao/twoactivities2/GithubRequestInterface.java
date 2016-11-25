package com.example.isao.twoactivities2;

import com.example.isao.twoactivities2.model.GithubUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubRequestInterface {
    @GET("users/{user}")
    Call<GithubUser> getJSON(@Path("user") String user);
}
