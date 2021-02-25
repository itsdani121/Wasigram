package com.example.wasigram;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.wasigram.databinding.ActivityMainBinding;
import com.google.android.exoplayer2.ExoPlayerFactory;
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
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };


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
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mainBinding.newsFeedRecycler);
        imageAdapter = new newsFeedAdapter(this, feedModel, this::videoPlayBack);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setSmoothScrollbarEnabled(true);
        imageAdapter.notifyDataSetChanged();
        mainBinding.newsFeedRecycler.setAdapter(imageAdapter);
        mainBinding.newsFeedRecycler.setHasFixedSize(true);
        mainBinding.newsFeedRecycler.setLayoutManager(manager);

    }

    void videoPlayBack(PlayerView player, Uri videoUrl, int position) {
        BandwidthMeter meter = new DefaultBandwidthMeter();
        TrackSelector track = new DefaultTrackSelector(
                new AdaptiveTrackSelection.Factory(meter)
        );
        imageAdapter.exoPlayer = ExoPlayerFactory.newSimpleInstance(this, track);
        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("video");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(videoUrl, factory, extractorsFactory, null, null);
        player.setPlayer(imageAdapter.exoPlayer);
        player.setKeepScreenOn(true);
        imageAdapter.exoPlayer.prepare(mediaSource);
        imageAdapter.exoPlayer.setPlayWhenReady(true);
        player.setUseController(true);

    }

    @Override
    public void onDestroy() {
        imageAdapter.releasePlayer();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        imageAdapter.pausePlayer();
    }
}