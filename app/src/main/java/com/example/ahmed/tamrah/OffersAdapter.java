package com.example.ahmed.tamrah;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.MyViewHolder> {

    private List<Offer> offerList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, type, city, price, rate;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.OfferTitle);
            type = (TextView) view.findViewById(R.id.TamrahType);
            city = (TextView) view.findViewById(R.id.city);
            price = (TextView) view.findViewById(R.id.OfferPrice);
            rate = (TextView) view.findViewById(R.id.Rating);

        }
    }




    public OffersAdapter(List<Offer> offersList) {
        this.offerList = offersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_format, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Offer offer = offerList.get(position);
        holder.title.setText(offer.getTitle());
        holder.type.setText(offer.getType());
        holder.city.setText(offer.getCity());
        holder.price.setText(new DecimalFormat(".##").format(offer.getPrice()) + " S.R.");
        holder.rate.setText(new DecimalFormat(".##").format(offer.getRate()));

    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }
}