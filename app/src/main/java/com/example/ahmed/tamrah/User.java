package com.example.ahmed.tamrah;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by khalidalnamlah on 3/9/18.
 */

public class User {
    private String name;
    private String region;
    private String description;
    private String phoneNum;
    private String address;
    private Image profileImage;
    private Context context;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener firebaseAuthListner;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private Map<String, Object> userInfo;
    private String myUID;


    public User() {
    }



    public void logout() {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Logout");
        alertDialog.setMessage("You are signed in as: " + user.getEmail());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Logout",
                new DialogInterface.OnClickListener() {
                    //firebase logout
                    public void onClick(DialogInterface dialog, int which) {
                        //firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.signOut();
                        dialog.dismiss();
                        if (user == null) {
                            Log.i("1", "right");
                        }
                        //startActivity(new Intent(this,MainActivity.class));
                        Log.i("1", "logged out");
                        Toast.makeText(context.getApplicationContext(), "LoggedOut", Toast.LENGTH_LONG).show();
                        //mDrawerLayout.closeDrawers();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //FirebaseAuth.getInstance().signOut();
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    public void getUserProfile(final String UID) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        databaseReference.addValueEventListener(new ValueEventListener() {

            //This will be called when there is a change in the User child
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot = dataSnapshot.child(UID);
                userInfo = ((Map<String, Object>) dataSnapshot.getValue());
                //Log.i("1",);
                name = userInfo.get("name").toString();
                region = userInfo.get("region").toString();
                description = userInfo.get("description").toString();
                phoneNum = userInfo.get("phoneNum").toString();
                //profileImage = ((Image) userInfo.get("profileImage"));

                Log.i("f", name);
                Log.i("f", region);
                Log.i("f", description);
                Log.i("f", phoneNum);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public void updateProfile() {

        String userId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("User").child(userId);
        Map newPost = new HashMap();
        newPost.put("name", "khalid");
        newPost.put("phoneNum", "966565684101");
        newPost.put("region", "Riyadh");
        newPost.put("description", "Fuck you MotherFucker...");
        newPost.put("profileImage", "http://justfood.nawa3em.com/subimg/106376015231.jpg");

        current_user_db.setValue(newPost);

    }


    public void resetPassword(String emailAddress) {
        firebaseAuth = FirebaseAuth.getInstance();
        //String emailAddress = "user@example.com";

        firebaseAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(context.getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(context.getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    public void setPassword(String newPassword) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        //String newPassword = "SOME-SECURE-PASSWORD";

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                        }
                    }
                });
    }

    public String getName() {
        return name;
    }

    public void setContext(Context context) {
        this.context = context;
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
}