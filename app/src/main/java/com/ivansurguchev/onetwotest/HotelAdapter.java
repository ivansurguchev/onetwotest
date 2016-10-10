package com.ivansurguchev.onetwotest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivansurguchev.onetwotest.entity.Hotel;

import java.util.List;

/**
 * Created by ivansurguchev on 09.10.16.
 */

public class HotelAdapter extends RecyclerView.Adapter {

    private List<Hotel> hotels;
    private Context context;

    public HotelAdapter(List<Hotel> hotels, Context context) {
        this.hotels = hotels;
        this.context = context.getApplicationContext();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hotel, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Hotel h = hotels.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.name.setText(h.name);
        viewHolder.price.setText(String.format(context.getResources().getString(R.string.price_currency), h.price));
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }


    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvHotelName);
            price = (TextView) itemView.findViewById(R.id.tvHotelPrice);
        }
    }
}
