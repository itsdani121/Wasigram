package com.example.wasigram;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class newsFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int imageFeed = 0;
    public static final int videoFeed = 1;
    Context context;
    ArrayList<newsFeedModel> feedModels;
    videoCallBack callBack;
    private RequestManager requestManager;

    public newsFeedAdapter(Context context, RequestManager requestManager, ArrayList<newsFeedModel> feedModels, videoCallBack callBack) {
        this.context = context;
        this.feedModels = feedModels;
        this.requestManager = requestManager;
        this.callBack = callBack;
    }

    public newsFeedAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.images_news_feed, parent, false);
            return new imageNewsFeed(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.video_news_feed, parent, false);
            return new videoNewsFeed(view);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            newsFeedModel feed = feedModels.get(position);
            ((imageNewsFeed) holder).onBind(feed, position, requestManager,callBack);
        } else {
            newsFeedModel model = feedModels.get(position);
            ((videoNewsFeed) holder).onBind(model, position, requestManager,callBack);
        }
        // notifyItemChanged(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (feedModels.get(position).getMediaType().contains("picture")) {
            return imageFeed;
        } else
            return videoFeed;
    }

    @Override
    public int getItemCount() {
        return feedModels.size();
    }
}
