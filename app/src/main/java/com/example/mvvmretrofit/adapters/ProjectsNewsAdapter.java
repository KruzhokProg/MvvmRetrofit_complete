package com.example.mvvmretrofit.adapters;

import android.content.Context;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvvmretrofit.R;
import com.example.mvvmretrofit.models.ProjectsNews;
import com.example.mvvmretrofit.util.CircleTransform;

import java.util.ArrayList;
import java.util.List;

import static com.example.mvvmretrofit.util.Constants.IMAGE_CONF;
import static com.example.mvvmretrofit.util.Constants.IMAGE_PATH;

public class ProjectsNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int PROJECTS_NEWS_TYPE = 1;
    public static final int LOADING_TYPE = 2;
    public static final int EXHAUSTED_TYPE = 3;

    private List<ProjectsNews> mProjectsNewsList;
    private OnProjectsNewsListener mOnProjectsNewsListener;
    private Context mContext;

    public ProjectsNewsAdapter(OnProjectsNewsListener mOnProjectsNewsListener) {
        this.mOnProjectsNewsListener = mOnProjectsNewsListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        mContext = parent.getContext();

        switch (viewType) {
            case PROJECTS_NEWS_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_projects_news_list_item, parent, false);
                return new ProjectsNewsViewHolder(view, mOnProjectsNewsListener);
            }
            case EXHAUSTED_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_exhausted, parent, false);
                return new SearchExhaustedViewHolder(view);
            }
            case LOADING_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list_item, parent, false);
                return new LoadingViewHolder(view);
            }
            default:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_projects_news_list_item, parent, false);
                return new ProjectsNewsViewHolder(view, mOnProjectsNewsListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if(itemViewType == PROJECTS_NEWS_TYPE){
            ProjectsNewsViewHolder h = ((ProjectsNewsViewHolder)holder);
            h.title.setText(mProjectsNewsList.get(position).getTitle());
            h.anounce.setText(mProjectsNewsList.get(position).getAnounce());
            h.publishedDate.setText(mProjectsNewsList.get(position).getPublished_at());

            h.img.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));
            h.cardView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));

            Uri imageUri = Uri.parse(IMAGE_PATH + mProjectsNewsList.get(position).getId() + IMAGE_CONF);

            Glide.with(holder.itemView.getContext())
                    .load(imageUri)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(new CircleTransform(3.0f/5))
                    .into(h.img);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(mProjectsNewsList.get(position).getTitle().equals("LOADING...")){
            return LOADING_TYPE;
        }
        else if(position == mProjectsNewsList.size()-1 &&
                position!=0 &&
                !mProjectsNewsList.get(position).getTitle().equals("EXHAUSTED...")){
            return LOADING_TYPE;
        }
        else if(mProjectsNewsList.get(position).getTitle().equals("EXHAUSTED...")){
            return EXHAUSTED_TYPE;
        }
        else{
            return PROJECTS_NEWS_TYPE;
        }
    }

    public void setQueryExhausted(){
        hideLoading();
        ProjectsNews exhaustedProjectsNews = new ProjectsNews();
        exhaustedProjectsNews.setTitle("EXHAUSTED...");
        mProjectsNewsList.add(exhaustedProjectsNews);
        notifyDataSetChanged();
    }

    private void hideLoading(){
        if(isLoading()){
            for(ProjectsNews item: mProjectsNewsList){
                if(item.getTitle().equals("LOADING...")){
                    mProjectsNewsList.remove(item);
                }
            }
            notifyDataSetChanged();
        }
    }

    public void displayLoading(){
        if(!isLoading()){
            ProjectsNews projectsNews = new ProjectsNews();
            projectsNews.setTitle("LOADING...");
            List<ProjectsNews> loadingList = new ArrayList<>();
            loadingList.add(projectsNews);
            mProjectsNewsList = loadingList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading(){
        if(mProjectsNewsList != null) {
            if (mProjectsNewsList.get(mProjectsNewsList.size() - 1).getTitle().equals("LOADING...")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if(mProjectsNewsList != null) {
            return mProjectsNewsList.size();
        }
        return 0;
    }

    public void setProjectsNewsList(List<ProjectsNews> projectsNewsList){
        mProjectsNewsList = projectsNewsList;
        notifyDataSetChanged();
    }

    public ProjectsNews getSelectedProjectsNews(int position){
        if(mProjectsNewsList != null){
            if(mProjectsNewsList.size() > 0){
                return mProjectsNewsList.get(position);
            }
        }
        return null;
    }
}
