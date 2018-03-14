package com.example.ahmed.tamrah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class UpgradeAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_account);
        addCitySpinerValues();
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
}
