package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    EditText email_edittext,password_edittext;
    Button login_btn;
    ProgressBar progressbar_pb;
    TextView signup_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_edittext=findViewById(R.id.email_et);
        password_edittext=findViewById(R.id.password_et);
        login_btn=findViewById(R.id.login_btn);
        progressbar_pb=findViewById(R.id.ProgressBar);
        signup_tv=findViewById(R.id.signupbtn);

        login_btn.setOnClickListener((v)->loginUser());
        signup_tv.setOnClickListener((v)->startActivity(new Intent(login.this,signup.class)));

    }
    void loginUser()
    {
        String email=email_edittext.getText().toString();
        String password=password_edittext.getText().toString();
        boolean isvalidate=validate_details(email,password);
        if(!isvalidate)
        {
            return;
        }
        loginInFirebase(email,password);
    }
    void loginInFirebase(String email,String password)
    {
        ChangeInProgressbar(true);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                ChangeInProgressbar(false);
                if(task.isSuccessful())
                {
                    if(firebaseAuth.getCurrentUser().isEmailVerified())
                    {
                          startActivity(new Intent(login.this,MainActivity.class));
                          finish();
                    }
                    else{
                        Toast.makeText(login.this, "Email is not verified", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(login.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void ChangeInProgressbar(boolean inprogress)
    {
        if(inprogress)
        {
            progressbar_pb.setVisibility(View.VISIBLE);
            login_btn.setVisibility(View.GONE);
        }
        else{
            progressbar_pb.setVisibility(View.GONE);
            login_btn.setVisibility(View.VISIBLE);
        }
    }
    boolean validate_details(String email,String password)
    {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            email_edittext.setError("Email is invalid");
            return false;
        }
        if(password.length()<6)
        {
            password_edittext.setError("Password length is invalid");
            return false;
        }

        return true;
    }
}