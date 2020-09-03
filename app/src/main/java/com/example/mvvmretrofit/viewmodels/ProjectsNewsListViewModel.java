package com.example.mvvmretrofit.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmretrofit.models.ProjectsNews;
import com.example.mvvmretrofit.repositories.ProjectsNewsRepository;

import java.util.List;

public class ProjectsNewsListViewModel extends ViewModel {

    private ProjectsNewsRepository mProjectsNewsRepository;

    public ProjectsNewsListViewModel() {
        mProjectsNewsRepository = ProjectsNewsRepository.getInstance();
    }

    public LiveData<Boolean> isQueryExhausted(){
        return mProjectsNewsRepository.isQueryExhausted();
    }

    public void searchNextPage(){
        if(!isQueryExhausted().getValue()) {
            mProjectsNewsRepository.searchNextPage();
        }
    }

    public LiveData<List<ProjectsNews>> getProjectsNews(){
        return mProjectsNewsRepository.getProjectsNews();
    }

    public void getListProjectsNewsApi(int pageNumber, int pageLimit){
        mProjectsNewsRepository.getListProjectsNewsApi(pageNumber, pageLimit);
    }
}
