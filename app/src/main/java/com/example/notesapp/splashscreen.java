package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class splashscreen extends AppCompatActivity {
    TextView notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Intent intent=new Intent();
        notes=findViewById(R.id.tvnotes);
        FirebaseUser currentuser= FirebaseAuth.getInstance().getCurrentUser();
        if(currentuser==null)
        {
            startActivity(new Intent(splashscreen.this,login.class));
        }
        else{
            startActivity(new Intent(splashscreen.this,MainActivity.class));
        }
        finish();
    }

}