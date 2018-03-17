package com.example.ahmed.tamrah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        query = getIntent().getStringExtra("text");


        mAdapter = new OffersAdapter(offerList);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Offer");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);//Created by Khalid
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        firebaseOfferSearch();


    }

    public void firebaseOfferSearch(){
        FirebaseRecyclerAdapter<Offer,OffersAdapter.MyViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Offer, OffersAdapter.MyViewHolder>(
                Offer.class,
                R.layout.search_result_format,
                OffersAdapter.MyViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(OffersAdapter.MyViewHolder viewHolder, Offer model, int position) {

                viewHolder.setDetails(model.getTitle(), model.getType(), model.getPrice(), model.getCity(), model.getRate());

            }
        };
        recyclerView.setAdapter(mAdapter);
    }
}
