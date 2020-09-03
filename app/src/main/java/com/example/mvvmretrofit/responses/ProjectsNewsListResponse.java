package com.example.mvvmretrofit.responses;

import com.example.mvvmretrofit.models.ProjectsNews;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectsNewsListResponse {

    @SerializedName("pages")
    private int pages;

    @SerializedName("posts")
    private List<ProjectsNews> posts;

    public List<ProjectsNews> getPosts(){
        return posts;
    }

    @Override
    public String toString() {
        return "ProjectsNewsSearchResponse{" +
                "pages=" + pages +
                ", posts=" + posts +
                '}';
    }
}
