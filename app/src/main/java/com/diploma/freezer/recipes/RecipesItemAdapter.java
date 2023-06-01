package com.diploma.freezer.recipes;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.diploma.freezer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipesItemAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<RecipeItem> recipeItems;
    LayoutInflater layoutInflater;
    CardView cardView;

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

    public void filterList(ArrayList<RecipeItem> filteredList){
        recipeItems = filteredList;
        notifyDataSetChanged();
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
        cardView = view.findViewById(R.id.colorful_cardView);

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(com.google.android.material.R.attr.colorPrimary, typedValue, true);
        @ColorInt int colorPrimary = typedValue.data;
        context.getTheme().resolveAttribute(com.google.android.material.R.attr.colorSecondary, typedValue, true);
        @ColorInt int colorSecondary = typedValue.data;
        context.getTheme().resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typedValue, true);
        @ColorInt int colorOnPrimary = typedValue.data;

        if (RecipesFragment.missing1Color.contains(recipeItems.get(i).getCaption()))
            cardView.setCardBackgroundColor(colorPrimary);
        else if (RecipesFragment.missing2Color.contains(recipeItems.get(i).getCaption()))
            cardView.setCardBackgroundColor(colorSecondary);
        else
            cardView.setCardBackgroundColor(colorOnPrimary);

        try{
            Picasso.get().load(recipeItems.get(i).getImage()).into(gridImage);
        }
        catch (Exception e){
            gridImage.setImageResource(R.drawable.ic_null_fastfood_24);
        }
        gridCaption.setText(recipeItems.get(i).getCaption());

        return view;
    }
}
