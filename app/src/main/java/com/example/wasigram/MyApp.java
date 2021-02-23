package com.example.wasigram;

import android.app.Application;
import android.content.Context;

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
}
