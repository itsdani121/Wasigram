package com.example.wasigram;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.wasigram.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    Toolbar toolbar;
    String JSON_URl = "https://wasisoft.com/dev/index.php";
    List<newsFeedModel> feedModels = new ArrayList<>();
    newsFeedAdapter imageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        toolbar = findViewById(R.id.mews_feed_toolbar);
        toolbar.setTitle("NewsFeed");
        toolbar.setTitleTextColor(Color.WHITE);
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
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    newsFeedModel FeedModel = new newsFeedModel();
                                    FeedModel.setTitleName(object.getString("account_name"));
                                    FeedModel.setLike(object.getString("likes"));
                                    FeedModel.setDescrption(object.getString("media_type"));
                                    FeedModel.setVideo(object.getString("media"));
                                    FeedModel.setViewImg(object.getString("media"));
                                    feedModels.add(FeedModel);
                                    String res = object.getString("media");
                                    Log.d("TAG", "onResponse: " + res);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            imageADD(feedModels);
                        } else {
                            Toast.makeText(MainActivity.this, "None ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(MainActivity.this, "No Value " + error.getErrorBody(), Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onError: " + error);
                    }
                });
    }

    void imageADD(List<newsFeedModel> feedModel) {
        imageAdapter = new newsFeedAdapter(this, feedModel);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setSmoothScrollbarEnabled(true);
        imageAdapter.notifyDataSetChanged();
        mainBinding.newsFeedRecycler.setAdapter(imageAdapter);
        mainBinding.newsFeedRecycler.setHasFixedSize(true);
        mainBinding.newsFeedRecycler.setLayoutManager(manager);
    }

    @Override
    public void onDestroy() {
        if (mainBinding.newsFeedRecycler != null) {
            if (imageAdapter.exoPlayer != null) {
                imageAdapter.exoPlayer.release();
                imageAdapter.exoPlayer=null;
            }
        }
        super.onDestroy();
    }
}