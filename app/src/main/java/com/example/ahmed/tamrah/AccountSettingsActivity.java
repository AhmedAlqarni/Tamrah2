package com.example.ahmed.tamrah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountSettingsActivity extends AppCompatActivity {


    private Toolbar toolBar;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        user = (User) getIntent().getSerializableExtra("User");
        setResult(-1, null);
        updateContext();

        //ToolBar
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

    }

    private void updateContext() {
        addCitySpinnerValues();
        TextView nameView = (TextView) findViewById(R.id.nameInput23);
        TextView phoneView = (TextView) findViewById(R.id.PhoneInputField);
        TextView addressView = (TextView) findViewById(R.id.AddressInputField);
        TextView bioView = (TextView) findViewById(R.id.BioInputField);
        nameView.setText(user.getName());
        phoneView.setText(user.getPhoneNum());
        addressView.setText(user.getAddress());
        bioView.setText(user.getDescription());
    }
    private void updateCallbackResult() {
        Intent intent = new Intent();
        intent.putExtra("User",  user);
        setResult(0, intent);
    }

    public void updateDatabase(String key, String value){
        DatabaseReference DBRef = FirebaseDatabase.getInstance().getReference().child("User");
        DBRef.child(Auth.fbAuth.getUid()).child(key).setValue(value);
    }

    private void addCitySpinnerValues() {
        final Spinner citySpinner = (Spinner) findViewById(R.id.CitySpinner);
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.cities_en)));
        final String[] citiesArray = new String[119];
        int userCityIndex = 0;

        try {
            for(int i = 0; i < 119; i++) {
                citiesArray[i] = reader.readLine();
                if(user.getRegion().equals(citiesArray[i]))
                    userCityIndex = i;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, citiesArray);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adp2);
        citySpinner.setSelection(userCityIndex);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(!citySpinner.getSelectedItem().toString().equals(user.getRegion()))
                    updateDatabase("region", citySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

    }

    public void settingsEditName(View view) {
        Button b = (Button) findViewById(R.id.button32);
        EditText edit = (EditText) findViewById(R.id.hidden_edit_view);
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher);
        switcher.showNext();

        if (b.getText().equals("Done")) {
            b.setText("Edit");
            String newName = edit.getText().toString().trim();
            if(!newName.equals(user.getName())) {
                user.setName(newName);
                updateContext();
                updateDatabase("name", user.getName());
                updateCallbackResult();
            }

        } else {
            b.setText("Done");
            edit.setText(user.getName());
        }
    }

    public void settingsEditPhone(View view) {
        Button b = (Button) findViewById(R.id.editPhoneBtn);
        EditText edit = (EditText) findViewById(R.id.hidden_edit_phone);
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher);
        switcher.showNext();

        if (b.getText().equals("Done")) {
            b.setText("Edit");
            String newPhone = edit.getText().toString().trim();
            if(!newPhone.equals(user.getPhoneNum())) {
                user.setPhoneNum(newPhone);
                updateContext();
                updateDatabase("phoneNum", user.getPhoneNum());
                updateCallbackResult();
            }

        } else {
            b.setText("Done");
            edit.setText(user.getPhoneNum());
        }
    }

    public void settingsEditAddress(View view) {
        Button b = (Button) findViewById(R.id.editAddressBtn);
        EditText edit = (EditText) findViewById(R.id.hidden_edit_address);
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher);
        switcher.showNext();

        if (b.getText().equals("Done")) {
            b.setText("Edit");
            String newAddress = edit.getText().toString().trim();
            if(!newAddress.equals(user.getAddress())) {
                user.setAddress(newAddress);
                updateContext();
                updateDatabase("address", user.getAddress());
                updateCallbackResult();
            }

        } else {
            b.setText("Done");
            edit.setText(user.getAddress());
        }
    }

    public void settingsEditBio(View view) {
        Button b = (Button) findViewById(R.id.editBioBtn);
        EditText edit = (EditText) findViewById(R.id.hidden_edit_Bio);
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher);
        switcher.showNext();

        if (b.getText().equals("Done")) {
            b.setText("Edit");
            String newBio = edit.getText().toString().trim();
            if(!newBio.equals(user.getDescription())) {
                user.setBio(newBio);
                updateContext();
                updateDatabase("description", user.getDescription());
                updateCallbackResult();
            }

        } else {
            b.setText("Done");
            edit.setText(user.getDescription());
        }
    }

    ///////// REVIEW BELOW //////////
    public void settingsEditPassword(View view) {
        Button b = (Button) findViewById(R.id.editPasswordBtn);
        EditText edit ;
        TextView tV = (TextView) findViewById(R.id.PasswordInputField);

        if (b.getText().equals("DONE")) {
            edit = (EditText) findViewById(R.id.hidden_edit_password);
            Log.i("xxxxxxxxxxxxxxxxxx:",edit.getText().toString().trim());
            setPassword(edit.getText().toString().trim());
            b.setText("EDIT");
            tV.setText(edit.getText().toString());
        } else {
            b.setText("DONE");
            edit = (EditText) findViewById(R.id.hidden_edit_password);
            tV = (TextView) findViewById(R.id.PasswordInputField);
        }
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher3);
        switcher.showNext(); //or switcher.showPrevious();
        TextView myTV = (TextView) switcher.findViewById(R.id.PasswordInputField);

    }

    public void setPassword(String newPassword) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //String newPassword = "SOME-SECURE-PASSWORD";
        Log.i("new pass: ",newPassword);

        firebaseUser.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i("", "User password updated.");
                        }
                    }
                });
    }
    ///////// REVIEW ABOVE //////////

    public void goToHome(View view) {
        finish();
    }
}
