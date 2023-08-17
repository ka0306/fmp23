package com.example.online_voting_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class login_activity extends AppCompatActivity {
   private EditText userEmail,userPassword;
   private Button loginBtn;
   private TextView forgetPassword;
   private FirebaseAuth mAuth;

    public static final String PREFERENCE ="prefKey";
    public static final String Name="nameKey";
    public static final String Email="emailKey";
    public static final String Password="passwordKey";
    public static final String NationalId="nationalIdKey";
    public static final String Image="imageKey";
    SharedPreferences sharedPreferences;
    StorageReference reference;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_avtivity);

        sharedPreferences= getApplicationContext().getSharedPreferences(PREFERENCE,MODE_PRIVATE);
        reference= FirebaseStorage.getInstance().getReference();
        firebaseFirestore= FirebaseFirestore.getInstance();

        findViewById(R.id.dont_have_acc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_activity.this,SignUp_activity.class));
            }
        });
        userEmail=findViewById(R.id.user_email);
        userPassword=findViewById(R.id.user_password);
        loginBtn=findViewById(R.id.Login_btn);
        forgetPassword=findViewById(R.id.forget_password);
        mAuth=FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=userEmail.getText().toString().trim();
                String password=userPassword.getText().toString().trim();
           mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                       verifyEmail();
                        }else {
                            Toast.makeText(login_activity.this, "User not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
    }
    private void verifyEmail() {
        FirebaseUser user=mAuth.getCurrentUser();
        assert user != null;
        if(user.isEmailVerified()){

            String name=sharedPreferences.getString(Name,null);
            String password=sharedPreferences.getString(Password,null);
            String email=sharedPreferences.getString(Email,null);
            String nationalId=sharedPreferences.getString(NationalId,null);
            String image=sharedPreferences.getString(Image,null);

           if(name !=null && password !=null && email !=null && nationalId !=null && image !=null) {
               String uid = mAuth.getUid();

               StorageReference imagePath = reference.child("image_profile").child(uid + "jpg");
               imagePath.putFile(Uri.parse(image)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                       if (task.isSuccessful()) {
                           imagePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   Map<String, String> map = new HashMap<>();
                                   map.put("name", name);
                                   map.put("email", email);
                                   map.put("password", password);
                                   map.put("nationalId", nationalId);
                                   map.put("image", uri.toString());
                                   map.put("uid", uid);
                                   firebaseFirestore.collection("users")
                                           .document(uid)
                                             .set(map)
                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task) {
                                                   if (task.isSuccessful()) {

                             startActivity(new Intent(login_activity.this, HomeActivity.class));
                                finish();
                      } else {
                      Toast.makeText(login_activity.this, "Data not store", Toast.LENGTH_SHORT).show();
                     }
                    }
                   });
                }
             });

           } else {

            Toast.makeText(login_activity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
           }
        }
         });
           }
        }else{
            mAuth.signOut();
            Toast.makeText(this, "Please Verify your Email", Toast.LENGTH_SHORT).show();
        }
    }
}