package com.example.wasigram;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.google.android.exoplayer2.ui.PlayerView;

public class videoNewsFeed extends RecyclerView.ViewHolder {
    private final View parent;
    public PlayerView videoView;
    public TextView videoTitle, videoLikes, videoDescription;
    public ImageView volumeControl;
    public RequestManager requestManager;
    public FrameLayout mediaContainer;

    public videoNewsFeed(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        mediaContainer = itemView.findViewById(R.id.item_video_exoplayer);
        volumeControl = itemView.findViewById(R.id.volumeControl);
        videoTitle = itemView.findViewById(R.id.profile_video_name_news_feed);
        videoLikes = itemView.findViewById(R.id.profile_video_like_count_news_feed);
        videoDescription = itemView.findViewById(R.id.profile_video_comments_news_feed);
    }

    public void onBind(newsFeedModel model, int position, RequestManager requestManager) {
        this.requestManager = requestManager;
        videoLikes.setText(model.getLike());
        parent.setTag(this);
        videoTitle.setText(model.getTitleName());
        videoDescription.setText(model.getDescription());
    }
}

