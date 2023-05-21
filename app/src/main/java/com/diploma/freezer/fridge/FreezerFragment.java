package com.diploma.freezer.fridge;


import static com.diploma.freezer.MainActivity.currentFridge;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diploma.freezer.R;

public class FreezerFragment extends Fragment {

    RecyclerView recyclerView;
    FreezerItemsAdapter freezerItemsAdapter;


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_freezer, container, false);


        recyclerView = inflatedView.findViewById(R.id.recyclerViewFreezer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflatedView.getContext()));

        freezerItemsAdapter = new FreezerItemsAdapter(inflatedView.getContext(), currentFridge.getFreezerItems());
        recyclerView.setAdapter(freezerItemsAdapter);

        freezerItemsAdapter.notifyDataSetChanged();

        return inflatedView;
    }
}