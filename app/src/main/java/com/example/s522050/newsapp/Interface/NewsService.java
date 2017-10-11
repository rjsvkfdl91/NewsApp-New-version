package com.example.s522050.newsapp.Interface;


import com.example.s522050.newsapp.Model.News;
import com.example.s522050.newsapp.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsService {

    @GET("v1/sources?language=en")
    Call<WebSite> getSources();

    @GET
    Call<News> getNewestArticles(@Url String url);
}
