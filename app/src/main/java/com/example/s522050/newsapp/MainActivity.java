package com.example.s522050.newsapp;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.s522050.newsapp.Adapter.ListSourceAdapter;
import com.example.s522050.newsapp.Common.Common;
import com.example.s522050.newsapp.Interface.NewsService;
import com.example.s522050.newsapp.Model.WebSite;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView listWebSite;
    RecyclerView.LayoutManager layoutManager;
    NewsService mService;
    ListSourceAdapter mAdapter;
    SpotsDialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init cache
        Paper.init(this);

        //Init Service
        mService = Common.getNewsService();

        //Init View
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebsiteSource(true);
            }
        });

        listWebSite = (RecyclerView)findViewById(R.id.list_source);
        listWebSite.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listWebSite.setLayoutManager(layoutManager);

        dialog = new SpotsDialog(this);

        loadWebsiteSource(false);
    }

    private void loadWebsiteSource(boolean isRefreshed) {

        if (!isRefreshed){
            String cache = Paper.book().read("cache");
            if(cache != null && !cache.isEmpty()){  // If have cache

                WebSite webSite = new Gson().fromJson(cache,WebSite.class); // Convert cache from Json to object
                mAdapter = new ListSourceAdapter(getBaseContext(),webSite);
                mAdapter.notifyDataSetChanged();
                listWebSite.setAdapter(mAdapter);
            }
            else{ //If not have cache
                dialog.show();
                //Fetch New data
                mService.getSources().enqueue(new Callback<WebSite>() {
                    @Override
                    public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                        dialog.dismiss();
                        mAdapter = new ListSourceAdapter(getBaseContext(),response.body());
                        mAdapter.notifyDataSetChanged();
                        listWebSite.setAdapter(mAdapter);

                        //Save to cache
                        Paper.book().write("cache", new Gson().toJson(response.body()));
                    }

                    @Override
                    public void onFailure(Call<WebSite> call, Throwable t) {

                    }
                });
            }
        }else{ //if swipe to refresh

            dialog.show();
            //Fetch new data
            mService.getSources().enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                    dialog.dismiss();
                    mAdapter = new ListSourceAdapter(getBaseContext(),response.body());
                    mAdapter.notifyDataSetChanged();
                    listWebSite.setAdapter(mAdapter);

                    //Save to cache
                    Paper.book().write("cache", new Gson().toJson(response.body()));


                    //Dismiss refresh progressing
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<WebSite> call, Throwable t) {

                }
            });

        }
    }
}
