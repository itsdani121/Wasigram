package com.example.wasigram;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wasigram.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    Toolbar toolbar;
    String[] images = {"https://www.pexels.com/photo/ornamental-window-bars-in-small-room-5029306/", "https://www.pexels.com/photo/white-car-on-snow-covered-road-6704324/", "https://www.pexels.com/photo/new-complex-of-contemporary-skyscrapers-in-downtown-4394112/"};
    String[] video = {"https://www.pexels.com/video/couple-cigarette-smoke-lifestyle-6531148/", "https://www.pexels.com/video/pamukkale-6047896/"};

    List<newsFeedModel> feedModel = new ArrayList<>();
    newsFeedImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        toolbar = findViewById(R.id.mews_feed_toolbar);
        toolbar.setTitle("NewsFeed");
        toolbar.setTitleTextColor(Color.WHITE);

        initRef();
    }

    void imageADD(List<newsFeedModel> feedModel) {
        imageAdapter = new newsFeedImageAdapter(this, feedModel);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setSmoothScrollbarEnabled(true);
        imageAdapter.notifyDataSetChanged();
        mainBinding.newsFeedRecycler.setAdapter(imageAdapter);
        mainBinding.newsFeedRecycler.setHasFixedSize(true);
        mainBinding.newsFeedRecycler.setLayoutManager(manager);
    }

    void initRef() {

        for (int i = 0; i < 10; i++) {
            String likes = String.valueOf(i);
            String image = String.valueOf(R.drawable.ic_profile);
            String title = "Title " + i;
            newsFeedModel model = new newsFeedModel(image, images, title, video, likes);
            feedModel.add(model);
        }

        imageADD(feedModel);
    }

}