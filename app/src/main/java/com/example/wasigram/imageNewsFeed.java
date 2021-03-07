package com.example.wasigram;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class imageNewsFeed extends RecyclerView.ViewHolder {
    public RequestManager requestManager;
    ImageView dpImage, viewPager;
    TextView title, likes, description, mediaType, viewComments,userID,imageDateSnap;
    String Json_Url = ApiUrl.media;
    boolean isExpanded = false;

    public imageNewsFeed(@NonNull View itemView) {
        super(itemView);
        dpImage = itemView.findViewById(R.id.profile_image_images_news_feed);
        viewPager = itemView.findViewById(R.id.profile_image_viewPage_news_feed);
        title = itemView.findViewById(R.id.profile_image_name_news_feed);
        mediaType = itemView.findViewById(R.id.mediaTypes);
        userID = itemView.findViewById(R.id.user_ids);
        imageDateSnap = itemView.findViewById(R.id.imageDateSnap);
        likes = itemView.findViewById(R.id.profile_image_like_count_news_feed);
        description = itemView.findViewById(R.id.profile_image_description_news_feed);
        viewComments = itemView.findViewById(R.id.profile_image_comments_news_feed);

    }


    public void onBind(Context context, newsFeedModel feed, int position, RequestManager requestManager, videoCallBack callBack) {
        this.requestManager = requestManager;
        title.setText(feed.getTitleName());
        likes.setText(feed.getLike());
        userID.setText(feed.getUserId());
        mediaType.setText(feed.getMediaType());
        description.setText(feed.getDescription());
        viewComments.setText(feed.getViewAllComments());
        imageDateSnap.setText(feed.getDateSnap());

        this.requestManager
                .load(Json_Url + feed.getViewImg())
                .centerCrop()
                .into(viewPager);
        description.setOnClickListener(view -> {
            if (!isExpanded) {
                callBack.onSuccessPlay(feed, position);
                description.setMaxLines(Integer.MAX_VALUE); //As in the android sourcecode
                description.setEllipsize(null);
                isExpanded = true;
            } else {
                callBack.onSuccessPlay(feed, position);
                description.setMaxLines(2); //As in the android sourcecode
                isExpanded = false;
            }
        });

        viewComments.setOnClickListener(view -> {
            moveActivity(context,feed.getUserId());
        });

    }

    void moveActivity(Context context, String id) {
        Intent myactivity = new Intent(context.getApplicationContext(), activity_to_fragment.class);
        myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
        myactivity.putExtra("post_id",id);
        context.getApplicationContext().startActivity(myactivity);
    }

    void moveFragment(Fragment fragment, View view) {
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragments, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

}

