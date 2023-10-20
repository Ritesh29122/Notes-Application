package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.time.Instant;

public class details extends AppCompatActivity {
    EditText Title_Edittext,Content_Edittext;
    ImageView saveImageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Title_Edittext=findViewById(R.id.notes_title_et);
        Content_Edittext=findViewById(R.id.notes_content_et);
        saveImageview=findViewById(R.id.save_iv);
        saveImageview.setOnClickListener((v)->savingNote());
    }
    void savingNote()
    {
        String noteTitle=Title_Edittext.getText().toString();
        String noteContent=Content_Edittext.getText().toString();
        if(noteTitle==null || noteTitle.isEmpty())
        {
            Title_Edittext.setError("Title is required");
            return;
        }
        Note note=new Note();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        SaveNoteinFirebase(note);
        note.setTimestamp(Timestamp.now());

    }
    void SaveNoteinFirebase(Note note)
    {
        DocumentReference documentReference;
        documentReference=utility.getCollectionReferenceforNotes().document();
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(details.this, "Notes Added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(details.this, "Failed while adding Notes", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}