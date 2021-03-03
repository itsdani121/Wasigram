package com.example.wasigram;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import static android.content.ContentValues.TAG;

public class videoNewsFeed extends RecyclerView.ViewHolder {
    private final View parent;
    public TextView videoTitle, videoLikes, videoDescription,mediaTypes;
    public ImageView volumeControl,heart,videoLike;
    public RequestManager requestManager;
    public FrameLayout mediaContainer;
    public int pos;

    public videoNewsFeed(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        mediaContainer = itemView.findViewById(R.id.item_video_exoplayer);
        volumeControl = itemView.findViewById(R.id.volumeControl);
        heart = itemView.findViewById(R.id.heart);
        mediaTypes = itemView.findViewById(R.id.mediaTypes);

        videoLike = itemView.findViewById(R.id.profile_video_like_news_feed);
        videoTitle = itemView.findViewById(R.id.profile_video_name_news_feed);
        videoLikes = itemView.findViewById(R.id.profile_video_like_count_news_feed);
        videoDescription = itemView.findViewById(R.id.profile_video_description_news_feed);
    }

    @SuppressLint("SetTextI18n")
    public void onBind(newsFeedModel model, int position, RequestManager requestManager,videoCallBack callBack) {
        this.requestManager = requestManager;
        videoLikes.setText(model.getLike());
        parent.setTag(this);
        videoTitle.setText(model.getTitleName());
        videoDescription.setText(model.getDescription());
        mediaTypes.setText(model.getMediaType());

        pos = position;

        videoDescription.setOnClickListener(view -> {
            callBack.onSuccessPlay(model,position);
        });

    }
}

