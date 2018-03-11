package com.example.ahmed.tamrah;

/**
 * Created by Warsh on 3/7/2018.
 */

public class Offer {
        private String mTitle, mType, mCity;
        private double mPrice, mRate;

    public Offer() {}

    public Offer(String mTitle, String mType, String mCity, double price, double rate) {
        this.mTitle = mTitle;
        this.mType = mType;
        this.mCity = mCity;
        this.mPrice = price;
        this.mRate = rate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        this.mCity = city;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        this.mPrice = price;
    }

    public double getRate() {
        return mRate;
    }

    public void setRate(double rate) {
        this.mRate = rate;
    }
}
