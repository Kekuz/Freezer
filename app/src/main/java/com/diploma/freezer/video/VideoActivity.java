package com.diploma.freezer.video;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.diploma.freezer.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class VideoActivity extends AppCompatActivity {

    TextView captionView;
    ImageView  imageButton;
    YouTubePlayerView youTubePlayerView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        captionView = findViewById(R.id.vidRecipeName);
        imageButton = findViewById(R.id.vid_back_icon);
        youTubePlayerView = findViewById(R.id.youtube_player_view);

        imageButton.setOnClickListener(view -> finish());

        captionView.setText(getIntent().getExtras().getString("caption"));



        IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder()
                .controls(1).ccLoadPolicy(1).fullscreen(0)
                .build();

        getLifecycle().addObserver(youTubePlayerView);

        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(getIntent().getExtras().getString("description"), 0);
            }
        };
        youTubePlayerView.initialize(listener, iFramePlayerOptions);


    }
}