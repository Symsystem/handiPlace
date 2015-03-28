package com.hackathon.handiplace.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.IconTextView;
import android.widget.TextView;

import com.hackathon.handiplace.R;
import com.hackathon.handiplace.classes.Restaurant;

import java.util.List;

public class RestoAdapter extends ArrayAdapter<Restaurant> {

    private Context context;
    private List<Restaurant> restaurants;

    public RestoAdapter(Context context, List<Restaurant> restaurants) {
        super(context, R.layout.resto_list_item, restaurants);
        this.context = context;
        this.restaurants = restaurants;
    }

    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Override
    public Restaurant getItem(int position) {
        return (Restaurant)restaurants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.resto_list_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.resto_name);
            holder.category = (TextView) convertView.findViewById(R.id.resto_cat);
            holder.rating = (IconTextView) convertView.findViewById(R.id.resto_rating);
            holder.km = (TextView) convertView.findViewById(R.id.kms);
            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Restaurant restaurant = restaurants.get(position);

        holder.name.setText(restaurant.getName());
        holder.category.setText(restaurant.getCategory());
        holder.rating.setText(calculRate(position));
        holder.km.setText(restaurant.getKilometers()+ "km");

        return convertView;

    }

    private String calculRate(int position) {
        return "{fa-star}{fa-star}{fa-star}";
    }

    private static class ViewHolder {

        TextView name;
        TextView category;
        TextView km;
        IconTextView rating;

    }
}
