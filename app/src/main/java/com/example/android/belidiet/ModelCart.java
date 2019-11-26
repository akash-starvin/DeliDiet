package com.example.android.belidiet;


// this class is used by list view adapter
public class ModelCart {
    private String packName, packQty, packPrice, packId;

    public ModelCart(String packName, String packQty, String packPrice, String packId) {
        this.packName = packName;
        this.packQty = packQty;
        this.packPrice = packPrice;
        this.packId = packId;
    }

    public String getPackId() {
        return packId;
    }

    public void setPackId(String packId) {
        this.packId = packId;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getPackQty() {
        return packQty;
    }

    public void setPackQty(String packQty) {
        this.packQty = packQty;
    }

    public String getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(String packPrice) {
        this.packPrice = packPrice;
    }
}
