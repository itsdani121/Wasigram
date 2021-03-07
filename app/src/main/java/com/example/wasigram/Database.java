package com.example.wasigram;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class Database {
    public static Database instance;
    public synchronized static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;

    }

    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();

    Database() {
        AndroidNetworking.initialize(MyApp.getInstance().getAppContext(), okHttpClient);
    }

    public void getJsonArray(String JsonUrl, OnArrayClickListener listener) {
        AndroidNetworking.get(JsonUrl)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        listener.onSuccessFullResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onFailedResponse(ANErrorIssue(error));
                    }
                });
    }

    private String ANErrorIssue(ANError anError) {
        if (anError.getErrorCode() == 401 || anError.getErrorCode() == 403) {
            return "Validation Error";
        } else if (anError.getErrorCode() == 400) {
            return "Validation Error";
        } else if (anError.getErrorCode() == 404) {
            return "Resource Not Found";
        } else if (anError.getErrorCode() == 405) {
            return "Method Not Allowed";
        } else if (anError.getErrorCode() == 419) {
            return " Token Expired";
        } else if (anError.getErrorCode() == 500) {
            return "Server Error";
        }
        return anError.getErrorBody();

    }


}
