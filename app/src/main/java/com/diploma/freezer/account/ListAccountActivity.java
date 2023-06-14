package com.diploma.freezer.account;

import static com.diploma.freezer.MainActivity.currentFirebaseUser;
import static com.diploma.freezer.MainActivity.currentFridge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.diploma.freezer.R;
import com.diploma.freezer.fridge.FreezerItemsUserAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListAccountActivity extends AppCompatActivity {
    String type;
    RecyclerView recyclerView;
    ListItemAdapter listItemAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_account);

        recyclerView = findViewById(R.id.recyclerViewList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        type = getIntent().getExtras().getString("type");

        if (type.equals("favorites")){

            listItemAdapter = new ListItemAdapter(this, convertFavorites(currentFirebaseUser.getFavorites()));//тут передаем общий адаптер
            recyclerView.setAdapter(listItemAdapter);
        }else{

            listItemAdapter = new ListItemAdapter(this, convertRating(currentFirebaseUser.getUserRating()));//тут передаем общий адаптер
            recyclerView.setAdapter(listItemAdapter);
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