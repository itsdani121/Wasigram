package com.example.wasigram;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.wasigram.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    Toolbar toolbar;
    String JSON_URl = "https://wasisoft.com/dev/index.php";
    ArrayList<newsFeedModel> feedModels = new ArrayList<>();
    String description[];
    newsFeedAdapter imageAdapter;
    ExoPlayerRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        toolbar = findViewById(R.id.mews_feed_toolbar);
        toolbar.setTitle("NewsFeed");
        toolbar.setTitleTextColor(Color.WHITE);
        mRecyclerView = findViewById(R.id.news_feed_recycler);

        showList();

    }

    void showList() {
        AndroidNetworking.get(JSON_URl)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        if (response != null) {
                            description = new String[response.length()];
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    newsFeedModel FeedModel = new newsFeedModel();
                                    FeedModel.setTitleName(object.getString("account_name"));
                                    FeedModel.setMediaType(object.getString("media_type"));
                                    FeedModel.setVideo(object.getString("media"));
                                    FeedModel.setViewImg(object.getString("media"));
                                    String like = object.getString("likes");
                                    FeedModel.setLike(like + " Likes");
                                    description[i] = object.getString("desc");
                                    limitDescription(FeedModel, i, description, false);
                                    feedModels.add(FeedModel);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            uploadFeedData(feedModels);
                        } else {
                            Toast.makeText(MainActivity.this, "None ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(MainActivity.this, "No Value " + error.getErrorBody(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void uploadFeedData(ArrayList<newsFeedModel> feedModel) {
        LinearSnapHelper snapHelper = new SnapHelperOneByOne();
        snapHelper.attachToRecyclerView(mRecyclerView);
        //set data object
        mRecyclerView.setMediaObjects(feedModel);
        imageAdapter = new newsFeedAdapter(this, initGlide(), feedModel, this::textChangePosition);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setSmoothScrollbarEnabled(true);
        imageAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(imageAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
    }

    void textChangePosition(newsFeedModel models, int position) {
        limitDescription(models, position, description, true);
    }

    void limitDescription(newsFeedModel feedModel, int position,
                          String[] description, boolean isClick) {
        if (!isClick) {
            Log.d("TAG", "limitDescription: if click false");

            if (description[position].length() < 50) {
                feedModel.setDescription(description[position]);
            } else {
                feedModel.setDescription(description[position].substring(0, 49) + "more details");
            }
        } else {
            feedModel.setDescription(description[position]);
            imageAdapter.notifyItemChanged(position);
            imageAdapter.notifyDataSetChanged();

        }
    }

    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions();
        return Glide.with(getApplicationContext())
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