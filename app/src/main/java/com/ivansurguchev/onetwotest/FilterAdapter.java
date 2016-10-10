package com.ivansurguchev.onetwotest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ivansurguchev.onetwotest.entity.HotelGroup;
import com.ivansurguchev.onetwotest.mvp.HotelsProtocol;

import java.util.List;

/**
 * Created by ivansurguchev on 09.10.16.
 */

public class FilterAdapter extends RecyclerView.Adapter {
    private List<HotelGroup> hotelGroups;
    private Context context;
    private HotelsProtocol.IPresenter presenter;
    private boolean[] filterState = new boolean[Common.MAX_GROUPS_COUNT];

    public FilterAdapter(List<HotelGroup> hotelGroups, boolean[] filterState,
                         Context context, HotelsProtocol.IPresenter presenter) {
        this.hotelGroups = hotelGroups;
        this.context = context.getApplicationContext();
        this.presenter = presenter;
        this.filterState = filterState;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_filter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final HotelGroup group = hotelGroups.get(position);
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.prices.setText(String.format(context.getResources().getString(R.string.price_filter_range),
                group.minPrice, group.maxPrice, group.hotels.size()));

        viewHolder.checkBox.setOnCheckedChangeListener(null);
        viewHolder.checkBox.setChecked(filterState[position]);

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.setGroupFilter(position, isChecked);
            }
        });

        viewHolder.prices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.checkBox.setChecked(!viewHolder.checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotelGroups.size();
    }

    public void setFilterState(boolean[] filterState) {
        this.filterState = filterState;
        notifyDataSetChanged();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView prices;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.cbFilter);
            prices = (TextView) itemView.findViewById(R.id.tvHotelPrices);
        }
    }
}
