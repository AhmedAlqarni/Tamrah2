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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    //private View rootView;

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignIn;
    private TextView textViewName;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Auth code (khalid)
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        buttonRegister = (Button) findViewById(R.id.btn_signup);
        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);
        textViewSignIn = (TextView) findViewById(R.id.link_login); //???
        textViewName = (TextView) findViewById(R.id.input_name);
        Log.i("k","108");
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                registerUser();
            }

        });

    }

    private void registerUser(){
        User user = new User(this);
        user.register(editTextEmail.getText().toString().trim(),
                editTextPassword.getText().toString().trim(),
                textViewName.getText().toString().trim());

        /*
        Log.i("q","160");
        String email, password;
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(getApplicationContext(), "Please enter your email ", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(getApplicationContext(), "Please enter your email ", Toast.LENGTH_SHORT).show();
        }

        progressDialog.setMessage("Registering ...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            loginAfterRegister();
                        }
                        else{
                            progressDialog.dismiss();
                            AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
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
                */


    }
    /*
    public void  loginAfterRegister(){ // After the user has registered, he will be signed in automatically
        String email = editTextEmail.getText().toString().trim(); //convert editText object to string
        String password = editTextPassword.getText().toString().trim();//convert editText object to string
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        finish();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                });

    }
    */

    public void hasAccount(View view) {
        finish();
        startActivity(new Intent(this,LoginActivity.class));
    }
}
