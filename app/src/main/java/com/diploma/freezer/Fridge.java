package com.diploma.freezer;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Fridge { //Холодильник -_-
    private final ArrayList<FreezerItem> freezerItems = new ArrayList<>();
    private final FirebaseFirestore db;


    public Fridge() {
        db = FirebaseFirestore.getInstance();
        syncData();
    }

    public ArrayList<FreezerItem> getFreezerItems() {
        return freezerItems;
    }

    private void syncData(){

        db.collection("ingredients").orderBy("foodName", Query.Direction.ASCENDING)
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
                                freezerItems.add(dc.getDocument().toObject(FreezerItem.class));
                                Log.d(TAG, "Food data: " + dc.getDocument());
                            }
                        }


                    }
                });
    }
}
