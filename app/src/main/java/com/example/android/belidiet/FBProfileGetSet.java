package com.example.android.belidiet;

public class FBProfileGetSet {
    String gender, age, weight, height,medCon, city,email;

    public FBProfileGetSet(String gender, String age, String weight, String height, String medCon, String city, String email) {
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.medCon = medCon;
        this.city = city;
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMedCon() {
        return medCon;
    }

    public void setMedCon(String medCon) {
        this.medCon = medCon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
