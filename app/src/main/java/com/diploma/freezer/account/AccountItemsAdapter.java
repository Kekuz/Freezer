package com.diploma.freezer.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.diploma.freezer.R;
import java.util.ArrayList;

public class AccountItemsAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<AccountItem> accountItemArrayList;
    LayoutInflater layoutInflater;

    public AccountItemsAdapter(Context context, ArrayList<AccountItem> accountItemArrayList) {
        this.context = context;
        this.accountItemArrayList = accountItemArrayList;
    }

    @Override
    public int getCount() {
        return accountItemArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return accountItemArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (layoutInflater == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null){
            view = layoutInflater.inflate(R.layout.item_profile, null);
        }
        TextView gridCaption = view.findViewById(R.id.profile_item_text);

        gridCaption.setText(accountItemArrayList.get(i).getName());

        return view;
    }
}
