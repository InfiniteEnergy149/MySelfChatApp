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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    ArrayList<UserChatMessage> userChatMessage = new ArrayList<>();
    Integer currentUserId, clickedUserId,groupChatId;
    TextView notice,groupNameTitle;
    EditText messageContent;
    Button sendClick,backClick;
    Integer messageGroupId = 0;
    String currentDate, currentTime;
    DBHelper DB = new DBHelper(this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = Integer.valueOf(extras.getString("userId"));
            clickedUserId = Integer.valueOf(extras.getString("clickedId"));
            groupChatId = Integer.valueOf(extras.getString("groupId"));//1 if group chat, -1 if not groupchat
            //The key argument here must match that used in the other activity
        }

        notice = findViewById(R.id.notice);
        messageContent = findViewById(R.id.contentEnter);
        sendClick = findViewById(R.id.sendBtn);
        backClick = findViewById(R.id.chatBackBtn);
        groupNameTitle = findViewById(R.id.groupName);

        if (groupChatId == 1) {//group chat selected
            notice.setText("Group Chat - user:" + currentUserId );
        }else{//1 on 1 chat selected
            notice.setText("user:" + currentUserId + " , clicked:" + clickedUserId);
        }

        messageGroupId = Integer.valueOf(setUserChatMessage(groupChatId));//Read details from database (Check if send or recieve)

        //Set Group Name
        Cursor groupIdRes = DB.getGroupData();
        while(groupIdRes.moveToNext()){
            if (groupIdRes.getInt(0)==(messageGroupId)){
                groupNameTitle.setText("Group Name: " + groupIdRes.getString(1));
            }
        }


        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        Chat_RecyclerViewAdapter adapter = new Chat_RecyclerViewAdapter(this, userChatMessage);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.smoothScrollToPosition(adapter.getItemCount());
        sendClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("check","test B");
                currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
               DB.insertMessageData(currentUserId, messageGroupId, messageContent.getText().toString(), currentDate, currentTime);
                userChatMessage.add(new UserChatMessage(true, "", "", messageContent.getText().toString(), currentDate, currentTime));
                Log.d("check","test C");
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(adapter.getItemCount());

                //update last message id for notifications
                Cursor resMess = DB.getMessageData();
                resMess.moveToLast();
                //get GroupUserXID for user
                Cursor resGUXD = DB.getGroupUserXData();
                String groupUserXDataId = "";
                while(resGUXD.moveToNext()){
                    if (resGUXD.getString(1).equals(currentUserId.toString()) && resGUXD.getString(2).equals(messageGroupId.toString())){
                        groupUserXDataId = resGUXD.getString(0);
                    }
                }
                DB.updateGroupUserXData(groupUserXDataId,currentUserId,messageGroupId,resMess.getInt(0));
                //Add send to message arrayList-(send/recieve,name/null,logo/null,dateTime,content)
            }
        });

        backClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent myIntent = new Intent(ChatActivity.this,DisplayActivity.class);
                myIntent.putExtra("userId",currentUserId.toString());
                startActivity(myIntent);
            }
            });
    }

    public String findGroupId(Integer groupChatId) {
    //********************************
        //Make sure the correct groupId if found
        String messageGroupId = "";
        if (groupChatId == 1){
            messageGroupId = "1";
        }else {
            ArrayList<String> currentUserIdGroupId = new ArrayList<>();
            ArrayList<String> clickedUserIdGroupId = new ArrayList<>();
            Cursor res = DB.getGroupUserXData();
            while (res.moveToNext()) {
                if (currentUserId.toString().equals(res.getString(1))) {
                    currentUserIdGroupId.add(res.getString(2));
                }
                if (clickedUserId.toString().equals(res.getString(1))) {
                    clickedUserIdGroupId.add(res.getString(2));
                    Log.d("clicked id",res.getString(2));
                }
            }


            for (int i = 0; i < currentUserIdGroupId.size(); i++) {
                for (int j = 0; j < clickedUserIdGroupId.size(); j++) {
                    //check if same and that groupchat is skipped
                    if (currentUserIdGroupId.get(i).equals(clickedUserIdGroupId.get(j)) && !(clickedUserIdGroupId.get(j).equals("1"))) {
                        messageGroupId = clickedUserIdGroupId.get(j);
                        Log.d("find messId",""+messageGroupId);
                        //find groupuserxid for clicked and current user for notifications later
                        //FIND GROUPUSERXIF FOR CLICKED AND CURRENT USER FOR NOTIFICATIONS
                    }
                }
            }
        }
        return messageGroupId;
    }

    public String setUserChatMessage(Integer groupChatId) {
        //messageGroupId is the id used to create and read messages from only this group
       String messageGroupId = findGroupId(groupChatId);
       Log.d("find groupId",""+messageGroupId);
         if (messageGroupId.equals("")){
             AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
             builder.setCancelable(true);
             builder.setTitle("Error Message");
             builder.setMessage("ERROR - Message Group Id");
             builder.show();
             Log.d("aa","g");
             messageGroupId = "-1";
         }else {
             Log.d("find k","hat");
             //Read all messages from this group Id to put into arrayList
             Cursor resMessage = DB.getMessageData();
             Cursor resUsers = DB.getUserData();
             int userIdPos = 0,currUserIdPos = 0;
             Boolean sendRecieve;
             String message, date, time, name, logo;
             while (resMessage.moveToNext()) {
                 Log.d("sender a", messageGroupId);
                 Log.d("sender b", resMessage.getString(2));
                 if (resMessage.getString(2).equals(messageGroupId)) {
                     if (resMessage.getString(1).equals(currentUserId.toString())) {
                         //Use send XML - boolean true

                         sendRecieve = true;
                         name = "";
                         logo = "";
                       //  Log.d("peachA",resMessage.getString(1));


                     }else{ //find sender logo and name
                      //    Log.d("peachB",currentUserId.toString());
                         sendRecieve = false;
                         //resMessage.getString(1);//sender user Id
                         resUsers = DB.getUserData();
                         name = "unknown h";
                         logo = "question";
                         while (resUsers.moveToNext()){
                             if (resUsers.getString(0).equals(resMessage.getString(1))){
                                 name = resUsers.getString(1);
                                 logo = resUsers.getString(5);
                                 //Log.d("apple","test");
                             }
                         }
                     }

                     message = resMessage.getString(3);
                     date = resMessage.getString(4);
                     time = resMessage.getString(5);

                     userChatMessage.add(new UserChatMessage(sendRecieve, name, logo, message, date, time));
                 }
             }
             //DB.updateGroupUserXData(currentUserId,Integer.valueOf(groupId),"");
             //DB.updateGroupUserXData(clickedUserId,Integer.valueOf(groupId),"");
         }

        //Update lastmessage id in GroupUserXData
        Cursor resMess = DB.getMessageData();
        resMess.moveToLast();
        //get GroupUserXID for user
        Cursor resGUXD = DB.getGroupUserXData();
        String groupUserXDataId = "";
        while(resGUXD.moveToNext()){
            if (resGUXD.getString(1).equals(currentUserId.toString()) && resGUXD.getString(2).equals(messageGroupId)){
                groupUserXDataId = resGUXD.getString(0);
            }
        }
        if (resMess.getCount()>0) {
            DB.updateGroupUserXData(groupUserXDataId, currentUserId, Integer.valueOf(messageGroupId), resMess.getInt(0));
        }
        return messageGroupId;
    }
}