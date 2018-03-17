package com.example.ahmed.tamrah;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Warsh on 3/7/2018.
 */

public class Offer {
    private String Title, mDesc, Type, mCity, Seller;
    private String Price, Rate;

    public Offer() {}

    public Offer(String title, String type, String city, String price, String rate) {
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
//        Log.i("", mType);
        return Title;

    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        this.mCity = city;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        this.Rate = rate;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String Desc) {
        this.mDesc = Desc;
    }

    public String getSellerUID() {
        return Seller;
    }

    public void setSellerUID(String SellerUID) {
        this.Seller = SellerUID;
    }

}