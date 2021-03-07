package com.example.wasigram;

import android.app.Application;
import android.content.Context;

import com.arthurivanets.arvi.PlayerProviderImpl;

public class MyApp extends Application {

    public static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    public Context getAppContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
            instance=this;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        if(level >= TRIM_MEMORY_BACKGROUND) {
            PlayerProviderImpl.getInstance(this).release();
        }
    }

}
