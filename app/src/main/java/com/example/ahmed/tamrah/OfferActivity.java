package com.example.ahmed.tamrah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class OfferActivity extends AppCompatActivity {

    private Offer offer;
    private User user;
    private ArrayList<Review> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        addRateSpinerValues();
        addQuantitySpinerValues();
        offer = (Offer) getIntent().getSerializableExtra("Offer");
        user = (User) getIntent().getSerializableExtra("User");
        fetchProfile(offer.getSellerUID());
        updateOfferContext();
    }

    private void updateOfferContext() {
        TextView titleView = (TextView) findViewById(R.id.text_offer_title);
        TextView cityView = (TextView) findViewById(R.id.text_offer_city);
        TextView descView = (TextView) findViewById(R.id.text_offer_description);
        TextView priceView = (TextView) findViewById(R.id.text_offer_price);
        TextView ratingView = (TextView) findViewById(R.id.text_offer_rating);

        titleView.setText(offer.getTitle());
        cityView.setText(offer.getCity());
        descView.setText(offer.getDesc());
        priceView.setText(offer.getPrice() + " S.R.");
        ratingView.setText(offer.getRate() + "");
    }

    private void updateSellerContext(User seller) {
        TextView sellerNameView = (TextView) findViewById(R.id.Username_viewOfferPage);
        CircleImageView sellerImageView = (CircleImageView) findViewById(R.id.profile_imageOfferPage);
        TextView sellerPhoneView = (TextView) findViewById(R.id.text_seller_mobile);
        TextView sellerRatingView = (TextView) findViewById(R.id.SellerRatingOfferPage);

        sellerNameView.setText(seller.getName());
        sellerImageView.setImageDrawable(new ImageFetcher().fetch(seller.getProfilePic()));
        sellerPhoneView.setText(seller.getPhoneNum());
        if(seller.getRate() < 0)
            sellerRatingView.setText("N\\A");
        else
            sellerRatingView.setText(seller.getRate() + "");
    }

    public void addRateSpinerValues(){
        List<String> ratingList = new ArrayList<>();
        ratingList.add("1");
        ratingList.add("2");
        ratingList.add("3");
        ratingList.add("4");
        ratingList.add("5");

        ArrayAdapter<String> ratingAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ratingList);
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner ratingSpinner = (Spinner) findViewById(R.id.spinner);
        ratingSpinner.setAdapter(ratingAdapter);
    }

    //to add all Quantity spinner values
    public void addQuantitySpinerValues(){
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_quantity);
        ArrayAdapter<String> adapter;
        List<String> list;
        list = new ArrayList<String>();
        //loop for inserting spinner values
        for (Double i = 0.5; i<=20; i= i +0.5){
            list.add(i+" Kg");
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void fetchProfile(final String UID) {
        DatabaseReference DBRef = FirebaseDatabase.getInstance().getReference("User");
        DBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot = dataSnapshot.child(UID);
                User seller = new User();
                seller.setProfileValues((Map<String, Object>)dataSnapshot.getValue());
                updateSellerContext(seller);
                Intent intent = new Intent();
                intent.putExtra("Seller",  user);
                setResult(0, intent);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
