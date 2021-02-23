package com.example.wasigram;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class newsFeedAdapter extends RecyclerView.Adapter {
    public static final int imageFeed = 0;
    public static final int videoFeed = 1;
    Context context;
    List<newsFeedModel> feedModels;
    String[] images;

    public newsFeedAdapter(Context context, List<newsFeedModel> feedModels, String[] images) {
        this.context = context;
        this.feedModels = feedModels;
        this.images = images;
    }

    public newsFeedAdapter(Context context, List<newsFeedModel> feedModels) {
        this.context = context;
        this.feedModels = feedModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.images_news_feed, parent, false);
            return new imageNewsFeed(view);
        }

        View view = LayoutInflater.from(context).inflate(R.layout.video_news_feed, parent, false);
        return new videoNewsFeed(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            newsFeedModel feed = feedModels.get(position);
            imageNewsFeed imageNews = (imageNewsFeed) holder;
            Glide
                    .with(context)
                    .load(feed.getImage())
                    .centerCrop()
                    .into(((imageNewsFeed) holder).dpImage);
            Glide
                    .with(context)
                    .load(feed.getViewImg())
                    .centerCrop()
                    .into(((imageNewsFeed) holder).viewPager);

            imageNews.title.setText(feed.getTitleName());
            imageNews.description.setText(feed.getDescription());

        } else {

            newsFeedModel model = feedModels.get(position);
            videoNewsFeed videoNews = (videoNewsFeed) holder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (feedModels.get(position).getDescription().contains("picture")) {
            return imageFeed;
        } else
            return videoFeed;
    }

    @Override
    public int getItemCount() {
        return feedModels.size();
    }


    static class imageNewsFeed extends RecyclerView.ViewHolder {
        ImageView dpImage, viewPager;
        TextView title, likes, description;

        public imageNewsFeed(@NonNull View itemView) {
            super(itemView);
            dpImage = itemView.findViewById(R.id.profile_image_images_news_feed);
            viewPager = itemView.findViewById(R.id.profile_image_viewPage_news_feed);
            title = itemView.findViewById(R.id.profile_image_name_news_feed);
            likes = itemView.findViewById(R.id.profile_image_like_count_news_feed);
            description = itemView.findViewById(R.id.profile_image_comments_news_feed);
        }
    }

    static class videoNewsFeed extends RecyclerView.ViewHolder {
        VideoView videoView;
        TextView videoTitle, videoLikes, videoDescrption;

        public videoNewsFeed(@NonNull View itemView) {
            super(itemView);

        }
    }
}