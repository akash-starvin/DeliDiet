package com.example.android.belidiet;

public class FBCartGetSet {
    String  cName, cQty, cPhone, cPrice;

    public FBCartGetSet(String cName, String cQty, String cPhone, String cPrice) {
        this.cName = cName;
        this.cQty = cQty;
        this.cPhone = cPhone;
        this.cPrice = cPrice;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcQty() {
        return cQty;
    }

    public void setcQty(String cQty) {
        this.cQty = cQty;
    }

    public String getcPhone() {
        return cPhone;
    }

    public void setcPhone(String cPhone) {
        this.cPhone = cPhone;
    }

    public String getcPrice() {
        return cPrice;
    }

    public void setcPrice(String cPrice) {
        this.cPrice = cPrice;
    }
}
