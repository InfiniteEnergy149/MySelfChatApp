package com.example.myselfchatapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    EditText name, password, age, description, logo, colour;
    Button insert, update, delete, view;
    DBHelper DB;
    Spinner spinDropLogoList,spinDropColourList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
       /*update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);*/



        name = findViewById(R.id.nameEnter);
        password = findViewById(R.id.passwordEnter);
        age = findViewById(R.id.ageEnter);
        description = findViewById(R.id.descriptionEnter);
        insert = findViewById(R.id.addUserBtn);
        view = findViewById(R.id.showDataBtn);


        spinDropLogoList = findViewById(R.id.logoDropList);
        ArrayAdapter<CharSequence> dropLogoListAdapter=ArrayAdapter.createFromResource(this, R.array.dropLogoMenu, android.R.layout.simple_spinner_item);
        dropLogoListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinDropLogoList.setAdapter(dropLogoListAdapter);

        spinDropColourList = findViewById(R.id.colourDropList);
        ArrayAdapter<CharSequence> dropColourListAdapter=ArrayAdapter.createFromResource(this, R.array.dropColourMenu, android.R.layout.simple_spinner_item);
        dropColourListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinDropColourList.setAdapter(dropColourListAdapter);

        DB = new DBHelper(this);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String passwordTXT = password.getText().toString();
                Integer ageTXT = Integer.valueOf(age.getText().toString());
                String descriptionTXT = description.getText().toString();
                String logoTXT = spinDropLogoList.getSelectedItem().toString();
                String colourTXT = spinDropColourList.getSelectedItem().toString();
                //Add tables to group and groupUserX
                createUserGroups(nameTXT,DB);
                if ((Integer.valueOf(age.getText().toString()) instanceof Integer)) {
                    Boolean checkInsertData = DB.insertUserData(nameTXT, passwordTXT, ageTXT, descriptionTXT, logoTXT, colourTXT);
                    if (checkInsertData == true) {
                        Toast.makeText(SignUpActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();

                }

                Intent myIntent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(myIntent);
            }
        });
       /* update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String dobTXT = dob.getText().toString();

                Boolean checkupdatedata = DB.updateUserData(nameTXT, contactTXT, dobTXT);
                if(checkupdatedata==true)
                    Toast.makeText(MainActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();
            }        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                Boolean checkudeletedata = DB.deleteData(nameTXT);
                if(checkudeletedata==true)
                    Toast.makeText(MainActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
            }        });*/

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getUserData();
                if(res.getCount()==0){
                    Toast.makeText(SignUpActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("ID :"+res.getString(0)+"\n");
                    buffer.append("Name :"+res.getString(1)+"\n");
                    buffer.append("Password :"+res.getString(2)+"\n");
                    buffer.append("Age :"+res.getString(3)+"\n");
                    buffer.append("Description :"+res.getString(4)+"\n");
                    buffer.append("Logo :"+res.getString(5)+"\n");
                    buffer.append("Colour :"+res.getString(6)+"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });


    }
    public void createUserGroups(String name, DBHelper DB){
        //ArrayList of all user data
        ArrayList<String> holdUsers = new ArrayList<>();
        ArrayList<ArrayList<String>> sortUsers = new ArrayList<>();
        Integer newUserId = 0;
        Cursor resGetUser = DB.getUserData();
        String newUserName = name;

        while (resGetUser.moveToNext()) {
            holdUsers.add(resGetUser.getString(0));
            holdUsers.add(resGetUser.getString(1));
            holdUsers.add(resGetUser.getString(2));
            holdUsers.add(resGetUser.getString(3));
            holdUsers.add(resGetUser.getString(4));
            holdUsers.add(resGetUser.getString(5));
            holdUsers.add(resGetUser.getString(6));
            sortUsers.add(holdUsers);
            holdUsers = new ArrayList<>();
            newUserId = Integer.valueOf(resGetUser.getString(0))+1;
        }


        Cursor resNewGroup = DB.getGroupData();
        Integer lastGroupId;
        for (int i = 0; i < sortUsers.size(); i++) {
            //Alphabetical order in naming of groups
            List<String> newNames = Arrays.asList(sortUsers.get(i).get(1),newUserName);
            Collections.sort(newNames);
                DB.insertGroupData(newNames.get(0) + " & " + newNames.get(1));
                resNewGroup = DB.getGroupData();
                resNewGroup.moveToLast();
                lastGroupId = resNewGroup.getInt(0);
                DB.insertGroupUserXData(Integer.valueOf(sortUsers.get(i).get(0)), lastGroupId, "");
                DB.insertGroupUserXData(newUserId, lastGroupId, "");
            }
        }

}