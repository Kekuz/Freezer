package com.diploma.freezer;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.diploma.freezer.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static User currentFirebaseUser;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new RecipesFragment());

        currentFirebaseUser = new User();


        binding.bottomNavView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.navigation_freezer){
                replaceFragment(new FreezerFragment());
            }else if (item.getItemId() == R.id.navigation_recipes){
                replaceFragment(new RecipesFragment());
            }else if (item.getItemId() == R.id.navigation_account) {
                replaceFragment(new AccountFragment());
            }

            return true;
        });

    }
    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }
}