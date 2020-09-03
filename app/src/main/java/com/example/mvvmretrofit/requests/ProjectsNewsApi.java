package com.example.mvvmretrofit.requests;

import com.example.mvvmretrofit.models.ProjectsNews;
import com.example.mvvmretrofit.responses.ProjectsNewsResponse;
import com.example.mvvmretrofit.responses.ProjectsNewsListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProjectsNewsApi {

    @GET("api/projects_posts/v0/posts?order_by%5Bpublished_at%5D=desc")
    Call<ProjectsNewsListResponse> getProjectsNewsList(
            @Query("page") Integer page,
            @Query("page_limit") Integer page_limit);

    @GET("api/projects_posts/v0/posts/{id}")
    Call<ProjectsNewsResponse> getProjectsNews2(@Path("id") Integer id);

    @GET("api/projects_posts/v0/posts/{id}")
    Call<ProjectsNews> getProjectsNews(@Path("id") Integer id);
}
