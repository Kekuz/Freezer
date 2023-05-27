package com.diploma.freezer.recipes;

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

import com.diploma.freezer.R;
import com.diploma.freezer.RatingItem;
import com.squareup.picasso.Picasso;

public class RecipeActivity extends AppCompatActivity {
    TextView captionView, descriptionView, productsTextView, textViewRating;
    ImageView imageView, imageButton;
    RatingBar ratingBar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        captionView = findViewById(R.id.recipeName);
        descriptionView = findViewById(R.id.descriptionTextView);
        imageView = findViewById(R.id.recipeImageView);
        productsTextView = findViewById(R.id.productsTextView);
        imageButton = findViewById(R.id.back_icon);
        ratingBar = findViewById(R.id.ratingBar);
        textViewRating = findViewById(R.id.textViewRating);

        imageButton.setOnClickListener(view -> finish());

        String recipeName = getIntent().getExtras().getString("caption");

        descriptionView.setText(Html.fromHtml(getIntent().getExtras().getString("description"), Html.FROM_HTML_MODE_COMPACT));
        captionView.setText(recipeName);
        productsTextView.setText(getIntent().getExtras().getString("ingredients"));

        try {
            Picasso.get().load(getIntent().getExtras().getString("image")).into(imageView);
        }catch (Exception e){
            imageView.setImageResource(R.drawable.ic_null_fastfood_24);
        }

        ratingBar.setRating(currentFirebaseUser.getRating(recipeName));


        if (currentRating.findByName(recipeName) != null){
            textViewRating.setVisibility(View.VISIBLE);
            textViewRating.setText(String.format("%.1f", Float.parseFloat(currentRating.findByName(recipeName).getSum()) / Float.parseFloat(currentRating.findByName(recipeName).getCount())) + "/5.0");
        }


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                currentFirebaseUser.rating(recipeName, v);//сохраняем оценку пользователя в его профиле онлайн


                currentRating.sendRating(recipeName, v);//отправляем в общий пул рейтинга онлайн

                //По хорошему код ниже должен быть в классе User, но я устал и не хочу переносить его. Главное что все работает)
                if (currentRating.findByName(recipeName) == null){ //отправляем в общий пул рейтинга локально
                    RatingItem newRatingItem = new RatingItem(recipeName, "1",String.valueOf(v));
                    currentRating.getRatingItems().add(newRatingItem);
                    Log.i("Added to local:", newRatingItem.toString());
                }else{
                    RatingItem ratingItem = currentRating.findByName(recipeName);
                    if(!currentFirebaseUser.getUserRating().containsKey(recipeName)){
                        ratingItem.setCount(String.valueOf(Float.parseFloat(ratingItem.getCount()) + 1));
                        ratingItem.setSum(String.valueOf(Float.parseFloat(ratingItem.getSum()) + v));
                    }else{
                        ratingItem.setSum(String.valueOf(Float.parseFloat(ratingItem.getSum()) - Float.parseFloat(currentFirebaseUser.getUserRating().get(recipeName)) + v));
                    }
                }


                currentFirebaseUser.getUserRating().put(recipeName, String.valueOf(v));//добавляем в локальный список оценок, чтобы отображать его далее в приложении

                textViewRating.setVisibility(View.VISIBLE);
                textViewRating.setText(String.format("%.1f", Float.parseFloat(currentRating.findByName(recipeName).getSum()) / Float.parseFloat(currentRating.findByName(recipeName).getCount())) + "/5.0");
            }
        });
    }

}