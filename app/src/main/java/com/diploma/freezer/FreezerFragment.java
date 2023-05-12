package com.diploma.freezer;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;

public class FreezerFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<FreezerItem> freezerItemArrayList;
    FreezerItemsAdapter freezerItemsAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;


    @SuppressLint({"MissingInflatedId", "CheckResult"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_freezer, container, false);

        progressDialog = new ProgressDialog(inflater.getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fatching data...");
        progressDialog.show();


        recyclerView = inflatedView.findViewById(R.id.recyclerViewFreezer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflatedView.getContext()));

        db = FirebaseFirestore.getInstance();
        freezerItemArrayList = new ArrayList<FreezerItem>();

        //imageView = inflatedView.findViewById(R.id.imageviev);
        //freezerItemArrayList.add(new FreezerItem("Milk","https://firebasestorage.googleapis.com/v0/b/freezer-392ad.appspot.com/o/freezerImages%2Fmilk.png?alt=media&token=2ae0d0ba-caa7-44cc-93e0-db18c33264bd"));


        freezerItemsAdapter = new FreezerItemsAdapter(inflatedView.getContext(), freezerItemArrayList);
        recyclerView.setAdapter(freezerItemsAdapter);

        EventChangeListener();

        return inflatedView;
    }

    private void EventChangeListener(){

        db.collection("recipes").orderBy("foodName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                freezerItemArrayList.add(dc.getDocument().toObject(FreezerItem.class));
                                Log.d(TAG, "Food data: " + dc.getDocument());
                            }
                            freezerItemsAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }


                    }
                });
    }
}