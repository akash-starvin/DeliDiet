package com.example.android.belidiet.firebase;

public class FBAddPackGetSet {
    String packName, packDesc,packUrl, packMainDesc;


    public FBAddPackGetSet(String packName, String packDesc, String packUrl, String packMainDesc) {
        this.packName = packName;
        this.packDesc = packDesc;
        this.packUrl = packUrl;
        this.packMainDesc = packMainDesc;
    }

    public String getPackMainDesc() {
        return packMainDesc;
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

    public String getPackUrl() {
        return packUrl;
    }

    public void setPackUrl(String packUrl) {
        this.packUrl = packUrl;
    }
}
