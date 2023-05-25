package com.diploma.freezer.fridge;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FreezerItem {
     private String foodName;
     private String image;

    public FreezerItem(){}//Это убирать запрещено!!!

    public FreezerItem(String foodName, String image) {
        this.foodName = foodName;
        this.image = image;
    }
    public FreezerItem(String foodName) {//Конструктор для того чтобы восстанавливать список из базы
        this.foodName = foodName;
        //image = "";//Произошла заглушечка

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference usersInfoReference = db.collection("ingredients").document(foodName);
        usersInfoReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        image = document.getData().get("image").toString();
                        Log.d(TAG, "Document: " + image);
                    } else {
                        image = "";
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }


    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
    public String getImage() {return image;}

    public void setImage(String image) {this.image = image;}
}
