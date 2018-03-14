package com.example.ahmed.tamrah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                MainActivity.user.resetPassword(editTextEmail.getText().toString().trim());


            }

        });

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
