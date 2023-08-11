package com.example.Splash_Screen_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.online_voting_app.R;
import com.example.online_voting_app.login_activity;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(()->{
            startActivity(new Intent(Splash_Screen.this, login_activity.class));
            finish();
        },3000);

    }
}