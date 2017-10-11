package com.example.s522050.newsapp.Common;


import com.example.s522050.newsapp.Interface.IconBetterIdeaService;
import com.example.s522050.newsapp.Interface.NewsService;
import com.example.s522050.newsapp.Remote.IconBetterIdeaClient;
import com.example.s522050.newsapp.Remote.RetrofitClient;

public class Common {

    private static final String BASE_URL = "https://newsapi.org/";
    public static final String API_KEY = "1f1fcf6cf15640959e6f783d41f84ba5";

    public static NewsService getNewsService() {

        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    public static IconBetterIdeaService getIconService() {

        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }

    // https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=1f1fcf6cf15640959e6f783d41f84ba5
    public static String getAPIUrl(String source, String sortBy, String apiKey) {

        StringBuilder apiBuilder = new StringBuilder("https://newsapi.org/v1/articles?source=");
        return apiBuilder.append(source)
                .append("&sortBy=")
                .append(sortBy)
                .append("&apiKey=")
                .append(API_KEY)
                .toString();
    }
}
