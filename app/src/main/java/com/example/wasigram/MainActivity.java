package com.example.wasigram;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wasigram.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        moveTo();
    }

    void moveTo() {
        Handler handler = new Handler();
        Runnable r = this::splashScreen;
        handler.postDelayed(r, 2000);
    }

    void splashScreen() {
        Intent intent = new Intent(MainActivity.this, activity_to_fragment.class);
        startActivity(intent);
        finish();
    }

}