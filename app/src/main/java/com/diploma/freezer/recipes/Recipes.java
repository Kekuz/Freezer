package com.diploma.freezer.recipes;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Recipes {
    private final ArrayList<RecipeItem> recipeItems = new ArrayList<>();
    private final FirebaseFirestore db;
    ProgressBar progressBar;


    public Recipes(ProgressBar progressBar) {
        this.progressBar = progressBar;
        db = FirebaseFirestore.getInstance();
        syncData();
    }

    public ArrayList<RecipeItem> getRecipeItems() {
        return recipeItems;
    }

    private void syncData(){

        db.collection("recipes").orderBy("caption", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){
                            Log.e("Firebase error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                recipeItems.add(dc.getDocument().toObject(RecipeItem.class));
                                Log.d(TAG, "Recipe data: " + recipeItems);
                                progressBar.setVisibility(View.GONE);
                            }
                        }


                    }
                });
    }
}
