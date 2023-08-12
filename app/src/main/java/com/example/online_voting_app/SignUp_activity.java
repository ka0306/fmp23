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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp_activity extends AppCompatActivity {
    private CircleImageView userProfile;
    private EditText userName,userPassword,userEmail,userNationalID;
    private Button singUpBtn;
    private FirebaseAuth mAuth;



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

        mAuth=FirebaseAuth.getInstance();

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
        singUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=userName.getText().toString().trim();
                String password=userPassword.getText().toString().trim();
                String email=userEmail.getText().toString().trim();
                String nationalId=userNationalID.getText().toString().trim();

                if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(password)
                        &&!TextUtils.isEmpty(email)&&Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        &&!TextUtils.isEmpty(nationalId)){
                    createUser(email,password);
                }else {
                    Toast.makeText(SignUp_activity.this, "Please enter your credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

}

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful()){
               Toast.makeText(SignUp_activity.this, " User created", Toast.LENGTH_SHORT).show();

           }else{
               Toast.makeText(SignUp_activity.this, "Fail Try again", Toast.LENGTH_SHORT).show();

           }
        }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUp_activity.this, "Some thing went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void cropImage() {


      }
    }