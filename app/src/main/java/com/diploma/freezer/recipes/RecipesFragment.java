package com.diploma.freezer.recipes;

import static com.diploma.freezer.MainActivity.currentFirebaseUser;
import static com.diploma.freezer.MainActivity.currentRecipes;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.appcompat.widget.SearchView;

import com.diploma.freezer.R;
import com.diploma.freezer.fridge.FreezerItem;


import java.util.ArrayList;


public class RecipesFragment extends Fragment {
    GridView gridView;
    public static SearchView adminSearchView;
    public static RecipesItemAdapter recipesItemAdapter;



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_recipes, container, false);


        gridView = inflatedView.findViewById(R.id.gridView);

        adminSearchView = inflatedView.findViewById(R.id.admin_search_view);
        adminSearchView.onActionViewExpanded();
        adminSearchView.setIconified(false);
        adminSearchView.clearFocus();

        if (currentFirebaseUser.isAdmin()) adminSearchView.setVisibility(View.VISIBLE);

        adminSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFilter(newText);
                return true;
            }
        });

        //recipesItemAdapter = new RecipesItemAdapter(inflatedView.getContext(), currentRecipes.getRecipeItems());
        ArrayList<RecipeItem> filtered = FilteredRecipes();
        recipesItemAdapter = new RecipesItemAdapter(inflatedView.getContext(), filtered);
        gridView.setAdapter(recipesItemAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("GridViewInfo: ", "position " + i + " "+ filtered.get(i));

                Intent intent = new Intent(inflatedView.getContext(), RecipeActivity.class);

                intent.putExtra("caption", filtered.get(i).getCaption());
                intent.putExtra("image", filtered.get(i).getImage());
                intent.putExtra("description", filtered.get(i).getDescription());
                intent.putExtra("ingredients",filtered.get(i).getStringIngredients());
                startActivity(intent);
            }
        });

        return inflatedView;
    }

    public ArrayList<RecipeItem> FilteredRecipes(){

        ArrayList<RecipeItem> res = new ArrayList<>();

        ArrayList<RecipeItem> allRec = currentRecipes.getRecipeItems(); //все рецепты

        ArrayList<String> userIng = new ArrayList<>();//еда в холодильнике
        for (FreezerItem x :currentFirebaseUser.getUserFridge()) {
            userIng.add(x.getFoodName());
        }

        // Основной алгоритм
        for (RecipeItem x: allRec) {
            ArrayList<String> recipeIng = (ArrayList<String>) x.getIngredients(); //ингридиенты рецепта
            int count = 0;
            for (String y: recipeIng) {
                for (String z: userIng){
                    if(y.equals(z)){
                        count++;
                    }
                }
            }
            if(recipeIng.size() == count) res.add(x);// Если количество совпадений равно количеству ингридиентов в рецепте, то добавляем
            Log.i("recipeIng: ", x.getIngredients().toString());
        }
        return (res.isEmpty() && userIng.isEmpty()) ? allRec : res;
        //return res;
    }
    private void searchFilter(String newText) {
        ArrayList<RecipeItem> filteredList = new ArrayList<>();
        for(RecipeItem item : currentRecipes.getRecipeItems()){
            if(item.getCaption().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(item);
            }
        }
        recipesItemAdapter.filterList(filteredList);
    }
}