package com.example.wasigram;

import com.androidnetworking.AndroidNetworking;

public class Database {
    public static Database instance;

    public synchronized static Database getInstance() {
        if (instance == null)
            instance = new Database();
            return instance;

    }

    Database() {

        AndroidNetworking.initialize(MyApp.getInstance().getAppContext());
    }

    
}
