package com.ivansurguchev.onetwotest.mvp;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ivansurguchev.onetwotest.HotelSorter;
import com.ivansurguchev.onetwotest.entity.Hotel;
import com.ivansurguchev.onetwotest.entity.HotelGroup;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HotelsModel implements HotelsProtocol.IModel{

    private final String FILE_NAME = "hotels.json";
    private HotelsProtocol.ModelCallback callback;
    private Context context;
    private List<Hotel> hotels = new ArrayList<>();
    private List<HotelGroup> hotelGroups = new ArrayList<>();

    HotelsModel(Context context, HotelsProtocol.ModelCallback callback) {
        this.callback = callback;
        this.context = context;
    }

    /**
     * Get list of hotels
     */
    @Override
    public void getHotels() {
        if (hotels.isEmpty()) {
            DataGetTask dataGetTask = new DataGetTask();
            dataGetTask.execute();
        } else {
            callback.onGetHotels(hotels);
        }
    }

    /**
     * Get list of hotel groups, depending on hotel price
     */
    @Override
    public void getGroups() {
        if (hotels.isEmpty()) {
            getHotels();
        }

        if (hotelGroups.isEmpty()) {
            HotelGroupTask hotelGroupTask = new HotelGroupTask();
            hotelGroupTask.execute();
        } else {
            callback.onGetGroups(hotelGroups);
        }
    }


    private class DataGetTask extends AsyncTask<Object, Object, List<Hotel>> {

        @Override
        protected List<Hotel> doInBackground(Object[] params) {
            String json;
            try {
                InputStream is = context.getAssets().open(FILE_NAME);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }

            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<Hotel>>(){}.getType();
            hotels = gson.fromJson(json, collectionType);
            return hotels;
        }

        @Override
        protected void onPostExecute(List<Hotel> hotels) {
            super.onPostExecute(hotels);
            callback.onGetHotels(hotels);
        }
    }

    private class HotelGroupTask extends  AsyncTask<Object, Object, List<HotelGroup>> {

        @Override
        protected List<HotelGroup> doInBackground(Object[] params) {
            hotelGroups = HotelSorter.sortToGroups(hotels);
            return hotelGroups;
        }

        @Override
        protected void onPostExecute(List<HotelGroup> groups) {
            super.onPostExecute(groups);
            callback.onGetGroups(groups);
        }
    }

}
