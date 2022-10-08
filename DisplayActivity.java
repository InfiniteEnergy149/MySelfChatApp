package com.example.myselfchatapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity {
    Integer currentUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = Integer.valueOf(extras.getString("userId"));
            //The key argument here must match that used in the other activity
        }

        //To logOut Scene
        Button logOutClick = (Button) findViewById(R.id.logOutBtn);
        logOutClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(DisplayActivity.this,MainActivity.class);
                startActivity(myIntent);
            }
        });

        //To profile Scene
        Button profileClick = (Button) findViewById(R.id.toProfileBtn);
        logOutClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(DisplayActivity.this,ProfileActivity.class);
                myIntent.putExtra("userId",currentUserId);
                startActivity(myIntent);
            }
        });


    }


}