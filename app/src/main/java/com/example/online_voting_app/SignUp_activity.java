package com.example.online_voting_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp_activity extends AppCompatActivity {
    private CircleImageView userProfile;
    private EditText userName,userPassword,userEmail,userNationalID;
    private Button singUpBtn;
    private Uri mainUri=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewById(R.id.have_acc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userProfile=findViewById(R.id.profile_image);
        userName=findViewById(R.id.user_name);
        userPassword=findViewById(R.id.user_password);
        userEmail=findViewById(R.id.user_email);
        userNationalID=findViewById(R.id.user_national_id);
        singUpBtn=findViewById(R.id.Signup_btn);

        // new line git


        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if(ContextCompat.checkSelfPermission(SignUp_activity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                        ActivityCompat.requestPermissions(SignUp_activity.this,
                                new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    }else {
                        cropImage();
                    }
                }else {
                    cropImage();
                }
            }
        });

}

    private void cropImage() {


      }
    }