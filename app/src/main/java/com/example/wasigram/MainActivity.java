package com.example.wasigram;

import android.graphics.Color;
import android.net.Uri;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    Toolbar toolbar;
    SimpleExoPlayer exoPlayer;
    String JSON_URl = "https://wasisoft.com/dev/index.php";
    List<newsFeedModel> feedModels = new ArrayList<>();
    newsFeedAdapter imageAdapter;
    PlayerView myPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        toolbar = findViewById(R.id.mews_feed_toolbar);
        toolbar.setTitle("NewsFeed");
        toolbar.setTitleTextColor(Color.WHITE);

        Log.i("TAG", "onCreate: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        showList();
        Log.i("TAG", "onStart: ");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("TAG", "onStop: ");
        exoPlayer.stop();
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.release();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TAG", "onDestroy: ");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG", "onResume: ");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("TAG", "onRestart: ");

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i("TAG", "onDetach: ");

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
        imageAdapter = new newsFeedAdapter(this, feedModel, (playerView, url) -> videoPlayBack(playerView, url));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setSmoothScrollbarEnabled(true);
        imageAdapter.notifyDataSetChanged();
        mainBinding.newsFeedRecycler.setAdapter(imageAdapter);
        mainBinding.newsFeedRecycler.setHasFixedSize(true);
        mainBinding.newsFeedRecycler.setLayoutManager(manager);
    }


    void videoPlayBack(PlayerView player, Uri videoUrl) {
        try {
            BandwidthMeter meter = new DefaultBandwidthMeter.Builder(this).build();
            TrackSelector track = new DefaultTrackSelector(
                    new AdaptiveTrackSelection.Factory(meter)
            );
            DefaultLoadControl loadControl = new DefaultLoadControl.Builder().setBufferDurationsMs(32 * 1024, 64 * 1024, 1024, 1024).createDefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, track, loadControl);
            DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoUrl, factory, extractorsFactory, null, null);
            player.setPlayer(exoPlayer);
            player.setKeepScreenOn(true);
            exoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}