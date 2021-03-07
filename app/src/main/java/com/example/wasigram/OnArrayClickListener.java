package com.example.wasigram;

import com.androidnetworking.error.ANError;

import org.json.JSONArray;

public interface OnArrayClickListener {
    void onSuccessFullResponse(JSONArray response);
    void onFailedResponse(String error);

}
