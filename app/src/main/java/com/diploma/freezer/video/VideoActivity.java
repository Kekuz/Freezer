package com.diploma.freezer.video;

import static com.diploma.freezer.MainActivity.currentFirebaseUser;
import static com.diploma.freezer.MainActivity.currentRating;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.diploma.freezer.Config;
import com.diploma.freezer.R;
import com.diploma.freezer.RatingItem;
import com.diploma.freezer.logreg.Login;
import com.diploma.freezer.recipes.RecipeActivity;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

public class VideoActivity extends YouTubeBaseActivity {

    TextView captionView, productsTextView, caloriesTextView;
    ImageView  imageButton;

    YouTubePlayerView youTubePlayerView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        captionView = findViewById(R.id.vidRecipeName);
        productsTextView = findViewById(R.id.vidProductsTextView);
        imageButton = findViewById(R.id.vid_back_icon);
        caloriesTextView = findViewById(R.id.vidCaloriesTextView);

        youTubePlayerView = findViewById(R.id.youTubeVideo);

        imageButton.setOnClickListener(view -> finish());

        String recipeName = getIntent().getExtras().getString("caption");

        captionView.setText(recipeName);
        productsTextView.setText(getIntent().getExtras().getString("ingredients"));
        caloriesTextView.setText(VideoActivity.this.getString(R.string.calories_count) + " " + getIntent().getExtras().getString("calories"));


        YouTubePlayer.OnInitializedListener listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(getIntent().getExtras().getString("description"));
                Log.d("Video description: ",getIntent().getExtras().getString("description") );
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(), VideoActivity.this.getString(R.string.loading_error), Toast.LENGTH_SHORT).show();
            }
        };

        youTubePlayerView.initialize(Config.YOUTUBE_API_KEY, listener);


    }
}