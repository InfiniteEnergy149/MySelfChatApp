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
    Button logOutClick,profileClick,groupChatClick;
    DBHelper DB = new DBHelper(this);

    @SuppressLint("MissingInflatedId")
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

        //To group chat
        groupChatClick =  findViewById(R.id.groupChatBtn);
        groupChatClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(DisplayActivity.this,ChatActivity.class);
                myIntent.putExtra("clickedId","0");
                myIntent.putExtra("groupId","1");
                myIntent.putExtra("userId",currentUserId.toString());
                startActivity(myIntent);
            }
        });

    }
 public void setUserChatDisplays(){
     //RecyclerView of Users (logo(border- colour),name)
     Cursor res = DB.getUserData();
     Cursor resA = DB.getUserData();
     Cursor resGroup = DB.getGroupData();
     Cursor resGroupUserX = DB.getGroupUserXData();
     String thisUserName = "";
     Integer thisGroupId = 0;
     Integer userLastMessId = 0;
     Integer groupLastMessId = 0;
     Boolean newMessage = false;
     //get user name
     while(resA.moveToNext()){
         if (resA.getString(0).equals(currentUserId.toString())) { //skip current user in chat list
         thisUserName = resA.getString(1);
         }
     }

     while(res.moveToNext()){
         if (!res.getString(0).equals(currentUserId.toString())){ //skip current user in chat list
             //name,logo,colour
             name = res.getString(1);
             logo = res.getString(5);
             colour = res.getString(6);

             //SET NOTIFICATION
             //get groupId
             resGroup= DB.getGroupData();
             while(resGroup.moveToNext()){
                 if (resGroup.getString(1).equals(name + " & " +thisUserName)||resGroup.getString(1).equals(thisUserName + " & " +name)){
                     thisGroupId = resGroup.getInt(0);
                 }
             }
             Log.d("last  groupID",thisGroupId.toString());
             //get last message id from user
             groupLastMessId = -2;
             groupLastMessId = -1;
             resGroupUserX = DB.getGroupUserXData();
             while (resGroupUserX.moveToNext()){
                 if (resGroupUserX.getString(2).equals(thisGroupId.toString())){

                     if (resGroupUserX.getInt(3)>groupLastMessId) {
                         groupLastMessId = resGroupUserX.getInt(3);
                         Log.d("last count","hat");
                     }
                 }
                 if (resGroupUserX.getString(2).equals(thisGroupId.toString())&& resGroupUserX.getString(1).equals(currentUserId.toString())){
                         if (resGroupUserX.getInt(3)>userLastMessId) {
                             userLastMessId = resGroupUserX.getInt(3);
                         }
                 }
             }
             Log.d("last message user", userLastMessId.toString());
             Log.d("last message group",groupLastMessId.toString());

             if (userLastMessId == groupLastMessId){
                 newMessage = false;
             }else{
                 newMessage = true;
             }


             userChatDisplays.add(new UserChatDisplay(name,logo,colour,newMessage));

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
        intent.putExtra("groupId","-1");
        startActivity(intent);
    }


}