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

public class AccountItemsAdapter extends RecyclerView.Adapter<AccountItemsAdapter.MyViewHolder>{
    Context context;
    ArrayList<AccountItem> accountItemArrayList;

    public AccountItemsAdapter(Context context, ArrayList<AccountItem> accountItemArrayList) {
        this.context = context;
        this.accountItemArrayList = accountItemArrayList;
    }

    @NonNull
    @Override
    public AccountItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_profile,parent,false);

        return new AccountItemsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountItemsAdapter.MyViewHolder holder, int position) {

        AccountItem accountItem = accountItemArrayList.get(position);

        holder.profileItem.setText(accountItem.getName());


    }

    @Override
    public int getItemCount() {
        return accountItemArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView profileItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profileItem = itemView.findViewById(R.id.profile_item_text);
        }
    }
}
