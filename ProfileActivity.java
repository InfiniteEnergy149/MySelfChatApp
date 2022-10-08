package com.example.myselfchatapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    Integer currentUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = Integer.valueOf(extras.getString("userId"));
            //The key argument here must match that used in the other activity
        }

    }
}