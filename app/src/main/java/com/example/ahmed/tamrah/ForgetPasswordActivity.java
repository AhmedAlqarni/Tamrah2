package com.example.ahmed.tamrah;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    private Toolbar toolBar;


    EditText editTextEmail;
    Button btnResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        editTextEmail = (EditText) findViewById(R.id.input_email);
        btnResetPassword=(Button) findViewById(R.id.btn_Rs);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                resetPassword(editTextEmail.getText().toString().trim());
            }

        });

    }

    public void noAccount(View view) {
        finish();
        startActivity(new Intent(this,SignupActivity.class));
    }

    public void resetPassword(String emailAddress) {
        final Context context = this;
        Auth.fbAuth = FirebaseAuth.getInstance();
        //String emailAddress = "user@example.com";

        Auth.fbAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("", "Email sent.");
                            Toast.makeText(context.getApplicationContext(), "An email has been sent to you to reset your password\nThank you", Toast.LENGTH_LONG).show();
                            goToLoginPage(); //to show the LoginActivity ...
                        }
                        else{
                            Toast.makeText(context.getApplicationContext(), "Sorry, Invalid Email", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
    //Button Handler
    //This is to make the app title clickable
    public void goToHome(View view) {
        finish();
    }
    public void goToLoginPage(){
        //startActivity(new Intent(this, LoginActivity.class));
        finish();

    }

}
