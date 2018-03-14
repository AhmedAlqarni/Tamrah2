package com.example.ahmed.tamrah;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.common.SignInButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AddOfferActivity extends AppCompatActivity {

    private Offer offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        final TextInputEditText title = (TextInputEditText) findViewById(R.id.OfferTitleInput);
        EditText descView = (EditText) findViewById(R.id.OfferDiscInput);
        final EditText priceView = (EditText) findViewById(R.id.OfferPriceInput);
        final EditText typeView = (EditText) findViewById(R.id.OfferTypeInput);
        final Spinner citySpinner = (Spinner) findViewById(R.id.Region);
        Button submitBtn = (Button) findViewById(R.id.PublishOfferBtn);


        BufferedReader reader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.cities_en)));
        String[] citiesArray = new String[118];

        try {
            for(int i = 0; i < 118; i++) {
                citiesArray[i] = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, citiesArray);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adp2);

        submitBtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                offer = new Offer(title.getText().toString(), typeView.getText().toString(),
                        citySpinner.getSelectedItem().toString(), Double.parseDouble(priceView.getText().toString()),-1);
                offer.publish();
            }
        });


    }
}
