package com.example.myselfchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //To signIn Scene
        Button signInClick = (Button) findViewById(R.id.signInBtn);
        signInClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(myIntent);
            }
        });

        //To signUp Scene
        Button signUpClick = (Button) findViewById(R.id.signUpBtn);
        signUpClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(myIntent);
            }
        });
    }


}