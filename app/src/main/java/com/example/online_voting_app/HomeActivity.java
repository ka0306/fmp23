package com.example.online_voting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    public static final String PREFERENCE="prefKey";
    SharedPreferences sharedPreferences;
    public static final String IsLogIn="islogin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences= getApplicationContext().getSharedPreferences(PREFERENCE,MODE_PRIVATE);
        SharedPreferences.Editor pref =sharedPreferences.edit();
        pref.putString(IsLogIn, String.valueOf(true));
        pref.apply();
        findViewById(R.id.log_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, login_activity.class));
                finish();
            }
        });
    }
}