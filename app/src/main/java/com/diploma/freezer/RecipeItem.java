package com.diploma.freezer;

import androidx.annotation.NonNull;

public class RecipeItem {
    private String caption;
    private String image;

    public RecipeItem(){}//Тоже удалять запрещено!!!

    public RecipeItem(String caption, String image) {
        this.caption = caption;
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
