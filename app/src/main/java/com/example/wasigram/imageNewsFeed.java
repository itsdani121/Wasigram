package com.example.wasigram;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;


public class imageNewsFeed extends RecyclerView.ViewHolder {
    ImageView dpImage, viewPager;
    TextView title, likes, description;
    Context context;
    private View parent;
    String Json_Url = "https://wasisoft.com/dev/";
    public  RequestManager requestManager;
    public imageNewsFeed(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        dpImage = itemView.findViewById(R.id.profile_image_images_news_feed);
        viewPager = itemView.findViewById(R.id.profile_image_viewPage_news_feed);
        title = itemView.findViewById(R.id.profile_image_name_news_feed);
        likes = itemView.findViewById(R.id.profile_image_like_count_news_feed);
        description = itemView.findViewById(R.id.profile_image_comments_news_feed);

    }


    public void onBind(newsFeedModel feed, int position,RequestManager requestManager) {
        this.requestManager=requestManager;
        title.setText(feed.getTitleName());
        likes.setText(feed.getLike());
        description.setText(feed.getDescription());
        this.requestManager
                .load(Json_Url + feed.getViewImg())
                .centerCrop()
                .into(viewPager);
    }
}

