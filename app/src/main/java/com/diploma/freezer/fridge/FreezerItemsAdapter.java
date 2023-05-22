package com.diploma.freezer.fridge;

import static com.diploma.freezer.MainActivity.userFridge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diploma.freezer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FreezerItemsAdapter extends RecyclerView.Adapter<FreezerItemsAdapter.MyViewHolder> {

    Context context;
    ArrayList<FreezerItem> freezerItemArrayList;

    public FreezerItemsAdapter(Context context, ArrayList<FreezerItem> freezerItemArrayList) {
        this.context = context;
        this.freezerItemArrayList = freezerItemArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_freezer,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FreezerItem freezerItem = freezerItemArrayList.get(position);

        holder.foodName.setText(freezerItem.getFoodName());
        Picasso.get().load(freezerItem.getImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userFridge.add(freezerItem);
                Log.i("Item click:", "num"+ freezerItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return freezerItemArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView foodName;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodname);
            image = itemView.findViewById(R.id.image);
        }
    }
}
