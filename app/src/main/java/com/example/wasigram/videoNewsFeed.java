package com.example.wasigram;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class videoNewsFeed extends RecyclerView.ViewHolder {
    private final View parent;
    public TextView videoTitle, videoLikes, videoDescription, mediaTypes, viewComments, userID, videoDateSnap;
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
        userID = itemView.findViewById(R.id.users_id);
        mediaTypes = itemView.findViewById(R.id.mediaTypes);
        videoDateSnap = itemView.findViewById(R.id.videoDateSnap);
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
        userID.setText(model.getUserId());
        videoDateSnap.setText(model.getDateSnap());
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
        viewComments.setOnClickListener(view -> {
            run(context, new commentListFragment(), model.getUserId());
        });

    }

    void moveActivity(Context context, String id) {
        Intent myactivity = new Intent(context.getApplicationContext(), activity_to_fragment.class);
        myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
        myactivity.putExtra("post_id", id);
        context.getApplicationContext().startActivity(myactivity);
    }

    private void run(Context context, Fragment fragment, String s) {
        AppCompatActivity activity = (AppCompatActivity) context;
        Bundle bundle = new Bundle();
        bundle.putString("post_id", s);
        fragment.setArguments(bundle);
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragments, fragment)
                .commit();


    }

    private void moveFrag(Context context, Fragment Fragment) {
        AppCompatActivity activity = (AppCompatActivity) context;
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragments, Fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}

