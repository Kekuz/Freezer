package com.diploma.freezer.fridge;

import static com.diploma.freezer.MainActivity.userFridge;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.diploma.freezer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FreezerItemsUserAdapter extends FreezerItemsAdapter{

    public FreezerItemsUserAdapter(Context context, ArrayList<FreezerItem> freezerItemArrayList) {
        super(context, freezerItemArrayList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FreezerItem freezerItem = freezerItemArrayList.get(position);

        holder.foodName.setText(freezerItem.getFoodName());
        try {
        Picasso.get().load(freezerItem.getImage()).into(holder.image);
        }catch (Exception e){
        holder.image.setImageResource(R.drawable.ic_null_fastfood_24);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast;
                if (!userFridge.contains(freezerItem)){
                    toast = Toast.makeText(holder.itemView.getContext(), freezerItem.getFoodName() + " " + context.getResources().getString(R.string.added_to_fridge), Toast.LENGTH_SHORT);
                    userFridge.add(freezerItem);
                }else{
                    toast = Toast.makeText(holder.itemView.getContext(), freezerItem.getFoodName() + " " + context.getResources().getString(R.string.already_exists), Toast.LENGTH_SHORT);
                }
                toast.show();

                Log.i("Item click:", "num"+ freezerItem);
            }
        });
    }
}
