package com.diploma.freezer.video;

import static com.diploma.freezer.MainActivity.currentFirebaseUser;
import static com.diploma.freezer.MainActivity.currentVideos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.diploma.freezer.R;
import com.diploma.freezer.recipes.RecipeActivity;
import com.diploma.freezer.recipes.RecipeItem;
import com.diploma.freezer.recipes.RecipesItemAdapter;

import java.util.ArrayList;

public class VideoFragment extends Fragment {
    GridView gridView;
    public static SearchView adminSearchView;
    public static VideoItemAdapter videosItemAdapter;
    public static ArrayList<String> missing1Color;
    public static ArrayList<String> missing2Color;
    private ArrayList<VideoItem> searchFilteredList;
    ArrayList<VideoItem> filtered;

    TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_recipes, container, false);

        textView = inflatedView.findViewById(R.id.back_text_recipes);

        gridView = inflatedView.findViewById(R.id.gridView);

        filtered = new ArrayList<>();


        //recipesItemAdapter = new RecipesItemAdapter(inflatedView.getContext(), currentRecipes.getRecipeItems());
        //filtered = FilteredRecipes();
        ArrayList<String> ing = new ArrayList<>();
        ing.add("Яички");
        filtered.add(new VideoItem("123", "Самая вкусная брускетта","Готовим...", ing));

        videosItemAdapter = new VideoItemAdapter(inflatedView.getContext(), currentVideos.getVideoItems());
        gridView.setAdapter(videosItemAdapter);

        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            ArrayList<VideoItem> list = currentVideos.getVideoItems();
            /*
            if (searchFilteredList == null){
                list = filtered;
            }else{
                list = searchFilteredList;
            }

             */

            Intent intent = new Intent(inflatedView.getContext(), VideoActivity.class);
            Log.i("GridViewInfo: ", "position " + i + " "+ list.get(i));

            intent.putExtra("caption", list.get(i).getCaption());
            intent.putExtra("description", list.get(i).getDescription());
            intent.putExtra("ingredients",list.get(i).getStringIngredients());
            intent.putExtra("calories",list.get(i).getCalories());

            startActivity(intent);
        });


        return inflatedView;
    }
}
