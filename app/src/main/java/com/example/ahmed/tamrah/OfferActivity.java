package com.example.ahmed.tamrah;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class OfferActivity extends AppCompatActivity {

    private Offer offer;
    //private TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        offer = (Offer) getIntent().getSerializableExtra("Offer");
        TextView title = (TextView) findViewById(R.id.OfferTitle);
        TextView city = (TextView) findViewById(R.id.City);
        TextView type = (TextView) findViewById(R.id.TamrahTypeOfferPage);
        TextView price = (TextView) findViewById(R.id.Phone);
        TextView desc = (TextView) findViewById(R.id.Description);
        TextView rate = (TextView) findViewById(R.id.OfferRating);

        title.setText(offer.getTitle());
        city.setText(offer.getCity());
        type.setText(offer.getType());
        price.setText(offer.getPrice() + " S.R.");
        desc.setText(offer.getDesc());
        //Log.i("the description is:",offer.getDesc());
        if(offer.getRate().equals("-1"))
            rate.setText("N\\A");
        else
            rate.setText(offer.getRate());

        //OfferTitle.setText(offer.getTitle());

    }

    private void fetchOffer(final String OID) {
        /*DatabaseReference DBRef = FirebaseDatabase.getInstance().getReference("Offer");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Retrieving Offer ...");
        progressDialog.show();
        DBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot = dataSnapshot.child(OID); //????
                //offer.setValues((Map<String, Object>)dataSnapshot.getValue());
                Map<String,Object> map = (Map<String,Object>)dataSnapshot.getValue();
                OfferTitle = (TextView) findViewById(R.id.OfferTitle);
                OfferTitle.setText((String) map.get("Title"));;
                progressDialog.dismiss();
                //updateContext();???
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }
}
