package com.example.myselfchatapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    EditText name, password, age, description, logo, colour;
    Button insert, update, delete, view;
    DBHelper DB;
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
        logo = findViewById(R.id.logoEnter);
        colour = findViewById(R.id.colourEnter);

        insert = findViewById(R.id.addUserBtn);
        view = findViewById(R.id.showDataBtn);

        DB = new DBHelper(this);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String passwordTXT = password.getText().toString();
                Integer ageTXT = Integer.valueOf(age.getText().toString());
                String descriptionTXT = description.getText().toString();
                String logoTXT = logo.getText().toString();
                String colourTXT = colour.getText().toString();

                Boolean checkInsertData = DB.insertUserData(nameTXT, passwordTXT, ageTXT, descriptionTXT, logoTXT, colourTXT);
                if(checkInsertData==true)
                    Toast.makeText(SignUpActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SignUpActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
            }        });
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
                Cursor res = DB.getData();
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
            }        });
    }}