package com.diploma.freezer.fridge;


import static com.diploma.freezer.MainActivity.currentFridge;
import static com.diploma.freezer.MainActivity.userFridge;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.diploma.freezer.R;
import com.diploma.freezer.logreg.Login;
import com.google.firebase.auth.FirebaseAuth;

public class FreezerFragment extends Fragment {

    RecyclerView recyclerView;
    FreezerItemsAdapter freezerItemsAdapter;
    Button addItemButton;


    @SuppressLint({"NotifyDataSetChanged", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_freezer, container, false);
        addItemButton = inflatedView.findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflatedView.getContext(), AllFreezerItemsActivity.class);
                startActivity(intent);
            }
        });



        recyclerView = inflatedView.findViewById(R.id.recyclerViewFreezer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflatedView.getContext()));

        freezerItemsAdapter = new FreezerItemsAdapter(inflatedView.getContext(), userFridge);
        recyclerView.setAdapter(freezerItemsAdapter);

        freezerItemsAdapter.notifyDataSetChanged();

        return inflatedView;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        freezerItemsAdapter.notifyDataSetChanged();
    }
}