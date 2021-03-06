package com.example.wasigram;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class activity_to_fragment extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_fragment);

        String userInfoId = getIntent().getStringExtra("post_id");
        run(new commentListFragment(), userInfoId);
        Log.d("TAG", "onCreate: " + userInfoId);
    }


    private void run(Fragment fragment, String s) {

        Bundle bundle = new Bundle();
        bundle.putString("post_id", s);
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragments, fragment)
                .commit();


    }
}