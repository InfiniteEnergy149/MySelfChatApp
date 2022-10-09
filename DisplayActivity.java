package com.example.myselfchatapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
    ArrayList<UserChatDisplay> userChatDisplays = new ArrayList<>();
    String name,logo,colour;
    Integer currentUserId;
    DBHelper DB = new DBHelper(this);;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Log.d("myselfchatapp","test AB "+currentUserId+"");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = Integer.valueOf(extras.getString("userId"));
            //The key argument here must match that used in the other activity
        }
        Log.d("myselfchatapp","test BA"+currentUserId+"");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        setUserChatDisplays();
        UCD_RecyclerViewAdapter adapter = new UCD_RecyclerViewAdapter(this,userChatDisplays);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
 public void setUserChatDisplays(){
     //RecyclerView of Users (logo(border- colour),name)
     Cursor res = DB.getData();

     while(res.moveToNext()){
         if (!res.getString(0).equals(currentUserId.toString())){ //skip current user in chat list
             //name,logo,colour
             name = res.getString(1);
             logo = res.getString(5);
             colour = res.getString(6);
             userChatDisplays.add(new UserChatDisplay(name,logo,colour));

         }
     }
 }
}