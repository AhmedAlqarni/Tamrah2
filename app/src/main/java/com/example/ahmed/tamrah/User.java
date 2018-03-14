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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

/**
 * Created by khalidalnamlah on 3/9/18.
 */

public class User extends AsyncTask<String, Void, Drawable> {
    private String name;
    private String region;
    private String description;
    private String phoneNum;
    private Drawable profilePicture;
    private String address;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private Map<String, Object> userInfo;
    private String myUID;


    public User() {
        /*initiated by anyone who want's to
        1- Sign up
        2- Sign in
        */
        //getUserProfile("rzjZ4oY3gMOklf2uBfIfJiEIQSn2");
    }


    public User(Map<String, Object> userInfo) {
        name = userInfo.get("name").toString();
        region = userInfo.get("region").toString();
        description = userInfo.get("description").toString();
        phoneNum = userInfo.get("phoneNum").toString();
        profilePicture = doInBackground((String) userInfo.get("profileImage"));

    }

    public void register(String email, String password, String name, final Context context) {
        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(context.getApplicationContext(), "Please enter your email ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(context.getApplicationContext(), "Please enter your email ", Toast.LENGTH_SHORT).show();
        }

        firebaseAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Registering ...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) { //CASTING
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(context.getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                            // Later
                            //loginAfterRegister(email,password);
                        } else {
                            progressDialog.dismiss();
                            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                            alertDialog.setTitle("Registering Failed");
                            alertDialog.setMessage("Invalid email or password");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }
                });
        if (firebaseAuth.getCurrentUser() != null) {
            myUID = firebaseAuth.getCurrentUser().getUid();
            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("User").child(myUID);
            Map newPost = new HashMap();
            newPost.put("name", name);
            current_user_db.setValue(newPost);
        }
    }

    public void login(String email, String password, final Context context) {

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(context.getApplicationContext(), "Please enter your email ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(context.getApplicationContext(), "Please enter your email ", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Logging in ...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) { // if logging was successfull (from firebase)
                            ((Activity) context).finish();
                            context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                            alertDialog.setTitle("Logging Failed");
                            alertDialog.setMessage("Invalid email or password");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                            Log.i("g", "faile"); // failure in logging in
                        }
                    }
                });

    }

    public void logout(final Context context) {
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

    public void getUserProfile(final String UID, final Context context) {
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Retrieving Profile . . .");
        progressDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            //This will locate the user based on the given User ID (UID)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot = dataSnapshot.child(UID);
                userInfo = ((Map<String, Object>) dataSnapshot.getValue());
                name = userInfo.get("name").toString();
                region = userInfo.get("region").toString();
                description = userInfo.get("description").toString();
                phoneNum = userInfo.get("phoneNum").toString();
                profilePicture = doInBackground((String) userInfo.get("profileImage"));
                progressDialog.dismiss();
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

    public void confirmEmail() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
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

    public String getRegion() {
        return region;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public Drawable getProfilePicture() {
        return profilePicture;
    }

    @Override
    // This method is to get the image after its URL
    // has been given from Firebase.
    protected Drawable doInBackground(String... urls) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            return Drawable.createFromStream((InputStream) new URL(urls[0]).getContent(), "Firebase");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}