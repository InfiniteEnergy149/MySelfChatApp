package com.example.myselfchatapp;

import android.content.Context;
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
    Integer currentUserId, clickedUserId;
    TextView notice;
    EditText messageContent;
    Button sendClick;
    Integer messageGroupId = 0;
    String currentDate, currentTime;
    DBHelper DB = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = Integer.valueOf(extras.getString("userId"));
            clickedUserId = Integer.valueOf(extras.getString("clickedId"));
            //The key argument here must match that used in the other activity
        }

        notice = findViewById(R.id.notice);
        notice.setText("user:" + currentUserId + " , clicked:" + clickedUserId);



        messageGroupId = Integer.valueOf(setUserChatMessage());//Read details from database (Check if send or recieve)
        Log.d("check","test A");
        messageContent = findViewById(R.id.contentEnter);
        sendClick = findViewById(R.id.sendBtn);


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
                //Add send to message arrayList-(send/recieve,name/null,logo/null,dateTime,content)
            }
        });


    }

    public String findGroupId() {

        //Make sure the correct groupId if found
        String messageGroupId = "";
        ArrayList<String> currentUserIdGroupId = new ArrayList<>();
        ArrayList<String> clickedUserIdGroupId = new ArrayList<>();
        Cursor res = DB.getGroupUserXData();
        while (res.moveToNext()) {
            if (currentUserId.toString().equals(res.getString(1))) {
                currentUserIdGroupId.add(res.getString(2));
            }
            if (clickedUserId.toString().equals(res.getString(1))) {
                clickedUserIdGroupId.add(res.getString(2));
            }
        }


        for (int i = 0; i < currentUserIdGroupId.size(); i++) {
            for (int j = 0; j < clickedUserIdGroupId.size(); j++) {
                if (currentUserIdGroupId.get(i).equals(clickedUserIdGroupId.get(j))) {
                    messageGroupId = clickedUserIdGroupId.get(j);

                    //find groupuserxid for clicked and current user for notifications later
                    //FIND GROUPUSERXIF FOR CLICKED AND CURRENT USER FOR NOTIFICATIONS
                }
            }
        }

        return messageGroupId;
    }

    public String setUserChatMessage() {
        //messageGroupId is the id used to create and read messages from only this group
       String messageGroupId = findGroupId();
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
                     } else {
                         //
                         while (resUsers.moveToNext()) {//get user resUsers position
                             userIdPos += 1;
                             if (resUsers.getString(0).equals(currentUserId.toString())) {
                                 currUserIdPos = userIdPos;
                             }
                         }
                         resUsers.moveToPosition(currUserIdPos - 1);
                         name = resUsers.getString(1);
                         logo = resUsers.getString(5);
                         sendRecieve = false;
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
        //return groupId to sencClick ONCLICK
        Log.d("track",messageGroupId);
        return messageGroupId;
    }
}