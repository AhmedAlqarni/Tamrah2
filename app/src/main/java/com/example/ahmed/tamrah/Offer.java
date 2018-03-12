package com.example.ahmed.tamrah;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Warsh on 3/7/2018.
 */

public class Offer {
    private String mTitle, mDesc, mType, mCity, mSellerUID;
    private double mPrice, mRate;

    public Offer() {}

    public Offer(String title, String type, String city, double price, double rate) {
        setTitle(title);
        setType(type);
        setCity(city);
        setPrice(price);
        setRate(rate);
    }

    public void publish() {
        DatabaseReference FBofferNode = FirebaseDatabase.getInstance().getReference().child("Offer");
        Map offerPost = new HashMap();
        offerPost.put("Seller", "N\\A"); //STUB
        offerPost.put("Title", getTitle());
        offerPost.put("Description", getDesc());
        offerPost.put("Price", new DecimalFormat(".##").format(getPrice()));
        offerPost.put("Type", getType());
        offerPost.put("Rate", getRate());
        //FBofferNode.setValue(offerPost);
        FBofferNode.push().setValue(offerPost);
    }

    //Setters & Getters:
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

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String Desc) {
        this.mDesc = Desc;
    }

    public String getSellerUID() {
        return mSellerUID;
    }

    public void setSellerUID(String SellerUID) {
        this.mSellerUID = SellerUID;
    }

}