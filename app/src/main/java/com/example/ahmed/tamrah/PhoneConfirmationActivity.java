package com.example.ahmed.tamrah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PhoneConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_confirmation);
    }

    //Button Handler
    //This is to make the app title clickable
    public void goToHome(View view) {
        finish();
    }
    //Button Handler
    //this is for the button in profile page "Edit Account"
    public void goToAccountSettings(View view) {
        startActivity(new Intent(this, AccountSettingsActivity.class));
    }
}
