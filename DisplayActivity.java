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

public class DisplayActivity extends AppCompatActivity implements RecyclerViewInterface{
    ArrayList<UserChatDisplay> userChatDisplays = new ArrayList<>();
    String name,logo,colour;
    TextView welcome;
    Integer currentUserId;
    Button logOutClick,profileClick;
    DBHelper DB = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = Integer.valueOf(extras.getString("userId"));
            //The key argument here must match that used in the other activity
        }

       // DB.insertGroupUserXData(0,0," ");
        welcome = findViewById(R.id.welcomeUser);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        setUserChatDisplays();
        UCD_RecyclerViewAdapter adapter = new UCD_RecyclerViewAdapter(this,userChatDisplays,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //To logOut Scene
        logOutClick = findViewById(R.id.logOutBtn);
        logOutClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(DisplayActivity.this,MainActivity.class);
                startActivity(myIntent);
            }
        });

        //To profile Scene
        profileClick =  findViewById(R.id.toProfileBtn);
        profileClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(DisplayActivity.this,ProfileActivity.class);
                myIntent.putExtra("userId",currentUserId.toString());
                startActivity(myIntent);
            }
        });


    }
 public void setUserChatDisplays(){
     //RecyclerView of Users (logo(border- colour),name)
     Cursor res = DB.getUserData();

     while(res.moveToNext()){
         if (!res.getString(0).equals(currentUserId.toString())){ //skip current user in chat list
             //name,logo,colour
             name = res.getString(1);
             logo = res.getString(5);
             colour = res.getString(6);
             userChatDisplays.add(new UserChatDisplay(name,logo,colour));

         }else{
             welcome.setText("Welcome : " + res.getString(1));
         }
     }
 }

    @Override
    public void onItemClick(int position) {
        Log.d("click name",position+"");
        Cursor res = DB.getUserData();
        Intent intent = new Intent(DisplayActivity.this,ChatActivity.class);
        intent.putExtra("userId",currentUserId.toString());
        //set clickedId
        String clickedUserId = "";
        Integer counter = 0;
        //position = in arraylist not in user_data
        while (res.moveToNext()) {//get clicked position in case of gap in id sequence
            //go through list and skip current choice
            // and apply position in arraylist from that list
            if (!res.getString(0).equals(currentUserId.toString())) {
                counter += 1;

                if (counter == position + 1) {
                    clickedUserId = res.getString(0);
                }
            }
        }

        intent.putExtra("clickedId",clickedUserId);//error
        startActivity(intent);
    }


}