package com.diploma.freezer.fridge;

import static com.diploma.freezer.MainActivity.currentFirebaseUser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.diploma.freezer.R;

public class FreezerFragment extends Fragment {

    RecyclerView recyclerView;
    public static FreezerItemsAdapter freezerItemsAdapter;
    Button addItemButton, clearButton;
    LinearLayout linearLayout;


    @SuppressLint({"NotifyDataSetChanged", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_freezer, container, false);
        addItemButton = inflatedView.findViewById(R.id.addItemButton);
        clearButton = inflatedView.findViewById(R.id.clearButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflatedView.getContext(), AllFreezerItemsActivity.class);
                startActivity(intent);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (freezerItemsAdapter != null && currentFirebaseUser.getUserFridge().size() != 0){
                    currentFirebaseUser.clearProductListFirebase();
                    freezerItemsAdapter.notifyDataSetChanged();
                }

            }
        });

        linearLayout = inflatedView.findViewById(R.id.user_fridge_linear_layout);

        recyclerView = inflatedView.findViewById(R.id.recyclerViewFreezer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflatedView.getContext()));

        freezerItemsAdapter = new FreezerItemsAdapter(inflatedView.getContext(), currentFirebaseUser.getUserFridge());//Тут передаем адаптер для юзера!
        recyclerView.setAdapter(freezerItemsAdapter);

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        return inflatedView;
    }
    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Toast.makeText(getContext(), currentFirebaseUser.getUserFridge().get(viewHolder.getAdapterPosition()).getFoodName() + " " + getContext().getResources().getString(R.string.product_deleted), Toast.LENGTH_SHORT).show();
            currentFirebaseUser.getUserFridge().remove(viewHolder.getAdapterPosition());

            currentFirebaseUser.saveProductListFirebase();

            freezerItemsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    };

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        freezerItemsAdapter.notifyDataSetChanged();
    }
}