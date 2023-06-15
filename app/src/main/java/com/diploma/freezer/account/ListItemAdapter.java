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
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<ListItem> listItems;

    public ListItemAdapter(Context context, ArrayList<ListItem> listItems, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.listItems = listItems;
        this.recyclerViewInterface = recyclerViewInterface;
    }



    @NonNull
    @Override
    public ListItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_account_list, parent,false);

        return new ListItemAdapter.MyViewHolder(v,recyclerViewInterface);
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

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            listItemFirst = itemView.findViewById(R.id.listItemFirst);
            listItemSecond = itemView.findViewById(R.id.listItemSecond);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos, listItemFirst.getText().toString());
                        }
                    }
                }
            });
        }
    }
}
