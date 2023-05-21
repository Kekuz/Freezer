package com.diploma.freezer.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.diploma.freezer.R;
import com.squareup.picasso.Picasso;

public class RecipeActivity extends AppCompatActivity {
    TextView captionView, descriptionView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        captionView = findViewById(R.id.captionTextView);
        descriptionView = findViewById(R.id.descriptionTextView);
        imageView = findViewById(R.id.recipeImageView);


        descriptionView.setText(getIntent().getExtras().getString("description"));
        captionView.setText(getIntent().getExtras().getString("caption"));
        Picasso.get().load(getIntent().getExtras().getString("image")).into(imageView);
    }
}