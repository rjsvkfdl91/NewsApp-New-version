package com.example.s522050.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.s522050.newsapp.Common.ISO8601Parse;
import com.example.s522050.newsapp.DetailActivity;
import com.example.s522050.newsapp.Interface.ItemClickListener;
import com.example.s522050.newsapp.Model.Article;
import com.example.s522050.newsapp.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class ListNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ItemClickListener itemClickListener;

    TextView article_title;
    RelativeTimeTextView article_time;
    CircleImageView article_image;

    public ListNewsViewHolder(View itemView) {
        super(itemView);

        article_image = itemView.findViewById(R.id.article_image);
        article_title = itemView.findViewById(R.id.article_title);
        article_time = itemView.findViewById(R.id.article_time);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}


public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsViewHolder> {

    private List<Article> articles;
    private Context context;

    public ListNewsAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @Override
    public ListNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_layout,parent,false);
        return new ListNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListNewsViewHolder holder, int position) {

        Picasso.with(context)
                .load(articles.get(position).getUrlToImage())
                .into(holder.article_image);

        if (articles.get(position).getTitle().length() > 65){
            holder.article_title.setText(articles.get(position).getTitle().substring(0,65)+context.getString(R.string.dot));
        }else
            holder.article_title.setText(articles.get(position).getTitle());

        Date date = null;
        try{
            date = ISO8601Parse.parse(articles.get(position).getPublishedAt());
        }catch (ParseException e){
            e.printStackTrace();
        }

        holder.article_time.setReferenceTime(date.getTime());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                Intent detailArticle = new Intent(context,DetailActivity.class);
                detailArticle.putExtra("webURL",articles.get(position).getUrl());
                context.startActivity(detailArticle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
