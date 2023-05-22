package com.diploma.freezer.fridge;

import static com.diploma.freezer.MainActivity.currentFridge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.diploma.freezer.R;

public class AllFreezerItemsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FreezerItemsAdapter freezerItemsAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_freezer_items);

        recyclerView = findViewById(R.id.recyclerViewFreezer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        freezerItemsAdapter = new FreezerItemsUserAdapter(this, currentFridge.getFreezerItems());//тут передаем общий адаптер
        recyclerView.setAdapter(freezerItemsAdapter);

    }
}