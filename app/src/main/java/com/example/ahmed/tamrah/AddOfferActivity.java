package com.example.ahmed.tamrah;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static com.example.ahmed.tamrah.AccountActivity.getCorrectlyOrientedImage;
import static com.example.ahmed.tamrah.AccountActivity.getOrientation;

public class AddOfferActivity extends AppCompatActivity {

    private Offer offer;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SELECTED_PICTURE = 1;
    final Spinner citySpinner = (Spinner) findViewById(R.id.Region);
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);
        user = (User) getIntent().getSerializableExtra("User");
        setResult(-1,null);
        final TextInputEditText title = (TextInputEditText) findViewById(R.id.OfferTitleInput);
        EditText descView = (EditText) findViewById(R.id.OfferDiscInput);
        final EditText priceView = (EditText) findViewById(R.id.OfferPriceInput);
        final EditText typeView = (EditText) findViewById(R.id.OfferTypeInput);
        Button submitBtn = (Button) findViewById(R.id.PublishOfferBtn);

        addCitySpinnerValues();
        submitBtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                offer = new Offer(title.getText().toString(), typeView.getText().toString(),
                        citySpinner.getSelectedItem().toString(), Double.parseDouble(priceView.getText().toString()),-1);
                offer.setDesc("No Desc. in the ADD OFFER activity. Modify that");
                publish();
            }
        });


    }

    private void addCitySpinnerValues() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.cities_en)));
        String[] citiesArray = new String[119];

        try {
            for(int i = 0; i < 119; i++) {
                citiesArray[i] = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, citiesArray);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adp2);
    }

    //For reading a picture from the device
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //For reading a picture from the deviceif(requestCode ==   SELECTED_PICTURE && data != null) {
        Uri uri = data.getData();
        // Show the Selected Image onImageView ImageView cV = (ImageView) findViewById(R.id.imageViewAdding);
        ImageView cV = (ImageView) findViewById(R.id.imageViewAdding);
        getOrientation(this, uri);
        try {
            //profile_image
            Bitmap loadedBitmap = getCorrectlyOrientedImage(this, uri,1000);
            cV.setImageBitmap(loadedBitmap);
            //cV.setImageURI(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //for the Camera App>>>
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap); Image result
        }
    }


    //Button Handler
    //for selecting image in the Add OfferFrag page
    public void selectPictureBtn(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECTED_PICTURE);

    }


    public void publish() {
        DatabaseReference FBofferNode = FirebaseDatabase.getInstance().getReference().child("Offer");
        Map offerPost = new HashMap();
        offerPost.put("SellerUID", Auth.fbAuth.getUid());
        offerPost.put("Title", offer.getTitle());
        offerPost.put("Description", offer.getDesc());
        offerPost.put("Price", new DecimalFormat(".##").format(offer.getPrice()));
        offerPost.put("Type", offer.getType());
        offerPost.put("Rate", offer.getRate());
        offerPost.put("City", offer.getCity());
        FBofferNode.push().setValue(offerPost);
        String OID = FBofferNode.getKey();

        FBofferNode = FirebaseDatabase.getInstance().getReference().child("User").
                child(Auth.fbAuth.getUid()).child("offer");
        FBofferNode.push().setValue(new HashMap().put("OID", OID));
        Intent intent = new Intent();
        intent.putExtra("User",  user);
        setResult(0, intent);
    }
}
