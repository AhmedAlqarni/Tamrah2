package com.example.ahmed.tamrah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

public class AccountSettingsActivity extends AppCompatActivity {


    private Toolbar toolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        //ToolBar
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        addCitySpinerValues();
        addLanguageSpinerValues();
        addCurrencySpinerValues();
    }


    //to add all City spinner values
    // add action on selection for the spinner
    public void addCitySpinerValues(){
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
    public void addLanguageSpinerValues(){
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
    public void addCurrencySpinerValues(){
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


    //Settings page text switcher
    //Name editing
    public void settingsEditName(View view) {
        Button b =  (Button) findViewById(R.id.button32);
        if(b.getText().equals("DONE")){
            b.setText("EDIT");
            EditText edit =  (EditText) findViewById(R.id.hidden_edit_view);
            TextView tV = (TextView) findViewById(R.id.nameInput23);
            tV.setText(edit.getText().toString());

        }else{
            b.setText("DONE");
            TextView tV = (TextView) findViewById(R.id.nameInput23);
            EditText edit =  (EditText) findViewById(R.id.hidden_edit_view);
            edit.setText(tV.getText());


        }
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher);
        switcher.showNext(); //or switcher.showPrevious();
        TextView myTV = (TextView) switcher.findViewById(R.id.nameInput23);

    }

    //Settings page text switcher
    //Email editing
    public void settingsEditEmail(View view) {
        Button b =  (Button) findViewById(R.id.editEmailBtn);
        if(b.getText().equals("DONE")){
            b.setText("EDIT");
            EditText edit =  (EditText) findViewById(R.id.hidden_edit_email);
            TextView tV = (TextView) findViewById(R.id.EmailInput);
            tV.setText(edit.getText().toString());

        }else{
            b.setText("DONE");
            TextView tV = (TextView) findViewById(R.id.EmailInput);
            EditText edit =  (EditText) findViewById(R.id.hidden_edit_email);
            edit.setText(tV.getText());

        }
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher2);
        switcher.showNext(); //or switcher.showPrevious();
        TextView myTV = (TextView) switcher.findViewById(R.id.EmailInput);

    }

    //Settings page text switcher
    //password editing
    public void settingsEditPassword(View view) {
        Button b =  (Button) findViewById(R.id.editPasswordBtn);
        if(b.getText().equals("DONE")){
            b.setText("EDIT");
            EditText edit =  (EditText) findViewById(R.id.hidden_edit_password);
            TextView tV = (TextView) findViewById(R.id.PasswordInputField);
            tV.setText(edit.getText().toString());

        }else{
            b.setText("DONE");

        }
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher3);
        switcher.showNext(); //or switcher.showPrevious();
        TextView myTV = (TextView) switcher.findViewById(R.id.PasswordInputField);

    }

    //Settings page text switcher
    //phone editing
    public void settingsEditPhone(View view) {
        Button b =  (Button) findViewById(R.id.editPhoneBtn);
        if(b.getText().equals("DONE")){
            b.setText("EDIT");
            EditText edit =  (EditText) findViewById(R.id.hidden_edit_phone);
            TextView tV = (TextView) findViewById(R.id.PhoneInputField);
            tV.setText(edit.getText().toString());

        }else{
            b.setText("DONE");
            TextView tV = (TextView) findViewById(R.id.PhoneInputField);
            EditText edit =  (EditText) findViewById(R.id.hidden_edit_phone);
            edit.setText(tV.getText());

        }
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher4);
        switcher.showNext(); //or switcher.showPrevious();
        TextView myTV = (TextView) switcher.findViewById(R.id.PhoneInputField);

    }

    //Settings page text switcher
    //address editing
    public void settingsEditAddress(View view) {
        Button b =  (Button) findViewById(R.id.editAddressBtn);
        if(b.getText().equals("DONE")){
            b.setText("EDIT");
            EditText edit =  (EditText) findViewById(R.id.hidden_edit_address);
            TextView tV = (TextView) findViewById(R.id.AddressInputField);
            tV.setText(edit.getText().toString());


        }else{
            b.setText("DONE");
            TextView tV = (TextView) findViewById(R.id.AddressInputField);
            EditText edit =  (EditText) findViewById(R.id.hidden_edit_address);
            edit.setText(tV.getText());

        }
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher5);
        switcher.showNext(); //or switcher.showPrevious();
        TextView myTV = (TextView) switcher.findViewById(R.id.AddressInputField);

    }

    //Settings page text switcher
    //bio editing
    public void settingsEditBio(View view) {
        Button b =  (Button) findViewById(R.id.editBioBtn);
        if(b.getText().equals("DONE")){
            b.setText("EDIT");
            EditText edit =  (EditText) findViewById(R.id.hidden_edit_Bio);
            TextView tV = (TextView) findViewById(R.id.BioInputField);
            tV.setText(edit.getText().toString());


        }else{
            b.setText("DONE");
            TextView tV = (TextView) findViewById(R.id.BioInputField);
            EditText edit =  (EditText) findViewById(R.id.hidden_edit_Bio);
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
