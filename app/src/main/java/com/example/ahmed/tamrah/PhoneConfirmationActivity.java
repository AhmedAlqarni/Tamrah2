package com.example.ahmed.tamrah;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneConfirmationActivity extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private EditText phoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_confirmation);

        //FirebaseUser f = FirebaseAuth.

        phoneEditText = (EditText) findViewById(R.id.input_sms_confirmation);
        //should have EditText for code verification num
        Button confirmButton = (Button) findViewById(R.id.btn_Cnfrm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = phoneEditText.getText().toString();
                //Toast.makeText(this, phoneNum, Toast.LENGTH_SHORT).show();
                verifyPhone(phoneNum,mCallBacks);
            }

        });

        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                //signInWithPhoneAuthCredential(phoneAuthCredential);
                Log.i("q","49");

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.i("q","55");

            }
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
               // Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                //mVerificationId = verificationId;
                //mResendToken = token;

                // ...
                Log.i("73","kkkkk");
            }

        };

        //Auth.fbAuth = FirebaseAuth.getInstance();

    }


    public void verifyPhone(String phoneNumber, PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                15,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbac
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        Auth.fbAuth.addAuthStateListener(mAuthListener);
    }*/

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
