package com.diploma.freezer.video;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class Videos {

    private final ArrayList<VideoItem> videoItems = new ArrayList<>();

    public Videos(){
        syncData();
    }

    public ArrayList<VideoItem> getVideoItems() {
        return videoItems;
    }

    private void syncData() {
        FirebaseFirestore.getInstance().collection("video").orderBy("caption", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {

                    if (error != null){
                        Log.e("Firebase error", error.getMessage());
                        return;
                    }

                    assert value != null;
                    for (DocumentChange dc : value.getDocumentChanges()){
                        if(dc.getType() == DocumentChange.Type.ADDED){
                            videoItems.add(dc.getDocument().toObject(VideoItem.class));
                            Log.d(TAG, "Video data: " + videoItems);
                        }
                    }


                });
    }
}
