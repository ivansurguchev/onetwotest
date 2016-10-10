package com.ivansurguchev.onetwotest.mvp;

import android.content.Context;

import com.ivansurguchev.onetwotest.Common;
import com.ivansurguchev.onetwotest.entity.Hotel;
import com.ivansurguchev.onetwotest.entity.HotelGroup;

import java.util.ArrayList;
import java.util.List;


public class HotelsPresenter implements HotelsProtocol.IPresenter, HotelsProtocol.ModelCallback {

    private HotelsProtocol.IView view;
    private HotelsModel model;
    private Context context;
    private boolean[] filterState = new boolean[Common.MAX_GROUPS_COUNT];
    private List<HotelGroup> groups;

    public HotelsPresenter(HotelsActivity view) {
        this.view = view;
        this.context = view.getApplicationContext();
        this.model = new HotelsModel(this.context, this);
        for(int i = 0; i < filterState.length; i++) {
            filterState[i] = true;
        }
    }

    @Override
    public void getHotels() {
        view.showProgress();
        model.getHotels();
    }

    @Override
    public void setGroupFilter(int position, boolean isChecked) {
        filterState[position] = isChecked;
        view.setFilterState(filterState);
        view.showHotels(getFilteredHotelsList(groups, filterState));
    }

    @Override
    public void clearFilter() {
        for(int i = 0; i < filterState.length; i++) {
            filterState[i] = false;
        }
        view.setFilterState(filterState);
        view.showHotels(getFilteredHotelsList(groups, filterState));
    }

    @Override
    public void dispose() {
        view = null;
    }

    @Override
    public void onGetHotels(List<Hotel> hotels) {
        model.getGroups();
    }

    @Override
    public void onGetGroups(List<HotelGroup> groups) {
        this.groups = groups;
        if (view == null) {
            return;
        }
        view.setGroups(groups, filterState);
        view.showHotels(getFilteredHotelsList(groups, filterState));
    }


    private List<Hotel> getFilteredHotelsList(List<HotelGroup> groups, boolean[] filterState) {
        List<Hotel> h = new ArrayList<>();
        for (int i = 0; i < groups.size(); i++) {
            if(filterState[i]) {
                h.addAll(groups.get(i).hotels);
            }
        }
        return h;
    }
}
