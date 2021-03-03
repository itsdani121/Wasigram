package com.example.wasigram;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;


public class imageNewsFeed extends RecyclerView.ViewHolder {
    public RequestManager requestManager;
    ImageView dpImage, viewPager;
    TextView title, likes, description, mediaType;
    String Json_Url = "https://wasisoft.com/dev/";

    public imageNewsFeed(@NonNull View itemView) {
        super(itemView);
        dpImage = itemView.findViewById(R.id.profile_image_images_news_feed);
        viewPager = itemView.findViewById(R.id.profile_image_viewPage_news_feed);
        title = itemView.findViewById(R.id.profile_image_name_news_feed);
        mediaType = itemView.findViewById(R.id.mediaTypes);
        likes = itemView.findViewById(R.id.profile_image_like_count_news_feed);
        description = itemView.findViewById(R.id.profile_image_description_news_feed);

    }


    public void onBind(newsFeedModel feed, int position, RequestManager requestManager,videoCallBack callBack) {
        this.requestManager = requestManager;
        title.setText(feed.getTitleName());
        likes.setText(feed.getLike());
        mediaType.setText(feed.getMediaType());
        description.setText(feed.getDescription());

        this.requestManager
                .load(Json_Url + feed.getViewImg())
                .centerCrop()
                .into(viewPager);
        description.setOnClickListener(view -> {
            callBack.onSuccessPlay(feed,position);
        });

    }

}

