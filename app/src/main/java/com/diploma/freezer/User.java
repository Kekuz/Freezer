package com.diploma.freezer;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class User {
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private String email, name;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference usersInfoReference;
    User(){
        this.mAuth = FirebaseAuth.getInstance();
        this.firebaseUser = mAuth.getCurrentUser();
        this.email = firebaseUser.getEmail();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        usersInfoReference = firebaseFirestore.collection("users").document(email);
        usersInfoReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        name = document.getData().get("name").toString();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public String getName() {
        return name;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }
}
