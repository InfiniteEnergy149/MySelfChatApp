package com.example.myselfchatapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    Integer currentUserId,logoSpinnerPosition,colourSpinnerPosition;
    DBHelper DB;
    EditText nameCheck,passwordCheck,ageCheck,descriptionCheck;
    Spinner logoDropListCheck,colourDropListCheck;
    ArrayAdapter logoAdap,colourAdap;
    Button updateUserClick,deleteUserClick,backClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = Integer.valueOf(extras.getString("userId"));
        }
        DB = new DBHelper(this);
            //RecyclerView of Users (logo(border- colour),name)
            Cursor res = DB.getUserData();

        logoDropListCheck = findViewById(R.id.logoDropList);
        ArrayAdapter<CharSequence> dropLogoListAdapter=ArrayAdapter.createFromResource(this, R.array.dropLogoMenu, android.R.layout.simple_spinner_item);
        dropLogoListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        logoDropListCheck.setAdapter(dropLogoListAdapter);
        logoAdap = (ArrayAdapter) logoDropListCheck.getAdapter();

        colourDropListCheck = findViewById(R.id.colourDropList);
        ArrayAdapter<CharSequence> dropColourListAdapter=ArrayAdapter.createFromResource(this, R.array.dropColourMenu, android.R.layout.simple_spinner_item);
        dropColourListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        colourDropListCheck.setAdapter(dropColourListAdapter);
        colourAdap = (ArrayAdapter) colourDropListCheck.getAdapter();

        nameCheck = findViewById(R.id.nameET);
        passwordCheck = findViewById(R.id.passwordET);
        ageCheck = findViewById(R.id.ageET);
        descriptionCheck = findViewById(R.id.descriptionET);
        updateUserClick = findViewById(R.id.updateUserBtn);
        deleteUserClick = findViewById(R.id.deleteUserBtn);
        backClick = findViewById(R.id.backBtn);


        while(res.moveToNext()){
                if (res.getString(0).equals(currentUserId.toString())){ //skip current user in chat list
                    //name,logo,colour
                    nameCheck.setText(res.getString(1));
                   passwordCheck.setText(res.getString(2));
                   ageCheck.setText(res.getString(3));
                   descriptionCheck.setText(res.getString(4));

                   logoSpinnerPosition = logoAdap.getPosition(res.getString(5));
                   logoDropListCheck.setSelection(logoSpinnerPosition);
                   colourSpinnerPosition = colourAdap.getPosition(res.getString(6));
                   colourDropListCheck.setSelection(colourSpinnerPosition);
                }
        }

        //edit User
        updateUserClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if ((Integer.valueOf(ageCheck.getText().toString()) instanceof Integer)  ){

                    Boolean checkupdatedata = DB.updateUserData(nameCheck.getText().toString(), passwordCheck.getText().toString(), Integer.valueOf(ageCheck.getText().toString()),descriptionCheck.getText().toString(),logoDropListCheck.getSelectedItem().toString(),colourDropListCheck.getSelectedItem().toString());
                    if(checkupdatedata==true) {
                        Toast.makeText(ProfileActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ProfileActivity.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ProfileActivity.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //delete User
        deleteUserClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                deleteUserGroups(currentUserId.toString(),DB);
                Boolean checkudeletedata = DB.deleteUserData(currentUserId.toString());
                if(checkudeletedata==true) {
                    Toast.makeText(ProfileActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ProfileActivity.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
                }
                Intent myIntent = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(myIntent);
            }


        });

        backClick.setOnClickListener(new View.OnClickListener() {
              public void onClick(View arg0) {
                  Intent myIntent = new Intent(ProfileActivity.this,DisplayActivity.class);
                  myIntent.putExtra("userId",currentUserId.toString());
                  startActivity(myIntent);
              }
        });

    }

    public void deleteUserGroups(String currentUserId, DBHelper DB){
        //ArrayList of all user data
        ArrayList<String> holdUsers = new ArrayList<>();
        ArrayList<ArrayList<String>> sortUsers = new ArrayList<>();
        Integer newUserId = 0;
        Cursor resGetUser = DB.getUserData();
        String userId = currentUserId;

        //Delete group then groupUserX where userId == currentUserId;

        //Find groupId's and GrouUserXId's
        Cursor resGroupUserX = DB.getGroupUserXData();
        ArrayList<Integer> groupId =  new ArrayList<>();
        ArrayList<Integer> groupUserXId = new ArrayList<>();
        while (resGroupUserX.moveToNext()){
            if (resGroupUserX.getString(1).equals(userId)) {//if id matches current id
                groupUserXId.add(resGroupUserX.getInt(0));//add groupUserXId to arrayList
                if (!groupId.contains(resGroupUserX.getInt(2))) {//avoid duplicates
                    groupId.add(resGroupUserX.getInt(2));//add group id to arraylist
                }
            }
        }

        for (int i = 0;i<groupId.size();i++){//delete by groupId
            DB.deleteGroupData(groupId.get(i).toString());
        }
        for (int i = 0;i<groupUserXId.size();i++){//delete by userId
            DB.deleteGroupUserXData(groupUserXId.get(i).toString());
        }
    }

}

//View,Edit and Delete (Error message to make sure)