package com.diploma.freezer;

import static android.content.ContentValues.TAG;

import static com.diploma.freezer.MainActivity.currentFirebaseUser;
import static com.diploma.freezer.recipes.RecipesFragment.adminSearchView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.diploma.freezer.fridge.FreezerFragment;
import com.diploma.freezer.fridge.FreezerItem;
import com.diploma.freezer.fridge.FreezerItemsUserAdapter;
import com.diploma.freezer.recipes.RecipesFragment;
import com.diploma.freezer.recipes.RecipesItemAdapter;
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
    private ArrayList<FreezerItem> userFridge;//Продукты в холодильнике у пользователя
    User(){
        this.mAuth = FirebaseAuth.getInstance();
        this.firebaseUser = mAuth.getCurrentUser();
        this.email = firebaseUser.getEmail();
        Log.d("Email:", email);
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        userFridge = new ArrayList<>();
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
                        Log.d(TAG, "Name data:" + name);
                        Log.d(TAG, "Admin data: " + admin);
                        Log.d(TAG, "foodList data: " + foodList.toString());
                        Log.d(TAG, "userRate data: " + userRating.toString());
                        if (currentFirebaseUser.isAdmin() && adminSearchView != null) adminSearchView.setVisibility(View.VISIBLE);

                        if(foodList!=null)
                            for(String s : foodList)
                                userFridge.add(newFridgeItem(s));
                        MainActivity.progressBar.setVisibility(View.GONE);
                        if (RecipesFragment.recipesItemAdapter != null)
                            RecipesFragment.recipesItemAdapter.notifyDataSetChanged();

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private FreezerItem newFridgeItem(String foodName) {//Конструктор для того чтобы восстанавливать список из базы

        FreezerItem res = new FreezerItem();
        firebaseFirestore.collection("ingredients").document(foodName)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            res.setFoodName(foodName);
                            String image = document.getData().get("image").toString();
                            res.setImage(image);
                            Log.d(TAG, "Document: " + image);
                            if (FreezerFragment.freezerItemsAdapter != null)
                                FreezerFragment.freezerItemsAdapter.notifyDataSetChanged();

                        } else {
                            res.setFoodName(foodName);
                            res.setImage("");
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                });
        return res;
    }

    public ArrayList<FreezerItem> getUserFridge() {
        return userFridge;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin(){
        Log.d(TAG, "Admin data: " + admin);
        if (admin == null) return false;
        else return admin.equals("1");
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

    public void clearProductListFirebase(){

        userFridge.clear();

        Map<String, Object> info = new HashMap<>();
        info.put("foodList", new ArrayList<>());

        firebaseFirestore.collection("users").document(email)
                .set(info, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Products successfully cleared:"))
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
