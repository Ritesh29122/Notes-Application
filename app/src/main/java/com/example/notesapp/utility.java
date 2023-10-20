package com.example.notesapp;
import android.content.Context;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class utility {
    static CollectionReference getCollectionReferenceforNotes()
    {
        FirebaseUser currentuser= FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notes").document(currentuser.getUid()).collection("my_notes");

    }
    static String timestampToString(Timestamp timestamp)
    {
        return new SimpleDateFormat("mm/dd/yyyy").format(Timestamp.now().toDate());
    }
}
