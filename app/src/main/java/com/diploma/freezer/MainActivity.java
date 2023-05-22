package com.diploma.freezer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.diploma.freezer.account.AccountFragment;
import com.diploma.freezer.databinding.ActivityMainBinding;
import com.diploma.freezer.fridge.FreezerFragment;
import com.diploma.freezer.fridge.FreezerItem;
import com.diploma.freezer.fridge.Fridge;
import com.diploma.freezer.recipes.Recipes;
import com.diploma.freezer.recipes.RecipesFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static User currentFirebaseUser;//Создаем юзера
    public static Fridge currentFridge;//Создаем базу продуктов
    public static ArrayList<FreezerItem> userFridge;//Продукты в холодильнике у пользователя
    public static Recipes currentRecipes;//Создаем базу рецептов
    ProgressBar progressBar;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressBar = findViewById(R.id.progressBarDB);
        replaceFragment(new RecipesFragment());

        currentFirebaseUser = new User();
        currentFridge = new Fridge();
        userFridge = new ArrayList<>();;
        currentRecipes = new Recipes(progressBar);

        binding.bottomNavView.getMenu().findItem(R.id.navigation_recipes).setChecked(true);// выбираем рецепты по умолчанию
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