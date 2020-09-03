package com.example.mvvmretrofit.requests;

import com.example.mvvmretrofit.util.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

//    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL_SCHOOL_MOSCOW)
//            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit;
//
//    private static ProjectsNewsApi projectsNewsApi = retrofit.create(ProjectsNewsApi.class);

    public static ProjectsNewsApi getProjectsNewsApi() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL_SCHOOL_MOSCOW)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(httpClient.build())
                            .build();

        return retrofit.create(ProjectsNewsApi.class);
    }
}
