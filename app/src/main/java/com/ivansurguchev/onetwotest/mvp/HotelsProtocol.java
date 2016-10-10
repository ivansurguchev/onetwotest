package com.ivansurguchev.onetwotest.mvp;

import com.ivansurguchev.onetwotest.entity.Hotel;
import com.ivansurguchev.onetwotest.entity.HotelGroup;

import java.util.List;

/**
 * Created by ivansurguchev on 07.10.16.
 */

public class HotelsProtocol {

    public interface IView {
        void showProgress();
        void hideProgress();
        void showHotels(List<Hotel> hotels);
        void setGroups(List<HotelGroup> groups, boolean[] filterState);
        void setFilterState(boolean[] filterState);
    }

    public interface IPresenter {
        void getHotels();
        void setGroupFilter(int position, boolean isChecked);
        void clearFilter();
        void dispose();
    }

    public interface IModel {
        void getHotels();
        void getGroups();
    }

    public interface ModelCallback {
        void onGetHotels(List<Hotel> hotels);
        void onGetGroups(List<HotelGroup> groups);
    }
}
