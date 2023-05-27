package com.diploma.freezer;

import static android.content.ContentValues.TAG;

import static com.diploma.freezer.MainActivity.currentFirebaseUser;
import static com.diploma.freezer.MainActivity.userFridge;
import static com.diploma.freezer.recipes.RecipesFragment.adminSearchView;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.diploma.freezer.fridge.FreezerItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private String email, name, admin;
    private ArrayList<String> foodList;
    private Map<String,String> userRating;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference usersInfoReference;
    User(){
        this.mAuth = FirebaseAuth.getInstance();
        this.firebaseUser = mAuth.getCurrentUser();
        this.email = firebaseUser.getEmail();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        usersInfoReference = firebaseFirestore.collection("users").document(email);
        usersInfoReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        name = document.getData().get("name").toString();
                        admin = document.getData().get("admin").toString();
                        foodList = (ArrayList<String>) document.getData().get("foodList");
                        userRating = (Map<String, String>) document.getData().get("userRating");
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Log.d(TAG, "Admin data: " + admin);
                        Log.d(TAG, "foodList data: " + foodList.toString());
                        Log.d(TAG, "userRate data: " + userRating.toString());
                        if (currentFirebaseUser.isAdmin()) adminSearchView.setVisibility(View.VISIBLE);
                        if(foodList!=null)
                            for(String s : foodList)
                                userFridge.add(new FreezerItem(s));
                        //recipesItemAdapter.notifyDataSetChanged();

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin(){
        Log.d(TAG, "Admin data: " + admin);
        if (admin == null) return false;
        else return admin.equals("1");
    }

    public ArrayList<FreezerItem> getFoodList() {
        ArrayList<FreezerItem> res = new ArrayList<>();
        for (String x : foodList) {
            res.add(new FreezerItem(x,""));
        }
        return res;
    }
    public void saveProductListFirebase(){

        ArrayList<String> userStringFridge = new ArrayList<>();
        for (FreezerItem x : userFridge) {
            userStringFridge.add(x.getFoodName());
        }

        Map<String, Object> info = new HashMap<>();
        info.put("foodList", userStringFridge);

        firebaseFirestore.collection("users").document(email)
                .set(info, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Products successfully saved:" + userStringFridge.toString()))
                .addOnFailureListener(e -> Log.w(TAG, "Error saving products", e));
    }

    public void rating(String name, float r){

        Map<String, Object> info = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        map.put(name, String.valueOf(r));
        info.put("userRating", map);

        firebaseFirestore.collection("users").document(email)
                .set(info, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Rating successfully saved:" + map.toString()))
                .addOnFailureListener(e -> Log.w(TAG, "Error saving rating", e));
    }

    public float getRating(String name){

        float rating;
        try {
           rating = Float.parseFloat(userRating.get(name));
        }catch (Exception e){
            rating = 0.0f;
        }
        return rating;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public Map<String, String> getUserRating(){
        return userRating;
    }

    public FirebaseFirestore getFirebaseFirestore() {
        return firebaseFirestore;
    }
}
