package com.example.mvvmretrofit;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmretrofit.adapters.OnProjectsNewsListener;
import com.example.mvvmretrofit.adapters.ProjectsNewsAdapter;
import com.example.mvvmretrofit.models.ProjectsNews;
import com.example.mvvmretrofit.requests.ProjectsNewsApi;
import com.example.mvvmretrofit.requests.ServiceGenerator;
import com.example.mvvmretrofit.responses.ProjectsNewsListResponse;
import com.example.mvvmretrofit.responses.ProjectsNewsResponse;
import com.example.mvvmretrofit.viewmodels.ProjectsNewsListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mvvmretrofit.util.Constants.PAGE_LIMIT;

public class ProjectsNewsListActivity extends BaseActivity implements OnProjectsNewsListener {

    private static final String TAG = "ProjectsNewsListActivity";

    private ProjectsNewsListViewModel mProjectsNewsListViewModel;
    private RecyclerView rv;
    private ProjectsNewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_news_list);

        rv = findViewById(R.id.rv_projects_news);
        mProjectsNewsListViewModel = new ViewModelProvider(this).get(ProjectsNewsListViewModel.class);

        initRecyclerView();
        subscribeObservers();
        testRetrofitRequest();
//        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                testRetrofitRequest();
////                testRetrofitRequestList();
//            }
//        });
    }

    private void subscribeObservers(){
        mProjectsNewsListViewModel.getProjectsNews().observe(this, new Observer<List<ProjectsNews>>() {
            @Override
            public void onChanged(List<ProjectsNews> projectsNews) {
                if(projectsNews != null) {
                    for (ProjectsNews item : projectsNews) {
                        Log.d(TAG, "onChanged: " + item.getTitle());
                    }
                    mAdapter.setProjectsNewsList(projectsNews);
                }
            }
        });

        mProjectsNewsListViewModel.isQueryExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Log.d(TAG, "onChanged: the query is exhausted...");
                    mAdapter.setQueryExhausted();
                }
            }
        });
    }

    private void initRecyclerView(){

        mAdapter = new ProjectsNewsAdapter(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
        // анимация загрузки данных
        mAdapter.displayLoading();

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!rv.canScrollVertically(1)){
                    // подгружаем следующую страницу
                    mProjectsNewsListViewModel.searchNextPage();
                }
            }
        });
    }

    private void getListProjectsNewsApi(int pageNumber, int pageLimit){
        mProjectsNewsListViewModel.getListProjectsNewsApi(pageNumber, pageLimit);
    }


    private void testRetrofitRequest(){
        getListProjectsNewsApi(1,PAGE_LIMIT);
    }

    @Override
    public void onProjectsNewsClick(int position) {
        Intent intent = new Intent(this, ProjectsNewsDetailActivity.class);
        intent.putExtra("projectsNews", mAdapter.getSelectedProjectsNews(position));
        startActivity(intent);
    }

//    private void testRetrofitRequest(){
//        ProjectsNewsApi projectsNewsApi = ServiceGenerator.getProjectsNewsApi();
//        Call<ProjectsNews> call = projectsNewsApi.getProjectsNews(144);
//        call.enqueue(new Callback<ProjectsNews>() {
//            @Override
//            public void onResponse(Call<ProjectsNews> call, Response<ProjectsNews> response) {
//                Log.d(TAG, "ответ сервера: " + response.toString());
//                if(response.code() == 200){
//                    Log.d(TAG, "ответ сервера: " + response.body().toString());
//                }else{
//                    Log.d(TAG, response.errorBody().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProjectsNews> call, Throwable t) {
//
//            }
//        });
//    }

//    private void testRetrofitRequestList(){
//        ProjectsNewsApi projectsNewsApi = ServiceGenerator.getProjectsNewsApi();
//
//        Call<ProjectsNewsListResponse> call = projectsNewsApi.getProjectsNewsList(2, 20);
//        call.enqueue(new Callback<ProjectsNewsListResponse>() {
//            @Override
//            public void onResponse(Call<ProjectsNewsListResponse> call, Response<ProjectsNewsListResponse> response) {
//                Log.d(TAG, "ответ сервера: " + response.toString());
//                if(response.code() == 200){
//                    Log.d(TAG, "ответ сервера: " + response.body().toString());
//                    List<ProjectsNews> projectsNews = new ArrayList<>(response.body().getPosts());
//                    for (ProjectsNews item: projectsNews){
//                        Log.d(TAG, "заголовок: " + item.getTitle());
//                    }
//                }
//                else{
//                        Log.d(TAG, response.errorBody().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProjectsNewsListResponse> call, Throwable t) {
//
//            }
//        });
//    }
}
