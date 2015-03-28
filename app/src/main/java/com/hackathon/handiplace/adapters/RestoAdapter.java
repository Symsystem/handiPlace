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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.resto_list_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.resto_name);
            holder.category = (TextView) convertView.findViewById(R.id.resto_cat);
            holder.rating = (IconTextView) convertView.findViewById(R.id.resto_rating);
            convertView.setTag(holder);

        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Restaurant restaurant = restaurants.get(position);

        String nameStr = bor.getName();
        double mo = (Math.round(bor.getMoney() * 100.0) / 100.0);
        String moneyStr = mo + LoanReminderApplication.currency;

        holder.name.setText(nameStr);
        holder.category.setText(moneyStr);

        return convertView;

    }

    private static class ViewHolder {

        TextView name;
        TextView category;
        IconTextView rating;

    }
}
