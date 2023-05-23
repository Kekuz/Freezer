package com.diploma.freezer.recipes;

import static com.diploma.freezer.MainActivity.currentRecipes;
import static com.diploma.freezer.MainActivity.userFridge;

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
import com.diploma.freezer.fridge.FreezerItem;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;


public class RecipesFragment extends Fragment {
    GridView gridView;
    public static RecipesItemAdapter recipesItemAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_recipes, container, false);


        gridView = inflatedView.findViewById(R.id.gridView);

        //recipesItemAdapter = new RecipesItemAdapter(inflatedView.getContext(), currentRecipes.getRecipeItems());
        recipesItemAdapter = new RecipesItemAdapter(inflatedView.getContext(), FilteredRecipes());
        gridView.setAdapter(recipesItemAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("GridViewInfo: ", "position " + i + " "+ currentRecipes.getRecipeItems().get(i));

                Intent intent = new Intent(inflatedView.getContext(), RecipeActivity.class);

                intent.putExtra("caption", currentRecipes.getRecipeItems().get(i).getCaption());
                intent.putExtra("image", currentRecipes.getRecipeItems().get(i).getImage());
                intent.putExtra("description", currentRecipes.getRecipeItems().get(i).getDescription());
                intent.putExtra("ingredients",currentRecipes.getRecipeItems().get(i).getStringIngredients());
                startActivity(intent);
            }
        });

        return inflatedView;
    }

    private ArrayList<RecipeItem> FilteredRecipes(){

        ArrayList<RecipeItem> res = new ArrayList<>();

        ArrayList<RecipeItem> allRec = currentRecipes.getRecipeItems(); //все рецепты

        ArrayList<String> userIng = new ArrayList<>();//еда в холодильнике
        for (FreezerItem x :userFridge) {
            userIng.add(x.getFoodName());
        }

        for (RecipeItem x: allRec) {
            ArrayList<String> recipeIng = (ArrayList<String>) x.getIngredients();
            Log.i("recipeIng: ", x.getIngredients().toString());
            if(recipeIng.equals(userIng)) res.add(x);
        }
        if (res.isEmpty() && userIng.isEmpty()) return allRec;
        else return res;
    }

}