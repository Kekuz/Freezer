package com.diploma.freezer.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.diploma.freezer.R;
import com.squareup.picasso.Picasso;

public class RecipeActivity extends AppCompatActivity {
    TextView captionView, descriptionView, productsTextView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        captionView = findViewById(R.id.captionTextView);
        descriptionView = findViewById(R.id.descriptionTextView);
        imageView = findViewById(R.id.recipeImageView);
        productsTextView = findViewById(R.id.productsTextView);

        descriptionView.setText(Html.fromHtml(getIntent().getExtras().getString("description"), Html.FROM_HTML_MODE_COMPACT));
        captionView.setText(getIntent().getExtras().getString("caption"));
        productsTextView.setText(getIntent().getExtras().getString("ingredients"));

        Picasso.get().load(getIntent().getExtras().getString("image")).into(imageView);
    }
}