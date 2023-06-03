package com.diploma.freezer.recipes;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeItem {
    private String caption;
    private String image;
    private String description;
    private String calories;
    private List<String> ingredients;

    public RecipeItem(){}//Тоже удалять запрещено!!!

    public RecipeItem(String calories, String caption, String description, String image, List<String> ingredients) {
        this.calories = calories;
        this.caption = caption;
        this.description = description;
        this.image = image;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public String toString() {
        return this.caption + " " + ingredients;
    }

    public String getImage() {
        return image;
    }
    public String getDescription() {return description;}

    public void setImage(String image) {
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public List<String> getIngredients() {return ingredients;}

    public String getStringIngredients(){
        return "·" + String.join("\n·", ingredients);
    }

    public String getCalories() {
        return calories;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
