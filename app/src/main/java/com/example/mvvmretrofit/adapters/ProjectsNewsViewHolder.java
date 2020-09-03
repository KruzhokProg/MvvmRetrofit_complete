package com.example.mvvmretrofit.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmretrofit.R;

public class ProjectsNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView img;
    public TextView anounce, publishedDate, title;
    public CardView cardView;
    OnProjectsNewsListener onProjectsNewsListener;

    public ProjectsNewsViewHolder(@NonNull View itemView, OnProjectsNewsListener onProjectsNewsListener) {
        super(itemView);

        this.onProjectsNewsListener = onProjectsNewsListener;
        img = itemView.findViewById(R.id.img_projects_news);
        anounce = itemView.findViewById(R.id.anounce_projects_news);
        publishedDate = itemView.findViewById(R.id.published_date_projects_news);
        title = itemView.findViewById(R.id.title_projects_news);
        cardView = itemView.findViewById(R.id.projectsNewsCardView);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onProjectsNewsListener.onProjectsNewsClick(getAdapterPosition());
    }
}
