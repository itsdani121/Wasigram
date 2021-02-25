package com.example.wasigram;

import android.net.Uri;

import com.google.android.exoplayer2.ui.PlayerView;

public interface videoCallBack {
    void onSuccessPlay(PlayerView playerView, Uri url, int position);
}
