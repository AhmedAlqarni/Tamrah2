package com.example.ahmed.tamrah;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Warsh on 3/7/2018.
 */

public class Offer implements Serializable{
    private String Title, Description, Type, mCity, Seller;
    private String Price, Rate, OID;


    public Offer() {}

//    public Offer(String title, String type, String city, String price, String rate) {
//        setTitle(title);
//        setType(type);
//        setCity(city);
//        setPrice(price);
//        setRate(rate);
//    }
    public Offer(String title, String type, String city, String price, String rate,String mDesc) {
        setTitle(title);
        setType(type);
        setCity(city);
        setPrice(price);
        setRate(rate);
        setDesc(mDesc);
    }



    //Setters & Getters:
    public String getTitle() {
//        Log.i("", mType);
        return Title;

    }

    public String getOID(){
        return OID;
    }
    public void setOID(String OID){
        this.OID = OID;
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
        return Description;
    }

    public void setDesc(String Desc) {
        this.Description = Desc;
    }

    public String getSellerUID() {
        return Seller;
    }

    public void setSellerUID(String SellerUID) {
        this.Seller = SellerUID;
    }

//    public void setValues(Map<String,Object> offerValues) {
//        Title = offerValues.get("Title").toString();
//        //mDesc = offerValues.get("region").toString();
//        Type = offerValues.get("Type").toString();
//        //phoneNum = offerValues.get("phoneNum").toString();
//        Seller = offerValues.get("Seller").toString();
//        Rate = offerValues.get("Rate").toString();
//        //isSeller = Boolean.parseBoolean(profileValues.get("isSeller").toString());
//        //profilePic = offerValues.get("profileImage").toString();
//        Price = offerValues.get("Price").toString();
//
//    }

}