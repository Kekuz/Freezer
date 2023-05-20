package com.diploma.freezer;

import static com.diploma.freezer.MainActivity.currentRecipes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


public class RecipesFragment extends Fragment {
    GridView gridView;
    RecipesItemAdapter recipesItemAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_recipes, container, false);


        gridView = inflatedView.findViewById(R.id.gridView);

        recipesItemAdapter = new RecipesItemAdapter(inflatedView.getContext(), currentRecipes.getRecipeItems());

        gridView.setAdapter(recipesItemAdapter);
        recipesItemAdapter.notifyDataSetChanged();

        return inflatedView;
    }

}