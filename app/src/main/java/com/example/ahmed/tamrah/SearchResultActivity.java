package com.example.ahmed.tamrah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
        final SearchView searchView = (android.widget.SearchView) findViewById(R.id.search_view);
        final Context context = this;
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.e("onQueryTextChange", "called");
                Log.i("", "xxxxxxxx");
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseOfferSearch(query);

                return false;
            }

            //mAdapter = new OffersAdapter(offerList);

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Offer");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onMovieClick(View view, int position) {
                TextView tv = (TextView) findViewById(R.id.OfferTitle);
                Intent intent = new Intent(SearchResultActivity.this, OfferActivity.class);
                intent.putExtra("Offer", offerList.get(position));
                startActivity(intent);
            }

            @Override
            public void onMovieLongClick(View view, int position) {

            }


        }));
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
                viewHolder.setDetails(model.getTitle(), model.getType(), model.getPrice(),
                        model.getCity(), model.getRate());
                offerList.add(model);
                Log.i("","title is:   "+model.getTitle());
                Log.i("","Desc is:   "+model.getDesc());


            }

//            @Override
//            public void onBindViewHolder(MyViewHolder1 viewHolder, int position) {
//                super.onBindViewHolder(viewHolder, position);
//            }
        };
        pd.dismiss();
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
            TextView textViewOID = (TextView)view.findViewById(R.id.OID);


            textViewTitle.setText(title);
            textViewType.setText(type);
            textViewPrice.setText(price+"");
            textViewCity.setText(city);
            textViewRate.setText(rate+"");
            //textViewOID.setText()

        }
    }

    //Button Handler
    //this is for any clicked offer in any page
    public void goToOffer(View view) {
//        ecyclerView.positi
//        Offer offer = new Offer();
//        TextView tv = (TextView) findViewById(R.id.OfferTitle);
//        Log.i("the title is :             ",tv.getText().toString());
//
//        Intent intent = new Intent(this, OfferActivity.class);
////        intent.putExtra("OID", );
//        startActivity(intent);
    }

    public void goToAddOffer(View view) {
        startActivity(new Intent(this, AddOfferActivity.class));
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector mGestureDetector;
        private ClickListener mClickListener;


        public RecyclerTouchListener(final Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.mClickListener = clickListener;
            mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if (child!=null && clickListener!=null){
                        clickListener.onMovieLongClick(child,recyclerView.getChildAdapterPosition(child));
                    }
                    super.onLongPress(e);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child!=null && mClickListener!=null && mGestureDetector.onTouchEvent(e)){
                mClickListener.onMovieClick(child,rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static interface ClickListener{
        public void onMovieClick(View view, int position);
        public void onMovieLongClick(View view, int position);
    }



}
