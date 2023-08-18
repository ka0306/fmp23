package com.example.Splash_Screen_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.online_voting_app.HomeActivity;
import com.example.online_voting_app.R;
import com.example.online_voting_app.login_activity;

public class Splash_Screen extends AppCompatActivity {
    public static final String PREFERENCE="prefKey";
    SharedPreferences sharedPreferences;
    public static final String IsLogIn="islogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
        @Override
                protected void onStart(){
                 super.onStart();

            sharedPreferences = getApplicationContext().getSharedPreferences(PREFERENCE,MODE_PRIVATE);
               boolean bol=sharedPreferences.getBoolean(IsLogIn,false);

           new Handler().postDelayed(()->{
               if(bol){
                   startActivity(new Intent(Splash_Screen.this, HomeActivity.class));
                   finish();
               }else {
                   startActivity(new Intent(Splash_Screen.this, login_activity.class));
                   finish();
               }
        },3000);

    }
}

