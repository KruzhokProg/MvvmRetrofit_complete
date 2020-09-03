package com.example.mvvmretrofit.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.mvvmretrofit.models.ProjectsNews;
import com.example.mvvmretrofit.requests.ProjectsNewsApiClient;

import java.util.List;

import static com.example.mvvmretrofit.util.Constants.PAGE_LIMIT;

public class ProjectsNewsRepository {

    private static ProjectsNewsRepository instance;
    private ProjectsNewsApiClient mProjectsNewsApiClient;
    private int mPageNumber;
    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<ProjectsNews>> mProjectsNews = new MediatorLiveData<>();

    public static ProjectsNewsRepository getInstance(){
        if(instance == null){
            instance = new ProjectsNewsRepository();
        }
        return instance;
    }

    private void initMediators(){
        LiveData<List<ProjectsNews>> projectsNewsListApiSource = mProjectsNewsApiClient.getProjectsNews();
        mProjectsNews.addSource(projectsNewsListApiSource, new Observer<List<ProjectsNews>>() {
            @Override
            public void onChanged(List<ProjectsNews> projectsNews) {
                if(projectsNews != null){
                    mProjectsNews.setValue(projectsNews);
                    doneQuery(projectsNews);
                }
                else{
                    // проверяем кэш бд
                    doneQuery(null);
                }
            }
        });
    }

    private void doneQuery(List<ProjectsNews> list){
        if(list != null){
            if(list.size() % PAGE_LIMIT != 0){
                mIsQueryExhausted.setValue(true);
            }
        }
        else{
            mIsQueryExhausted.setValue(true);
        }
    }

    public LiveData<Boolean> isQueryExhausted(){
        return mIsQueryExhausted;
    }

    private ProjectsNewsRepository(){
        mProjectsNewsApiClient = ProjectsNewsApiClient.getInstance();
        initMediators();
    }

    public LiveData<List<ProjectsNews>> getProjectsNews(){
        //return mProjectsNewsApiClient.getProjectsNews();
        return mProjectsNews;
    }

    public void getListProjectsNewsApi(int pageNumber, int pageLimit){
        mPageNumber = pageNumber;
        mIsQueryExhausted.setValue(false);
        mProjectsNewsApiClient.getListProjectsNewsApi(pageNumber, pageLimit);
    }

    public void searchNextPage(){
        getListProjectsNewsApi(mPageNumber + 1, PAGE_LIMIT);
    }
}
