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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SignupActivity extends AppCompatActivity {

    //private View rootView;

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Toolbar toolBar;
    private Intent intent;
    private TextView textViewSignIn;
    private TextView textViewName;
    private User user;
    private FirebaseUser FBUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //?????
        user = (User) getIntent().getSerializableExtra("User");
        setResult(-1, null);
        //ToolBar
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        buttonRegister = (Button) findViewById(R.id.btn_signup);
        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);
        textViewSignIn = (TextView) findViewById(R.id.link_login); //???
        textViewName = (TextView) findViewById(R.id.input_name);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                register(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim(),
                        textViewName.getText().toString().trim());
            }

        });

    }

    public void register(String email, String password, final String name) {
        final User temp = user;
        final Context context = this;
        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(context, "Please enter your email ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(context.getApplicationContext(), "Please enter your email ", Toast.LENGTH_SHORT).show();
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering ...");
        progressDialog.show();
        Auth.fbAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) { //CASTING
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(context.getApplicationContext(), "Registered Successfully\nPlease verify your email", Toast.LENGTH_SHORT).show();
                            confirmEmail();
                            makeUserNode(name);
                            intent = new Intent(context,LoginActivity.class);
                            intent.putExtra("User", temp);
                            startActivityForResult(intent, 0);
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
    }

    public void confirmEmail() {
        Auth.fbAuth.getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }

    public void makeUserNode(String name){
        String UID = Auth.fbAuth.getCurrentUser().getUid();
        DatabaseReference DBRef = FirebaseDatabase.getInstance().getReference().child("User").child(UID);
        Map userNode = new HashMap();
        userNode.put("name", name);
        userNode.put("region","Dammam");
        userNode.put("description","");
        userNode.put("phoneNum","");
        userNode.put("profileImage","");
        userNode.put("address","");
        userNode.put("rate",-1);
        userNode.put("isSeller",false);
        userNode.put("email",Auth.fbAuth.getCurrentUser().getEmail());
        DBRef.setValue(userNode);
        DBRef.child("offer").push();
        DBRef.child("cart").push();
        DBRef.child("order").push();
        DBRef.child("review").push();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 0) {
            user = (User) data.getSerializableExtra("User");
            Intent intent = new Intent();
            intent.putExtra("User",  user);
            setResult(0, intent);
        }
        finish();
    }

    public void hasAccount(View view) {
        finish();
        startActivity(new Intent(this,LoginActivity.class));
    }

    //Button Handler
    //This is to make the app title clickable
    public void goToHome(View view) {
        finish();
    }
}
