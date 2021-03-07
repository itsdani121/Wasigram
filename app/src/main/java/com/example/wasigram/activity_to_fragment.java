package com.example.wasigram;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class activity_to_fragment extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_fragment);

        String userInfoId = getIntent().getStringExtra("post_id");
        moveFrag(new HomeFragment());
        navigationView = findViewById(R.id.bottom_bar);
        onClickNav();
    }

    void onClickNav() {
        navigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.search:
                    moveFrag(new SearchFragment());
                    return true;
                case R.id.addBox:
                    moveFrag(new AddFragment());
                    return true;
                case R.id.lovely:
                    moveFrag(new LikeFragment());
                    return true;
                case R.id.profile:
                    moveFrag(new ProfileFragment());
                    return true;
                default:
                    moveFrag(new HomeFragment());

                    return true;
            }
        });
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

    private void moveFrag(Fragment Fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragments, Fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}