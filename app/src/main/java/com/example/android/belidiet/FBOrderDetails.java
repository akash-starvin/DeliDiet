package com.example.android.belidiet;

public class FBOrderDetails {

    String id, phone, price, add;

    public FBOrderDetails(String id, String phone, String price, String add) {
        this.id = id;
        this.phone = phone;
        this.price = price;
        this.add = add;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }
}
