package com.hackathon.handiplace;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class RestoAdapter extends ArrayAdapter<Restaurant> {

    private Context context;
    private List<Restaurant> restaurants;

    public RestoAdapter(Context context, List<Restaurant> restaurants) {
        super(context, R.layout.resto_list_item, restaurants);
        this.context = context;
        this.restaurants = restaurants;
    }

}
