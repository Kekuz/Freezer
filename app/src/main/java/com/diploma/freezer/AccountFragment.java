package com.diploma.freezer;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class AccountFragment extends Fragment {
    Button button;
    TextView nameTextView;
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference userInfoReference;
    String userName;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_account, container, false);
        button = inflatedView.findViewById(R.id.logout_button);
        auth = FirebaseAuth.getInstance();
        nameTextView = inflatedView.findViewById(R.id.details);
        user = auth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userInfoReference = firebaseFirestore.collection("users").document(user.getEmail());//получаю референс
        userInfoReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        userName = document.getData().get("name").toString();
                        nameTextView.setText(" " + userName);
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        if (user == null){
            Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
            startActivity(intent);
            getActivity().finish();
        }else {
            //textView.setText(user.getEmail());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return inflatedView;
    }
}