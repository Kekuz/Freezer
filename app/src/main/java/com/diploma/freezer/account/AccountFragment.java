package com.diploma.freezer.account;

import static com.diploma.freezer.MainActivity.currentFirebaseUser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.diploma.freezer.fridge.FreezerItemsAdapter;
import com.diploma.freezer.logreg.Login;
import com.diploma.freezer.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class AccountFragment extends Fragment {
    Button button;
    TextView nameTextView;
    RecyclerView recyclerView;
    AccountItemsAdapter accountItemsAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_account, container, false);
        button = inflatedView.findViewById(R.id.logout_button);
        nameTextView = inflatedView.findViewById(R.id.details);
        nameTextView.setText(currentFirebaseUser.getName());


        recyclerView = inflatedView.findViewById(R.id.profile_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflatedView.getContext()));

        ArrayList<AccountItem> accountItems = new ArrayList<>();

        accountItems.add(new AccountItem("Профиль"));
        accountItems.add(new AccountItem("Избранное"));
        accountItems.add(new AccountItem("Оценки"));

        accountItemsAdapter = new AccountItemsAdapter(inflatedView.getContext(), accountItems);
        recyclerView.setAdapter(accountItemsAdapter);

        //Чтобы обрабатывать нажатия нужно сделать GridView


        if (currentFirebaseUser.getFirebaseUser() == null){
            Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
            startActivity(intent);
            getActivity().finish();
        }

        //Это надо будет убрать, когда будут работать кнопки меню
        nameTextView.setOnClickListener(v -> {
            button.setVisibility(View.VISIBLE);
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return inflatedView;
    }
}