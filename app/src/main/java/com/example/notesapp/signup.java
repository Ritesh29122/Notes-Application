package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class signup extends AppCompatActivity {

    EditText email_edittext,password_edittext,confirm_password_et;
    Button createaccount_bt;
    ProgressBar progressbar_pb;
    TextView login_tv;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email_edittext=findViewById(R.id.email_et);
        password_edittext=findViewById(R.id.password_et);
        confirm_password_et=findViewById(R.id.confirmpassword_et);
        createaccount_bt=findViewById(R.id.createaccount_btn);
        progressbar_pb=findViewById(R.id.ProgressBar);
        login_tv=findViewById(R.id.loginbtn);

        createaccount_bt.setOnClickListener(v->CreateAccount());
        login_tv.setOnClickListener(v->finish());
    }
    void CreateAccount()
    {
        String email=email_edittext.getText().toString();
        String password=password_edittext.getText().toString();
        String confirmpassword=confirm_password_et.getText().toString();

        boolean isvalidate=validate_details(email,password,confirmpassword);
        if(!isvalidate)
        {
            return;
        }
        CreateAccountInFirebase(email,password);
    }
    void CreateAccountInFirebase(String email,String password)
    {
        ChangeInProgressbar(true);
        FirebaseAuth firebaseauth=FirebaseAuth.getInstance();
        firebaseauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                ChangeInProgressbar(false);
                if(task.isSuccessful())
                {
                    //Account is created
                    Toast.makeText(signup.this,"Successfully create account, check email to verify",Toast.LENGTH_SHORT).show();
                    firebaseauth.getCurrentUser().sendEmailVerification();
                    firebaseauth.signOut();
                    finish();
                }
                else
                {
                    Toast.makeText(signup.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    void ChangeInProgressbar(boolean inprogress)
    {
        if(inprogress)
        {
            progressbar_pb.setVisibility(View.VISIBLE);
            createaccount_bt.setVisibility(View.GONE);
        }
        else{
            progressbar_pb.setVisibility(View.GONE);
            createaccount_bt.setVisibility(View.VISIBLE);
        }
    }
    boolean validate_details(String email,String password,String confirmpassword)
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
        if(!password.equals(confirmpassword))
        {
            confirm_password_et.setError("Password entered does not match");
            return false;
        }
        return true;
    }
}