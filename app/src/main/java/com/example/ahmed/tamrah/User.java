package com.example.ahmed.tamrah;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by khalidalnamlah on 3/9/18.
 */

public class User implements Serializable{
    private String UID;
    private String name;
    private String region;
    private String description;
    private String phoneNum;
    private String profilePic;
    private String email;
    private String address;

    private double rate;
    private boolean isSeller;
    private ArrayList<Offer> cart;
    private ArrayList<Offer> order;

    private boolean isLoggedIn;



    public User() {
        UID = "";
        name = "";
        region = "";
        description = "";
        phoneNum = "";
        profilePic = "";
        address = "";
        rate = 0;
        isSeller = false;
        isLoggedIn = false;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getEmail() {
        Log.i("",this.email);
        return email;
    }

    public String getAddress(){ return address; }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    public void setRegion(String region){
        this.region = region;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setBio(String bio){
        this.description = bio;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void loggedIn(boolean isLoggedIn){this.isLoggedIn = isLoggedIn;}

    public boolean isLoggedIn(){ return isLoggedIn;}

    public void setProfileValues(Map<String,Object> profileValues) {
        name = profileValues.get("name").toString();
        region = profileValues.get("region").toString();
        description = profileValues.get("description").toString();
        phoneNum = profileValues.get("phoneNum").toString();
        address = profileValues.get("address").toString();
        rate = Double.parseDouble(profileValues.get("rate").toString());
        isSeller = Boolean.parseBoolean(profileValues.get("isSeller").toString());
        profilePic = profileValues.get("profileImage").toString();
        email = profileValues.get("email").toString();

    }

}