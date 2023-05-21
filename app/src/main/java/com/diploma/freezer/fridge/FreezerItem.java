package com.diploma.freezer.fridge;

public class FreezerItem {
     private String foodName;
     private String image;

    public FreezerItem(){}//Это убирать запрещено!!!

    public FreezerItem(String foodName, String image) {
        this.foodName = foodName;
        this.image = image;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
    public String getImage() {return image;}

    public void setImage(String image) {this.image = image;}
}
