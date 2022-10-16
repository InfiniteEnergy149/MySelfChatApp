package com.example.myselfchatapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {
    EditText nameEnterTXT, passwordEnterTXT;
    Button signInClick;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        DB = new DBHelper(this);
       signInClick = findViewById(R.id.signInBtn);
        nameEnterTXT = findViewById(R.id.nameEnter);
        passwordEnterTXT= findViewById(R.id.passwordEnter);

        signInClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getUserData();
                if(res.getCount()==0){
                    Toast.makeText(SignInActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                Boolean checkCredentials = false;
                Integer currentUserId = 0;

                //Check credentials against records
               StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    if (res.getString(1).equals(nameEnterTXT.getText().toString())
                    && res.getString(2).equals(passwordEnterTXT.getText().toString())){
                        checkCredentials = true;
                        currentUserId = Integer.valueOf(res.getString(0));
                    }
                }
                 if (checkCredentials == true){ //Username and Password are in records
                     //Send User to displayActivity scene
                     Intent myIntent = new Intent(SignInActivity.this,DisplayActivity.class);
                     //Pass currentUserId to next activity

                     myIntent.putExtra("userId",currentUserId.toString());
                     startActivity(myIntent);
                 }else { //Username and Password are in records - Error Alert
                     AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                     builder.setCancelable(true);
                     builder.setTitle("User Entries");
                     builder.setMessage("Username and Password Don't match Records");
                     builder.show();
                 }
            }        });
    }


}