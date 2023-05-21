package com.diploma.freezer.recipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.diploma.freezer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipesItemAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<RecipeItem> recipeItems;
    LayoutInflater layoutInflater;

    public RecipesItemAdapter(Context context, ArrayList<RecipeItem> recipeItems) {
        this.context = context;
        this.recipeItems = recipeItems;
    }

    @Override
    public int getCount() {
        return recipeItems.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (layoutInflater == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null){
            view = layoutInflater.inflate(R.layout.grid_item_recipes, null);
        }
        ImageView gridImage = view.findViewById(R.id.gridImage);
        TextView gridCaption = view.findViewById(R.id.gridCaption);

        Picasso.get().load(recipeItems.get(i).getImage()).into(gridImage);
        gridCaption.setText(recipeItems.get(i).getCaption());

        return view;
    }
}
