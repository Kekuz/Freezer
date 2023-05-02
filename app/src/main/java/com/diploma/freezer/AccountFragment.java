package com.diploma.freezer;

import static com.diploma.freezer.MainActivity.currentFirebaseUser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;


public class AccountFragment extends Fragment {
    Button button;
    TextView nameTextView;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_account, container, false);
        button = inflatedView.findViewById(R.id.logout_button);
        nameTextView = inflatedView.findViewById(R.id.details);
        nameTextView.setText(currentFirebaseUser.getName());


        if (currentFirebaseUser.getFirebaseUser() == null){
            Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
            startActivity(intent);
            getActivity().finish();
        }else {
            //nameTextView.setText(currentFirebaseUser.getName());
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