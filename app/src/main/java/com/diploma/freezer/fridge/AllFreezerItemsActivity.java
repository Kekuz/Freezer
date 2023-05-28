package com.diploma.freezer.fridge;

import static com.diploma.freezer.MainActivity.currentFridge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.diploma.freezer.R;

import java.util.ArrayList;
import java.util.List;

public class AllFreezerItemsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FreezerItemsUserAdapter freezerItemsUserAdapter;
    SearchView searchView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_freezer_items);

        searchView = findViewById(R.id.search_view);
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.clearFocus();

        recyclerView = findViewById(R.id.recyclerViewFreezer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        freezerItemsUserAdapter = new FreezerItemsUserAdapter(this, currentFridge.getFreezerItems());//тут передаем общий адаптер
        recyclerView.setAdapter(freezerItemsUserAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

    }

    private void filter(String newText) {
        ArrayList<FreezerItem> filteredList = new ArrayList<>();
        for(FreezerItem item : currentFridge.getFreezerItems()){
            if(item.getFoodName().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(item);
            }
        }
        freezerItemsUserAdapter.filterList(filteredList);
    }
}