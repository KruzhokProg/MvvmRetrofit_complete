package com.example.mvvmretrofit;

import android.os.Bundle;
import android.view.View;

public class McrkpoNewsListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcrkpo_news_list);

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mProgressBar.getVisibility() == View.VISIBLE){
                    showProgressBar(false);
                }
                else{
                    showProgressBar(true);
                }
            }
        });
    }
}
