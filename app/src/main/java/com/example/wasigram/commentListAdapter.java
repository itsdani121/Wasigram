package com.example.wasigram;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class commentListAdapter extends RecyclerView.Adapter<commentListAdapter.MyViewHolder> {

    Context context;
    ArrayList<commentsListModel> feedModels;
    boolean isExpanded = false;

    public commentListAdapter(Context context, ArrayList<commentsListModel> feedModels) {
        this.context = context;
        this.feedModels = feedModels;
    }

    public commentListAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_comments_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        commentsListModel model = feedModels.get(position);
        holder.userName.setText(model.getUserName());
        holder.media.setText(model.getMedia());
        holder.userId.setText(model.getId());
        holder.userComments.setText(model.getUserComments());
        holder.userLikes.setOnClickListener(view -> {
            if (!isExpanded) {
                holder.userLikes.setImageResource(R.drawable.ic_heart);
                ImageViewCompat.setImageTintList(holder.userLikes, ColorStateList.valueOf(ContextCompat.getColor(context, R.color.Red)));
                isExpanded = true;
            } else {
                holder.userLikes.setImageResource(R.drawable.ic_like);
                ImageViewCompat.setImageTintList(holder.userLikes, ColorStateList.valueOf(ContextCompat.getColor(context, R.color.Grey)));
                isExpanded = false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedModels.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userComments, userName, media, userId;
        ImageView userLikes;

        public MyViewHolder(View view) {
            super(view);
            userLikes = view.findViewById(R.id.profile_video_comments_user_like);
            userComments = view.findViewById(R.id.profile_video_user_comments_details);
            userName = view.findViewById(R.id.profile_video_comment_user_name);
            media = view.findViewById(R.id.media);
            userId = view.findViewById(R.id.user_id);
        }
    }
}

