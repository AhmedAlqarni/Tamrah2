package com.example.ahmed.tamrah;

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

import java.util.ArrayList;
import java.util.List;

public class AccountSettingsActivity extends AppCompatActivity {


    private Toolbar toolBar;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        //Created by Khalid
        //User user = new User();
        Bundle exras = getIntent().getExtras();
        user = (User) exras.get("User");
        setResult(-1, null);//????

        TextView tV = (TextView) findViewById(R.id.nameInput23);
        Log.i("30","the name is: "+user.getName());
        tV.setText(user.getName());

        TextView emailTextView = (TextView) findViewById(R.id.EmailInput);
        emailTextView.setText(user.getEmail());

        TextView phoneTextView = (TextView) findViewById(R.id.PhoneInputField);
        phoneTextView.setText(user.getPhoneNum());

        TextView addressTextView = (TextView) findViewById(R.id.AddressInputField);
        addressTextView.setText(user.getAddress());

        TextView bioTextView = (TextView) findViewById(R.id.BioInputField);
        //emailTextView.setText(user.getBio());

        //ToolBar
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        addCitySpinerValues();
        addLanguageSpinerValues();
        addCurrencySpinerValues();


    }


    //to add all City spinner values
    // add action on selection for the spinner
    public void addCitySpinerValues() {
        final Spinner spinner = (Spinner) findViewById(R.id.CitySpinner);
        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<String>();
        list.add("Makkah");
        list.add("Riyadh");
        list.add("Jeddah");
        list.add("Dammam");
        list.add("Tabuk");
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                TextView tV = (TextView) findViewById(R.id.CityInputLabel);
                tV.setText(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

    }


    //to add all Language spinner values
    // add action on selection for the spinner
    public void addLanguageSpinerValues() {
        final Spinner spinner = (Spinner) findViewById(R.id.LanguageSpinner);
        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<String>();
        list.add("English");
        list.add("Arabic");
        list.add("Hindi");
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                TextView tV = (TextView) findViewById(R.id.SelectedLanguage);
                tV.setText(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }


    //to add all Currency spinner values
    // add action on selection for the spinner
    public void addCurrencySpinerValues() {
        final Spinner spinner = (Spinner) findViewById(R.id.CurrencySpinner);
        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<String>();
        list.add("SAR");
        list.add("USD");
        list.add("AED ");
        list.add("KWD");
        list.add("BHD");
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                TextView tV = (TextView) findViewById(R.id.SelectedCurrency);
                tV.setText(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }

///////////////////////////////////
    //Settings page text switcher
    //Name editing
    public void settingsEditName(View view) {
        Button b = (Button) findViewById(R.id.button32);
        if (b.getText().equals("DONE")) {
            b.setText("EDIT");
            EditText edit = (EditText) findViewById(R.id.hidden_edit_view);
            TextView tV = (TextView) findViewById(R.id.nameInput23);
            tV.setText(edit.getText().toString());


        } else {
            b.setText("DONE");
            TextView tV = (TextView) findViewById(R.id.nameInput23);
            final EditText edit = (EditText) findViewById(R.id.hidden_edit_view);
            edit.setText(tV.getText());
            final Button b2 = b;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //edit.getText().toString().trim();
                    User user = new User(); //should be reviewed
                    user.setName(edit.getText().toString().trim());
                    b2.setText("EDit");
                }
            });


        }
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher);
        switcher.showNext(); //or switcher.showPrevious();
        TextView myTV = (TextView) switcher.findViewById(R.id.nameInput23);



    }


//////////////////////////////////




    /*//Settings page text switcher
    //Name editing
    public void settingsEditName(View view) {
        Button b = (Button) findViewById(R.id.button32);
        if (b.getText().equals("DONE")) {
            b.setText("EDIT");
            EditText edit = (EditText) findViewById(R.id.hidden_edit_view);
            TextView tV = (TextView) findViewById(R.id.nameInput23);
            tV.setText(edit.getText().toString());

        } else {
            b.setText("DONE");
            TextView tV = (TextView) findViewById(R.id.nameInput23);
            EditText edit = (EditText) findViewById(R.id.hidden_edit_view);
            edit.setText(tV.getText());


        }
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher);
        switcher.showNext(); //or switcher.showPrevious();
        TextView myTV = (TextView) switcher.findViewById(R.id.nameInput23);

    }*/

/////////////////////////////

    //Settings page text switcher
    //Email editing
    public void settingsEditEmail(View view) {
        Button b = (Button) findViewById(R.id.editEmailBtn);
        if (b.getText().equals("DONE")) {
            b.setText("EDIT");
            EditText edit = (EditText) findViewById(R.id.hidden_edit_email);
            TextView tV = (TextView) findViewById(R.id.EmailInput);
            tV.setText(edit.getText().toString());

        } else {
            b.setText("DONE");
            TextView tV = (TextView) findViewById(R.id.EmailInput);
            final EditText edit = (EditText) findViewById(R.id.hidden_edit_email);
            edit.setText(tV.getText());
            final Button b2 = b;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //edit.getText().toString().trim();
                    User user = new User(); //should be reviewed
                    user.setName(edit.getText().toString().trim());
                    b2.setText("EDIT");
                }
            });

        }
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher2);
        switcher.showNext(); //or switcher.showPrevious();
        TextView myTV = (TextView) switcher.findViewById(R.id.EmailInput);

    }

