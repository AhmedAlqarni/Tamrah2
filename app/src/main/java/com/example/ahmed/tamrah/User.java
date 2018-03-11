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

import java.util.concurrent.Executor;

import butterknife.internal.ListenerClass;

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
    private ProgressDialog progressDialog ;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    public User(Context context){
        this.context = context;
        progressDialog = new ProgressDialog(context);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        if(user != null){
            Log.i("1-", "user is null");
        }
    }

    public void register(String email, String password){
        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(context.getApplicationContext(), "Please enter your email ", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(context.getApplicationContext(), "Please enter your email ", Toast.LENGTH_SHORT).show();
        }
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog.setMessage("Registering ...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) { //CASTING
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(context.getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            // Later
                            //loginAfterRegister(email,password);
                        }
                        else{
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

    }

    public void login(String email, String password){

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(context.getApplicationContext(), "Please enter your email ", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(context.getApplicationContext(), "Please enter your email ", Toast.LENGTH_SHORT).show();
        }
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog.setMessage("Logging in ...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){ // if logging was successfull (from firebase)
                            ((Activity)context).finish();
                            context.startActivity(new Intent(context.getApplicationContext(),MainActivity.class));
                        }
                        else{
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
                            Log.i("g","faile"); // failure in logging in
                        }
                    }
                });

    }

    public void logout(){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Logout");
        alertDialog.setMessage("You are signed in as: "+ user.getEmail());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Logout",
                new DialogInterface.OnClickListener() {
                    //firebase logout
                    public void onClick(DialogInterface dialog, int which) {
                        //firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.signOut();
                        dialog.dismiss();
                        if(user ==null){
                            Log.i("1","right");
                        }
                        //startActivity(new Intent(this,MainActivity.class));
                        Log.i("1","logged out");
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
    /*
    public void  loginAfterRegister(String email, String password){ // After the user has registered, he will be signed in automatically
        //firebaseAuth
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        ((Activity)context).finish();
                        context.startActivity(new Intent(context.getApplicationContext(),MainActivity.class));
                    }
                });

    }*/


}
