package com.example.android.belidiet;

public class ModelMP {
    String packName, packDesc, packMainDesc, packImg;

    public ModelMP(String packName, String packDesc, String packMainDesc, String packImg) {
        this.packName = packName;
        this.packDesc = packDesc;
        this.packMainDesc = packMainDesc;
        this.packImg = packImg;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getPackDesc() {
        return packDesc;
    }

    public void setPackDesc(String packDesc) {
        this.packDesc = packDesc;
    }

    public String getPackMainDesc() {
        return packMainDesc;
    }

    public void setPackMainDesc(String packMainDesc) {
        this.packMainDesc = packMainDesc;
    }

    public String getPackImg() {
        return packImg;
    }

    public void setPackImg(String packImg) {
        this.packImg = packImg;
    }
}
