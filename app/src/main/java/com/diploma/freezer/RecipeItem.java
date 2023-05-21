package com.diploma.freezer;

import androidx.annotation.NonNull;

public class RecipeItem {
    private String caption;
    private String image;

    private String description;

    public RecipeItem(){}//Тоже удалять запрещено!!!

    public RecipeItem(String caption,String description, String image) {
        this.caption = caption;
        this.description = description;
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
        return this.caption + " " + this.image;
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

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
