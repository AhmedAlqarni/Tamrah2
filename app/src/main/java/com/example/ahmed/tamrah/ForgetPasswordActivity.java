package com.example.ahmed.tamrah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ForgetPasswordActivity extends AppCompatActivity {
    private Toolbar toolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }

    public void noAccount(View view) {
        finish();
        startActivity(new Intent(this,SignupActivity.class));
    }
    //Button Handler
    //This is to make the app title clickable
    public void goToHome(View view) {
        finish();
    }
}
