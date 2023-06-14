package com.diploma.freezer.account;

import static com.diploma.freezer.MainActivity.currentFirebaseUser;
import static com.diploma.freezer.MainActivity.currentFridge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.diploma.freezer.R;
import com.diploma.freezer.fridge.FreezerItemsUserAdapter;
import com.diploma.freezer.logreg.Login;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListAccountActivity extends AppCompatActivity {
    String type;
    RecyclerView recyclerView;
    ListItemAdapter listItemAdapter;
    ImageView imageButton;
    TextView listNameView;

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



        type = getIntent().getExtras().getString("type");

        if (type.equals("favorites")){

            listItemAdapter = new ListItemAdapter(this, convertFavorites(currentFirebaseUser.getFavorites()));//тут передаем общий адаптер
            recyclerView.setAdapter(listItemAdapter);
            listNameView.setText(ListAccountActivity.this.getString(R.string.favorites));
        }else{

            listItemAdapter = new ListItemAdapter(this, convertRating(currentFirebaseUser.getUserRating()));//тут передаем общий адаптер
            recyclerView.setAdapter(listItemAdapter);
            listNameView.setText(ListAccountActivity.this.getString(R.string.rating));
        }

    }

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
            String value = in.get(key);
            out.add(new ListItem(key,value));
        }
        return out;
    }
}