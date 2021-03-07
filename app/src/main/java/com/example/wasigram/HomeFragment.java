package com.example.wasigram;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.wasigram.databinding.FragmentHomeBinding;
import com.github.marlonlom.utilities.timeago.TimeAgo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import static android.content.ContentValues.TAG;
import static java.time.OffsetDateTime.*;

public class HomeFragment extends Fragment {
    String JSON_URl = ApiUrl.posts;
    ArrayList<newsFeedModel> feedModels = new ArrayList<>();
    String description[];
    newsFeedAdapter imageAdapter;
    ExoPlayerRecyclerView mRecyclerView;
    FragmentHomeBinding homeBinding;
    Toolbar toolbar;
    Database database;

    public HomeFragment() {
        // Required empty public constructor
        database = Database.getInstance();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = homeBinding.getRoot();
        toolbar = view.findViewById(R.id.home_toolbar);
        toolbar.setTitle("Home ");
        toolbar.setTitleTextColor(Color.WHITE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.news_feed_recycler);
        showList();
    }

    void showList() {
        if (isAdded()) {
            database.getJsonArray(JSON_URl, new OnArrayClickListener() {
                @Override
                public void onSuccessFullResponse(JSONArray response) {
                    if (response != null) {
                        description = new String[response.length()];
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                newsFeedModel FeedModel = new newsFeedModel();
                                FeedModel.setTitleName(object.getString("name"));
                                FeedModel.setUserId(object.getString("id"));
                                FeedModel.setMediaType(object.getString("media_type"));
                                FeedModel.setVideo(object.getString("media_url"));
                                FeedModel.setViewImg(object.getString("media_url"));
                                String dates = object.getString("created_at");
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                long time = Objects.requireNonNull(format.parse(dates)).getTime();
                                long now = System.currentTimeMillis();
                                CharSequence ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.DAY_IN_MILLIS);
                                FeedModel.setDateSnap(ago + "");
                                String like = object.getString("likes_count");
                                int count = Integer.parseInt(object.getString("comments_count"));
                                FeedModel.setViewAllComments("view all " + count + " comments");
                                FeedModel.setLike(like + " Likes");
                                description[i] = object.getString("description");
                                limitDescription(FeedModel, i, description, false);
                                feedModels.add(FeedModel);
                            } catch (JSONException | ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        uploadFeedData(feedModels);
                    } else {
                        Toast.makeText(getActivity(), "Empty posts ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailedResponse(String error) {
                    Toast.makeText(requireContext(), "No Value " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }
    }


    void uploadFeedData(ArrayList<newsFeedModel> feedModel) {
        //set data object
        mRecyclerView.setMediaObjects(feedModel);
        imageAdapter = new newsFeedAdapter(requireContext(), initGlide(), feedModel, this::textChangePosition);
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        manager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(manager);
        imageAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(imageAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    void textChangePosition(newsFeedModel models, int position) {
        limitDescription(models, position, description, true);
    }

    void limitDescription(newsFeedModel feedModel, int position,
                          String[] description, boolean isClick) {
        if (!isClick) {
            if (description[position].length() < 50) {
                feedModel.setDescription(description[position]);
            } else {
                feedModel.setDescription(description[position].substring(0, 49) + "...more details");
            }
        } else {
            feedModel.setDescription(description[position]);
            imageAdapter.notifyItemChanged(position);
            imageAdapter.notifyDataSetChanged();
        }
    }

    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions();
        return Glide.with(requireContext())
                .setDefaultRequestOptions(options);
    }

    @Override
    public void onDestroy() {
        if (mRecyclerView != null) {
            mRecyclerView.releasePlayer();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        if (mRecyclerView != null) mRecyclerView.pausePlayer();
        super.onPause();
    }

}