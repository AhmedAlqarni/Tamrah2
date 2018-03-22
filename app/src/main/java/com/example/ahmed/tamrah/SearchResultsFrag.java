package com.example.ahmed.tamrah;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchResultsFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchResultsFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultsFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static DatabaseReference databaseReference ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Offer> offerList = new ArrayList<>();
    private static RecyclerView recyclerView; //static by Khalid
    private static OffersAdapter mAdapter;//static by Khalid


    private OnFragmentInteractionListener mListener;

    public SearchResultsFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchResultsFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchResultsFrag newInstance(String param1, String param2) {
        SearchResultsFrag fragment = new SearchResultsFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAdapter = new OffersAdapter(offerList);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Offer");

        View rootView = inflater.inflate(R.layout.fragment_search_results, container, true);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);//Created by Khalid

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //prepareOffers(); //STUB

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_results, container, false);
    }

    //Stub Method
//    public void prepareOffers(){
//
//        for(int i = 0; i< 100; i++) {
//            switch (i%4) {
//                case 0:
//                    offerList.add(new Offer("Best of the best" + i, "Ajuah", "Riyadh", 39.99 + i, 4.5));
//                    break;
//                case 1:
//                    offerList.add(new Offer("Not for sale .. lolol" + i, "Sukkari", "Riyadh", 39.99 + i, 4.5));
//                    break;
//                case 2:
//                    offerList.add(new Offer("You want tamr? come and get it ... if you can" + i, "Khulass", "Riyadh", 39.99 + i, 4.5));
//                    break;
//                case 3:
//                    offerList.add(new Offer("Just filling space to check if every thing works smoothly ... whats up guys?" + i, "MEOWWWWW", "Riyadh", 39.99 + i, 4.5));
//                    break;
//            }
//        }
//        mAdapter.notifyDataSetChanged();
//    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static void firebaseOfferSearch(){
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
