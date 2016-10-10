package com.ivansurguchev.onetwotest.mvp;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ivansurguchev.onetwotest.FilterAdapter;
import com.ivansurguchev.onetwotest.HotelAdapter;
import com.ivansurguchev.onetwotest.R;
import com.ivansurguchev.onetwotest.entity.Hotel;
import com.ivansurguchev.onetwotest.entity.HotelGroup;

import java.util.List;

public class HotelsActivity extends AppCompatActivity implements HotelsProtocol.IView, View.OnClickListener{

    private HotelsProtocol.IPresenter presenter;

    private RecyclerView rvHotels;
    private RecyclerView rvFilters;
    private ProgressBar pbLoading;
    private TextView tvNoHotels;
    private TextView tvClearFilters;
    private LinearLayout llFilters;
    private FrameLayout flFiltersHeader;

    private HotelAdapter hotelAdapter = null;
    private FilterAdapter filterAdapter = null;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvHotels = (RecyclerView) findViewById(R.id.rvHotels);
        rvFilters = (RecyclerView) findViewById(R.id.rvFilters);
        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
        tvNoHotels = (TextView) findViewById(R.id.tvNoHotels);
        tvClearFilters = (TextView) findViewById(R.id.tvClearFilters);
        llFilters = (LinearLayout) findViewById(R.id.llFilters);
        flFiltersHeader = (FrameLayout) findViewById(R.id.flFiltersHeader);

        rvHotels.setLayoutManager(new LinearLayoutManager(this));
        rvFilters.setLayoutManager(new LinearLayoutManager(this));

        bottomSheetBehavior = BottomSheetBehavior.from(llFilters);
        flFiltersHeader.setOnClickListener(this);
        tvClearFilters.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new HotelsPresenter(this);
        presenter.getHotels();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.dispose();
    }

    @Override
    public void showProgress() {
        rvHotels.setVisibility(View.GONE);
        tvNoHotels.setVisibility(View.GONE);
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void showHotels(List<Hotel> hotels) {
        hideProgress();
        if (hotels.isEmpty()) {
            rvHotels.setVisibility(View.GONE);
            tvNoHotels.setVisibility(View.VISIBLE);
        } else {
            tvNoHotels.setVisibility(View.GONE);
            hotelAdapter = new HotelAdapter(hotels, this);
            rvHotels.setAdapter(hotelAdapter);
            rvHotels.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setGroups(List<HotelGroup> groups, boolean[] filterState) {
        filterAdapter = new FilterAdapter(groups, filterState, this, presenter);
        rvFilters.setAdapter(filterAdapter);
    }

    @Override
    public void setFilterState(boolean[] filterState) {
        if(filterAdapter == null) {
            return;
        }
        filterAdapter.setFilterState(filterState);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.flFiltersHeader:
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;

            case R.id.tvClearFilters:
                presenter.clearFilter();
                break;
        }
    }
}
