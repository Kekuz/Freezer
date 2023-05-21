package com.diploma.freezer.fridge.recipes;

import static com.diploma.freezer.MainActivity.currentRecipes;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.diploma.freezer.R;


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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("GridViewInfo: ", "position " + i + " "+ currentRecipes.getRecipeItems().get(i));

                Intent intent = new Intent(inflatedView.getContext(), RecipeActivity.class);

                intent.putExtra("caption", currentRecipes.getRecipeItems().get(i).getCaption());
                intent.putExtra("image", currentRecipes.getRecipeItems().get(i).getImage());
                intent.putExtra("description", currentRecipes.getRecipeItems().get(i).getDescription());
                startActivity(intent);
            }
        });

        //recipesItemAdapter.notifyDataSetChanged();

        return inflatedView;
    }

}