////////////////////////////

    /*//Settings page text switcher
    //Email editing
    public void settingsEditEmail(View view) {
        Button b = (Button) findViewById(R.id.editEmailBtn);
        if (b.getText().equals("DONE")) {
            b.setText("EDIT");
            EditText edit = (EditText) findViewById(R.id.hidden_edit_email);
            TextView tV = (TextView) findViewById(R.id.EmailInput);
            tV.setText(edit.getText().toString());

        } else {
            b.setText("DONE");
            TextView tV = (TextView) findViewById(R.id.EmailInput);
            EditText edit = (EditText) findViewById(R.id.hidden_edit_email);
            edit.setText(tV.getText());

        }
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher2);
        switcher.showNext(); //or switcher.showPrevious();
        TextView myTV = (TextView) switcher.findViewById(R.id.EmailInput);

    }*/

    /*//Settings page text switcher
    //password editing
    public void settingsEditPassword(View view) {
        Button b = (Button) findViewById(R.id.editPasswordBtn);
        if (b.getText().equals("DONE")) {
            b.setText("EDIT");
            EditText edit = (EditText) findViewById(R.id.hidden_edit_password);
            TextView tV = (TextView) findViewById(R.id.PasswordInputField);
            tV.setText(edit.getText().toString());

        } else {
            b.setText("DONE");

        }
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher3);
        switcher.showNext(); //or switcher.showPrevious();
        TextView myTV = (TextView) switcher.findViewById(R.id.PasswordInputField);

    }*/

/////////////////////////////////

    //Settings page text switcher
    //password editing
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

/////////////////////////////////

    //Settings page text switcher
    //phone editing
    public void settingsEditPhone(View view) {
        Button b = (Button) findViewById(R.id.editPhoneBtn);
        if (b.getText().equals("DONE")) {
            b.setText("EDIT");
            EditText edit = (EditText) findViewById(R.id.hidden_edit_phone);
            TextView tV = (TextView) findViewById(R.id.PhoneInputField);
            tV.setText(edit.getText().toString());

        } else {
            b.setText("DONE");
            TextView tV = (TextView) findViewById(R.id.PhoneInputField);
            EditText edit = (EditText) findViewById(R.id.hidden_edit_phone);
            edit.setText(tV.getText());

        }
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher4);
        switcher.showNext(); //or switcher.showPrevious();
        TextView myTV = (TextView) switcher.findViewById(R.id.PhoneInputField);

    }

    //Settings page text switcher
    //address editing
    public void settingsEditAddress(View view) {
        Button b = (Button) findViewById(R.id.editAddressBtn);
        if (b.getText().equals("DONE")) {
            EditText edit = (EditText) findViewById(R.id.hidden_edit_address);
            TextView tV = (TextView) findViewById(R.id.AddressInputField);
            tV.setText(edit.getText().toString());
            User user = new User();
            user.setAddress(edit.getText().toString().trim());
            b.setText("EDIT");

        } else {
            b.setText("DONE");
            TextView tV = (TextView) findViewById(R.id.AddressInputField);
            EditText edit = (EditText) findViewById(R.id.hidden_edit_address);
            edit.setText(tV.getText());

        }
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher5);
        switcher.showNext(); //or switcher.showPrevious();
        TextView myTV = (TextView) switcher.findViewById(R.id.AddressInputField);

    }

    //Settings page text switcher
    //bio editing
    public void settingsEditBio(View view) {
        Button b = (Button) findViewById(R.id.editBioBtn);
        if (b.getText().equals("DONE")) {
            EditText edit = (EditText) findViewById(R.id.hidden_edit_Bio);
            TextView tV = (TextView) findViewById(R.id.BioInputField);
            tV.setText(edit.getText().toString());
            User user = new User();
            user.setBio(edit.getText().toString().trim());//to be implemented in the user class
            b.setText("EDIT");
            b.setText("EDIT");
        } else {
            b.setText("DONE");
            TextView tV = (TextView) findViewById(R.id.BioInputField);
            EditText edit = (EditText) findViewById(R.id.hidden_edit_Bio);
            edit.setText(tV.getText());

        }
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher6);
        switcher.showNext(); //or switcher.showPrevious();
        TextView myTV = (TextView) switcher.findViewById(R.id.BioInputField);

    }


    //Button Handler
    //This is to make the app title clickable
    //also closes this activity
    public void goToHome(View view) {
        finish();
    }
}
