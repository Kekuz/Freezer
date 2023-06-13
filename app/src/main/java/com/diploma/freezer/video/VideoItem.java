package com.diploma.freezer.video;

import androidx.annotation.NonNull;


import java.util.List;

public class VideoItem {
    private String caption;
    private String description;
    private String calories;
    private List<String> ingredients;

    public VideoItem(){}

    public VideoItem(String calories, String caption, String description, List<String> ingredients){
        this.calories = calories;
        this.caption = caption;
        this.description = description;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public String toString() {
        return this.caption + " " + ingredients;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public String getStringIngredients(){
        return "·" + String.join("\n·", ingredients);
    }
}
