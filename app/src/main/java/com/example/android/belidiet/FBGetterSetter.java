package com.example.android.belidiet;

public class FBGetterSetter {
    String accName, phone, email, url;

    public FBGetterSetter(String accName, String phone, String email, String url) {
        this.accName = accName;
        this.phone = phone;
        this.email = email;
        this.url = url;
    }

    public FBGetterSetter(String accName, String phone, String email) {
        this.accName = accName;
        this.phone = phone;
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}