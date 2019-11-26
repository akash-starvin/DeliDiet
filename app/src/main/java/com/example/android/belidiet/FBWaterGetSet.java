package com.example.android.belidiet;

public class FBWaterGetSet {
    String date, num , email;

    public FBWaterGetSet(String date, String num, String email) {
        this.date = date;
        this.num = num;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

}
