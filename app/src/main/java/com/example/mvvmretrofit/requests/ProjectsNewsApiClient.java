package com.example.mvvmretrofit.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmretrofit.AppExecutors;
import com.example.mvvmretrofit.models.ProjectsNews;
import com.example.mvvmretrofit.responses.ProjectsNewsListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.mvvmretrofit.util.Constants.NETWORK_TIMEOUT;

public class ProjectsNewsApiClient {

    private static final String TAG = "ProjectsNewsApiClient";

    private static  ProjectsNewsApiClient instance;
    private MutableLiveData<List<ProjectsNews>> mProjectsNews;
    private RetrieveProjectsNewsRunnable mRetrieveProjectsNewsRunnable;

    public static ProjectsNewsApiClient getInstance(){
        if(instance == null){
            instance = new ProjectsNewsApiClient();
        }
        return instance;
    }

    private ProjectsNewsApiClient(){
        mProjectsNews = new MutableLiveData<>();
    }

    public LiveData<List<ProjectsNews>> getProjectsNews(){
        return mProjectsNews;
    }

    public void getListProjectsNewsApi(int pageNumber, int pageLimit){

        if(mRetrieveProjectsNewsRunnable != null){
            mRetrieveProjectsNewsRunnable = null;
        }
        mRetrieveProjectsNewsRunnable = new RetrieveProjectsNewsRunnable(pageNumber, pageLimit);
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveProjectsNewsRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {

                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    private class RetrieveProjectsNewsRunnable implements Runnable{

        private int pageNumber;
        private int pageLimit;
        boolean cancelRequest;

        public RetrieveProjectsNewsRunnable(int pageNumber, int pageLimit) {
            this.pageNumber = pageNumber;
            this.pageLimit = pageLimit;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getProjectsNews(pageNumber, pageLimit).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<ProjectsNews> list = new ArrayList<>(((ProjectsNewsListResponse)response.body()).getPosts());
                    if(pageNumber == 1){
                        mProjectsNews.postValue(list);
                    }
                    else{
                        List<ProjectsNews> currentProjectsNews = mProjectsNews.getValue();
                        currentProjectsNews.addAll(list);
                        mProjectsNews.postValue(currentProjectsNews);
                    }
                }
                else{
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mProjectsNews.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mProjectsNews.postValue(null);
            }
        }

        private Call<ProjectsNewsListResponse> getProjectsNews(int pageNumber, int pageLimit){
            return ServiceGenerator.getProjectsNewsApi().getProjectsNewsList(pageNumber, pageLimit);
        }

        private void cancelRequest(){
            Log.d(TAG, "cancelRequest: canceling the search request. ");
            cancelRequest = true;
        }
    }
}
