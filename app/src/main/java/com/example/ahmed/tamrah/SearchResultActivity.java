package com.example.ahmed.tamrah;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private List<Offer> offerList = new ArrayList<>();
    private RecyclerView recyclerView; //static by Khalid
    private OffersAdapter mAdapter;//static by Khalid
    private DatabaseReference databaseReference ;
    private String query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);



        //mAdapter = new OffersAdapter(offerList);



    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Offer");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);//Created by Khalid
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(mAdapter);

        query = getIntent().getStringExtra("text");
        Log.i("44",query);
        firebaseOfferSearch(query);

    }

    public void firebaseOfferSearch(String query){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Retrieving search results ... MotherFucker xD");
        pd.show();
        Query firebaseQuerySearch = databaseReference.orderByChild("Type").startAt(query).endAt(query+"\uf8ff");
        Log.i("asasasasasasasasasasasas",firebaseQuerySearch.toString());

        FirebaseRecyclerAdapter<Offer,MyViewHolder1> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Offer, MyViewHolder1>(
                Offer.class,
                R.layout.search_result_format,
                MyViewHolder1.class,
                firebaseQuerySearch) {
            @Override
            protected void populateViewHolder(MyViewHolder1 viewHolder, Offer model, int position) {
                //Log.i("", model.getType());
                viewHolder.setDetails(model.getTitle(), model.getType(), model.getPrice(), model.getCity(), model.getRate());
                pd.dismiss();
            }

        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class MyViewHolder1 extends RecyclerView.ViewHolder {
        public TextView title, type, city, price, rate;
        private View view;

        public MyViewHolder1(View view) {
            super(view);

            //Created by Khalid
            this.view = view;

//            title = (TextView) view.findViewById(R.id.OfferTitle);
//            type = (TextView) view.findViewById(R.id.TamrahType);
//            city = (TextView) view.findViewById(R.id.city);
//            price = (TextView) view.findViewById(R.id.OfferPrice);
//            rate = (TextView) view.findViewById(R.id.Rating);

        }

        public void setDetails(String title, String type, String price, String city,
                               String rate){
//            Log.i("38",type);
            TextView textViewTitle = (TextView) view.findViewById(R.id.OfferTitle);
            TextView textViewType = (TextView)view.findViewById(R.id.TamrahType);
            TextView textViewPrice = (TextView)view.findViewById(R.id.OfferPrice);
            TextView textViewCity = (TextView)view.findViewById(R.id.city);
            //TextView textViewDescription = (TextView)view.findViewById(R.id.);
            TextView textViewRate = (TextView)view.findViewById(R.id.Rating);
            // TextView textViewImg = view.findViewById(R.id.);

            textViewTitle.setText(title);
            textViewType.setText(type);
            textViewPrice.setText(price+"");
            textViewCity.setText(city);
            textViewRate.setText(rate+"");

        }
    }

}
