package com.example.owen.weathergo.stud;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by owen on 2017/4/9.
 */

public interface GitHubClient {


    @GET("/user/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(
        @Path("user") String user);

}
