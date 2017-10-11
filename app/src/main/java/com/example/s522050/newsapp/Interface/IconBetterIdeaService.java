package com.example.s522050.newsapp.Interface;


import com.example.s522050.newsapp.Model.IconBetterIdea;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IconBetterIdeaService {

    @GET
    Call<IconBetterIdea> getIconUrl(@Url String url);
}
