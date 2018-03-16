package com.example.ahmed.tamrah;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class  LoginActivity extends AppCompatActivity{

    private Toolbar toolBar;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (User) getIntent().getSerializableExtra("User");
        if(user==null)
            user = new User();
        setResult(-1, null);
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        final EditText email = (EditText) findViewById(R.id.input_email);
        final EditText password = (EditText) findViewById(R.id.input_password);
        Button loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(email.getText().toString().trim(),password.getText().toString().trim());
            }
        });
    }

    public void login(String email, String password) {
        final Context context= this;
        //Email field or Password field is empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context.getApplicationContext(), "Please enter your email ", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(context.getApplicationContext(), "Please enter your password ", Toast.LENGTH_SHORT).show();
            return;
        }


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in ...");
        progressDialog.show();
        Auth.fbAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) { // if logging was successfull (from firebase)
                            if(Auth.fbAuth.getCurrentUser().isEmailVerified()){
                                fetchProfile(Auth.fbAuth.getCurrentUser().getUid());
                                user.loggedIn(true);
                            }
                            else{
                                Toast.makeText(context, "Please verify your email", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                            alertDialog.setTitle("Login Failed");
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

    private void fetchProfile(final String UID) {
        final Context context = this;
        final Intent intent = new Intent(context,MainActivity.class);
        DatabaseReference DBRef = FirebaseDatabase.getInstance().getReference("User");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Retrieving Profile ...");
        progressDialog.show();
        DBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot = dataSnapshot.child(UID);
                user.setProfileValues((Map<String, Object>)dataSnapshot.getValue());
                Intent intent = new Intent();
                intent.putExtra("User",  user);
                setResult(2, intent);
                progressDialog.dismiss();
                Toast.makeText(context, "Welcome Back, " + user.getName() + " \ud83d\ude09", Toast.LENGTH_LONG).show();
                finish();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void noAccount(View view) {
        finish();
        startActivity(new Intent(this,SignupActivity.class));
    }

    public void goToForgetPassword(View view) {
        //finish();
        startActivity(new Intent(this,ForgetPasswordActivity.class));
    }

    public void goToHome(View view) {
        finish();
    }

}
