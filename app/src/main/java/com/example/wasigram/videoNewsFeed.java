package com.example.wasigram;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

public class videoNewsFeed extends RecyclerView.ViewHolder {
    private final View parent;
    public TextView videoTitle, videoLikes, videoDescription, mediaTypes, viewComments;
    public ImageView volumeControl, heart, videoLike;
    public RequestManager requestManager;
    public FrameLayout mediaContainer;
    public int pos;
    boolean isExpanded = false;

    public videoNewsFeed(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        mediaContainer = itemView.findViewById(R.id.item_video_exoplayer);
        volumeControl = itemView.findViewById(R.id.volumeControl);
        heart = itemView.findViewById(R.id.heart);
        mediaTypes = itemView.findViewById(R.id.mediaTypes);
        viewComments = itemView.findViewById(R.id.profile_video_comments_news_feed);
        videoLike = itemView.findViewById(R.id.profile_video_like_news_feed);
        videoTitle = itemView.findViewById(R.id.profile_video_name_news_feed);
        videoLikes = itemView.findViewById(R.id.profile_video_like_count_news_feed);
        videoDescription = itemView.findViewById(R.id.profile_video_description_news_feed);
    }

    @SuppressLint("SetTextI18n")
    public void onBind(Context context, newsFeedModel model, int position, RequestManager requestManager, videoCallBack callBack) {
        this.requestManager = requestManager;
        videoLikes.setText(model.getLike());
        parent.setTag(this);
        videoTitle.setText(model.getTitleName());
        videoDescription.setText(model.getDescription());
        mediaTypes.setText(model.getMediaType());
        viewComments.setText(model.getViewAllComments());
        pos = position;

        videoDescription.setOnClickListener(view -> {
            if (!isExpanded) {
                callBack.onSuccessPlay(model, position);
                videoDescription.setMaxLines(Integer.MAX_VALUE); //As in the android sourcecode
                isExpanded = true;
            } else {
                callBack.onSuccessPlay(model, position);
                videoDescription.setMaxLines(2); //As in the android sourcecode
                isExpanded = false;
            }
        });

    }
}

