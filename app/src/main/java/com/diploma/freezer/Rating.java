package com.diploma.freezer;

import static android.content.ContentValues.TAG;

import static com.diploma.freezer.MainActivity.currentFirebaseUser;
import static com.diploma.freezer.MainActivity.currentRating;

import android.util.Log;


import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Rating {
    private ArrayList<RatingItem> ratingItems = new ArrayList<>();

    public RatingItem findByName(String name){
        for (RatingItem r: ratingItems) {
            if(r.getName().equals(name)) return r;
        }
        return null;
    }

    public ArrayList<RatingItem> getRatingItems() {
        return ratingItems;
    }

    Rating(){

        FirebaseFirestore.getInstance().collection("rating").orderBy("count", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {

            if (error != null){
                Log.e("Firebase error", error.getMessage());
                return;
            }

            assert value != null;
            for (DocumentChange dc : value.getDocumentChanges()){
                if(dc.getType() == DocumentChange.Type.ADDED){
                    ratingItems.add(dc.getDocument().toObject(RatingItem.class));
                    Log.d(TAG, "Rating data: " + dc.getDocument());
                }
            }


        });
    }

    public void sendRating(String name, float v){

        RatingItem ratingItem = findByName(name);//Может быть равен null

        Map<String, String> info = new HashMap<>();
        if (ratingItem == null){
            info.put("count", "1");
            info.put("name", name);
            info.put("sum", String.valueOf(v));

            FirebaseFirestore.getInstance().collection("rating").document(name)
                    .set(info)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Rating successfully saved:" + info))
                    .addOnFailureListener(e -> Log.w(TAG, "Error saving rating", e));
        }else{

            if(!currentFirebaseUser.getUserRating().containsKey(name)){
                info.put("count", String.valueOf(Float.parseFloat(ratingItem.getCount()) + 1));
                info.put("sum", String.valueOf(Float.parseFloat(ratingItem.getSum()) + v));
            }
            else{
                info.put("sum", String.valueOf(Float.parseFloat(ratingItem.getSum()) - Float.parseFloat(currentFirebaseUser.getUserRating().get(name)) + v));
            }

            FirebaseFirestore.getInstance().collection("rating").document(name)
                    .set(info,SetOptions.merge())
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Rating successfully saved:" + info))
                    .addOnFailureListener(e -> Log.w(TAG, "Error saving rating", e));
        }

    }

}
