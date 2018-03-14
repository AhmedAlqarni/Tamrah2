package com.example.ahmed.tamrah;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class  LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginButton;
    private EditText editTextEmail;
    private EditText editTextpassword;
    private Toolbar toolBar;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth ;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (User) getIntent().getSerializableExtra("User");

        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextpassword = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == loginButton){
            login(editTextEmail.getText().toString().trim(),editTextpassword.getText().toString().trim());
        }
    }

    public void login(String email, String password) {
        final Context context= this;
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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in ...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) { // if logging was successfull (from firebase)
                            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                ((Activity) context).finish();
                                context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class));}
                            else{
                                Toast.makeText(context.getApplicationContext(), "Email is not verified", Toast.LENGTH_LONG).show();

                            }

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

    public void noAccount(View view) {
        finish();
        startActivity(new Intent(this,SignupActivity.class));
    }

    public void goToForgetPassword(View view) {
        finish();
        startActivity(new Intent(this,ForgetPasswordActivity.class));
    }

    //Button Handler
    //This is to make the app title clickable
    public void goToHome(View view) {
        finish();
    }

}
