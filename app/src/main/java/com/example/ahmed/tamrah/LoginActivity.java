package com.example.ahmed.tamrah;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

       // if(firebaseAuth.getCurrentUser() != null){ To know if the user is logged in or not
            //finish();
         //   startActivity(new Intent(getApplicationContext(),Registration.class));
          //  finish();
      //  }

        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextpassword = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == loginButton){
            login();
        }

    }


    public void  loginAfterRegister(String email,String password){

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        finish();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                });

    }

    private void login() {
        String email = editTextEmail.getText().toString().trim(); //convert editText object to string
        String password = editTextpassword.getText().toString().trim();//convert editText object to string

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(getApplicationContext(), "Please enter your email ", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(getApplicationContext(), "Please enter your email ", Toast.LENGTH_SHORT).show();
        }

        progressDialog.setMessage("Logging in ...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){ // if logging was successfull (from firebase)
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
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
    public FirebaseAuth getFirebaseAuth(){
        return firebaseAuth;
    }

    public void noAccount(View view) {
        finish();
        startActivity(new Intent(this,Registration.class));
    }

    public void goToForgetPassword(View view) {
        finish();
        startActivity(new Intent(this,ForgetPassword.class));
    }
}
