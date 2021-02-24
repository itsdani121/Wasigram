package com.example.wasigram;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.List;

public class newsFeedAdapter extends RecyclerView.Adapter {
    public static final int imageFeed = 0;
    public static final int videoFeed = 1;
    Context context;
    List<newsFeedModel> feedModels;
    String[] images;
    videoCallBack callBack;
    String Json_Url = "https://wasisoft.com/dev/";

    public newsFeedAdapter(Context context, List<newsFeedModel> feedModels, String[] images) {
        this.context = context;
        this.feedModels = feedModels;
        this.images = images;
    }

    public newsFeedAdapter(Context context, List<newsFeedModel> feedModels, videoCallBack callBack) {
        this.context = context;
        this.feedModels = feedModels;
        this.callBack = callBack;
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            newsFeedModel feed = feedModels.get(position);
            imageNewsFeed imageNews = (imageNewsFeed) holder;

            Glide
                    .with(context)
                    .load(Json_Url + feed.getViewImg())
                    .centerCrop()
                    .into(imageNews.viewPager);
            Log.d("TAG", "onBindViewHolder: " + feed.getViewImg());

            imageNews.title.setText(feed.getTitleName());
            imageNews.likes.setText(feed.getLike());
            imageNews.description.setText(feed.getDescription());

        } else {

            newsFeedModel model = feedModels.get(position);
            videoNewsFeed videoNews = (videoNewsFeed) holder;
            videoNews.videoLikes.setText(model.getLike());
            videoNews.videoTitle.setText(model.getTitleName());
            videoNews.videoDescription.setText(model.getDescription());
            callBack.onSuccessPlay(videoNews.videoView,Uri.parse(Json_Url + model.getVideo()));
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




    public static class imageNewsFeed extends RecyclerView.ViewHolder {
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

    public static class videoNewsFeed extends RecyclerView.ViewHolder {
        PlayerView videoView;
        TextView videoTitle, videoLikes, videoDescription;

        public videoNewsFeed(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.item_video_exoplayer);
            videoTitle = itemView.findViewById(R.id.profile_video_name_news_feed);
            videoLikes = itemView.findViewById(R.id.profile_video_like_count_news_feed);
            videoDescription = itemView.findViewById(R.id.profile_video_comments_news_feed);
        }
    }
}
