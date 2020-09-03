package com.example.mvvmretrofit.responses;

import com.example.mvvmretrofit.models.ProjectsNews;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectsNewsResponse {

    private ProjectsNews post;

    public ProjectsNews getPost() {
        return post;
    }

    @Override
    public String toString() {
        return "ProjectsNewsResponse{" +
                "post=" + post +
                '}';
    }
}
