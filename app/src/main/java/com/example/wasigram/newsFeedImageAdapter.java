package com.example.wasigram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.util.List;

public class newsFeedImageAdapter extends RecyclerView.Adapter<newsFeedImageAdapter.myViewHolder> {
    Context context;
    List<newsFeedModel> feedModels;

    public newsFeedImageAdapter(Context context, List<newsFeedModel> feedModels) {
        this.context = context;
        this.feedModels = feedModels;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_feed, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        newsFeedModel model = feedModels.get(position);
        /*Glide
                .with(context)
                .load(model.getViewImg())
                .centerCrop()
                .into(holder.viewImg);*/

        holder.videoView.setVideoPath(model.getVideo()[position]);
        holder.image.setImageResource(Integer.parseInt(model.getImage()));
        holder.title.setText(model.getTitleName());
        holder.like.setText(model.getLike());
    }

    @Override
    public int getItemCount() {
        return feedModels.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ViewPager2 viewImg;
        TextView title, like;
        VideoView videoView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            viewImg = itemView.findViewById(R.id.profile_viewPage_news_feed);
            image = itemView.findViewById(R.id.profile_image_news_feed);
            title = itemView.findViewById(R.id.profile_name_news_feed);
            like = itemView.findViewById(R.id.profile_like_count_news_feed);
            videoView = itemView.findViewById(R.id.profile_video_news_feed);
        }
    }
}
