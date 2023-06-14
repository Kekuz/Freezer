package com.diploma.freezer.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diploma.freezer.R;

import java.util.ArrayList;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.MyViewHolder>{
    Context context;
    ArrayList<ListItem> listItems;

    public ListItemAdapter(Context context, ArrayList<ListItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }



    @NonNull
    @Override
    public ListItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_account_list, parent,false);

        return new ListItemAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemAdapter.MyViewHolder holder, int position) {

        ListItem listItem = listItems.get(position);

        holder.listItemFirst.setText(listItem.getListItemFirst());
        holder.listItemSecond.setText(listItem.getListItemSecond());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView listItemFirst, listItemSecond;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            listItemFirst = itemView.findViewById(R.id.listItemFirst);
            listItemSecond = itemView.findViewById(R.id.listItemSecond);
        }
    }
}
