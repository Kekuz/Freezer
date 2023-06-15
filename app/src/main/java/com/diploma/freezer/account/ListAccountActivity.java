package com.diploma.freezer.account;

import static com.diploma.freezer.MainActivity.currentFirebaseUser;
import static com.diploma.freezer.MainActivity.currentRating;
import static com.diploma.freezer.MainActivity.currentRecipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.diploma.freezer.R;
import com.diploma.freezer.recipes.RecipeActivity;
import com.diploma.freezer.recipes.RecipeItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListAccountActivity extends AppCompatActivity implements RecyclerViewInterface {
    String type;
    RecyclerView recyclerView;
    ListItemAdapter listItemAdapter;
    ImageView imageButton;
    TextView listNameView;
    ArrayList<ListItem> convertedFavorites, convertedRating;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_account);

        recyclerView = findViewById(R.id.recyclerViewList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listNameView = findViewById(R.id.list_name);

        imageButton = findViewById(R.id.back_icon);
        imageButton.setOnClickListener(view -> finish());

        convertedFavorites = convertFavorites(currentFirebaseUser.getFavorites());
        convertedRating = convertRating(currentFirebaseUser.getUserRating());


        type = getIntent().getExtras().getString("type");



        if (type.equals("favorites")){

            listItemAdapter = new ListItemAdapter(this, convertedFavorites, this);//тут передаем общий адаптер
            recyclerView.setAdapter(listItemAdapter);
            listNameView.setText(ListAccountActivity.this.getString(R.string.favorites));

        }else{

            listItemAdapter = new ListItemAdapter(this, convertedRating,this);//тут передаем общий адаптер
            recyclerView.setAdapter(listItemAdapter);
            listNameView.setText(ListAccountActivity.this.getString(R.string.rating));
        }

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


            if(type.equals("favorites")){

                String name = currentFirebaseUser.getFavorites().get(viewHolder.getAdapterPosition());
                currentFirebaseUser.getFavorites().remove(viewHolder.getAdapterPosition());
                convertedFavorites.remove(viewHolder.getAdapterPosition());

                currentFirebaseUser.saveFavoritesListFirebase(convertedFavorites);

                listItemAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                Toast.makeText(ListAccountActivity.this, name + " " + ListAccountActivity.this.getString(R.string.deleted_from_favorites), Toast.LENGTH_SHORT).show();

            }else{


                List<String> keys = new ArrayList<>(currentFirebaseUser.getUserRating().keySet());
                String name = keys.get(viewHolder.getAdapterPosition());// Имя рецепта

                Toast.makeText(ListAccountActivity.this, name + " " + ListAccountActivity.this.getString(R.string.deleted_from_rating), Toast.LENGTH_SHORT).show();



                currentRating.updateRatingItem(name);//обновляем локальный рейтинг рецептов

                currentFirebaseUser.getUserRating().remove(name);//удаляем из профиля пользователя локально

                convertedRating.remove(viewHolder.getAdapterPosition());// удяляем отсюда чтобы окно правильно обновилось

                currentFirebaseUser.updateRatingFirebase(currentRating.findByName(name));//обновляем и у пользователя и в общем рейтинге онлайн



                listItemAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }
    };

    private ArrayList<ListItem> convertFavorites(ArrayList<String> in){
        ArrayList<ListItem> out = new ArrayList<>();
        for (String x : in) {
            out.add(new ListItem(x,""));
        }
        return out;
    }

    private ArrayList<ListItem> convertRating(Map<String,String> in){
        ArrayList<ListItem> out = new ArrayList<>();

        List<String> keys = new ArrayList<>(in.keySet());
        for(int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = in.get(key).charAt(0) + "/5";
            out.add(new ListItem(key,value));
        }
        return out;
    }

    @Override
    public void onItemClick(int position, String name) {
        if (type.equals("favorites")){
            Intent intent = new Intent(this, RecipeActivity.class);

            RecipeItem recipeItem = currentRecipes.findByName(name);

            Log.d("GridViewInfo: ", "position " + position + " "+ name);

            intent.putExtra("caption", recipeItem.getCaption());
            intent.putExtra("image", recipeItem.getImage());
            intent.putExtra("description", recipeItem.getDescription());
            intent.putExtra("ingredients",recipeItem.getStringIngredients());
            intent.putExtra("calories",recipeItem.getCalories());

            startActivity(intent);
        }
    }
}