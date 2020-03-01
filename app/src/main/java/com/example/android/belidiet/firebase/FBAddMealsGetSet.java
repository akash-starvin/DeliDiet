package com.example.android.belidiet.firebase;

/*
Instead of passing multiple values to database, we'll add those data into an object and sent it to database
 */


public class FBAddMealsGetSet {

    String mealName, mealDesc, mealPrice, mealImg, mealPack;

    public FBAddMealsGetSet(String mealName, String mealDesc, String mealPrice, String mealImg, String mealPack) {
        this.mealName = mealName;
        this.mealDesc = mealDesc;
        this.mealPrice = mealPrice;
        this.mealImg = mealImg;
        this.mealPack = mealPack;
    }

    public String getMealPack() {
        return mealPack;
    }

    public void setMealPack(String mealPack) {
        this.mealPack = mealPack;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealDesc() {
        return mealDesc;
    }

    public void setMealDesc(String mealDesc) {
        this.mealDesc = mealDesc;
    }

    public String getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(String mealPrice) {
        this.mealPrice = mealPrice;
    }

    public String getMealImg() {
        return mealImg;
    }

    public void setMealImg(String mealImg) {
        this.mealImg = mealImg;
    }
}
