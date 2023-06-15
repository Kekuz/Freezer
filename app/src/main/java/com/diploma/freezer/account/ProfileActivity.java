package com.diploma.freezer.account;

import static com.diploma.freezer.MainActivity.currentFirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.diploma.freezer.R;
import com.diploma.freezer.logreg.Login;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    ImageView imageButton;
    TextView listNameView, profileName, profileEmail, adminProfile, countRatingProfile;
    Button exitButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        listNameView = findViewById(R.id.list_name);
        listNameView.setText(ProfileActivity.this.getString(R.string.title_profile));

        exitButton = findViewById(R.id.logout_button);

        imageButton = findViewById(R.id.back_icon);
        imageButton.setOnClickListener(view -> finish());

        profileName = findViewById(R.id.profile_name);
        profileName.setText(currentFirebaseUser.getName());

        profileEmail = findViewById(R.id.profile_email);
        profileEmail.setText(currentFirebaseUser.getFirebaseUser().getEmail());

        adminProfile = findViewById(R.id.admin_profile);
        if (currentFirebaseUser.isAdmin())
            adminProfile.setText(this.getString(R.string.yes));
        else adminProfile.setText(this.getString(R.string.no));

        countRatingProfile = findViewById(R.id.count_rating_profile);
        countRatingProfile.setText(String.valueOf(currentFirebaseUser.getUserRating().size()));


        exitButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ProfileActivity.this.getApplicationContext(), Login.class);
            startActivity(intent);
            ProfileActivity.this.finish();
        });
    }
}