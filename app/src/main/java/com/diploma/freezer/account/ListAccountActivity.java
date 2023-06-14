package com.diploma.freezer.account;

import static com.diploma.freezer.MainActivity.currentFirebaseUser;
import static com.diploma.freezer.MainActivity.currentRating;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.diploma.freezer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListAccountActivity extends AppCompatActivity {
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

            listItemAdapter = new ListItemAdapter(this, convertedFavorites);//тут передаем общий адаптер
            recyclerView.setAdapter(listItemAdapter);
            listNameView.setText(ListAccountActivity.this.getString(R.string.favorites));

        }else{

            listItemAdapter = new ListItemAdapter(this, convertedRating);//тут передаем общий адаптер
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

            Toast.makeText(ListAccountActivity.this, ListAccountActivity.this.getString(R.string.product_deleted), Toast.LENGTH_SHORT).show();

            if(type.equals("favorites")){

                currentFirebaseUser.getFavorites().remove(viewHolder.getAdapterPosition());
                convertedFavorites.remove(viewHolder.getAdapterPosition());

                currentFirebaseUser.saveFavoritesListFirebase(convertedFavorites);

                listItemAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }else{


                List<String> keys = new ArrayList<>(currentFirebaseUser.getUserRating().keySet());
                String name = keys.get(viewHolder.getAdapterPosition());// Имя рецепта



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
}