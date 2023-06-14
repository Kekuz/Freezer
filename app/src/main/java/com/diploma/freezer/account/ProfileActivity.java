package com.diploma.freezer.account;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.diploma.freezer.R;

public class ProfileActivity extends AppCompatActivity {
    ImageView imageButton;
    TextView listNameView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        listNameView = findViewById(R.id.list_name);
        listNameView.setText(ProfileActivity.this.getString(R.string.title_profile));

        imageButton = findViewById(R.id.back_icon);
        imageButton.setOnClickListener(view -> finish());
    }
}