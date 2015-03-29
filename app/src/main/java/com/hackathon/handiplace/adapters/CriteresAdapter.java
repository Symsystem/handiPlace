package com.hackathon.handiplace.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hackathon.handiplace.R;
import com.hackathon.handiplace.classes.Criterion;

import java.util.ArrayList;

/**
 * Created by sym on 29/03/15.
 */
public class CriteresAdapter extends ArrayAdapter<Criterion> {

    private Context context;
    private ArrayList<Criterion> criterions;

    public CriteresAdapter(Context context, ArrayList<Criterion> criterions) {
        super(context, R.layout.critere_unit, criterions);
        this.context = context;
        this.criterions = criterions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.critere_unit, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.critere_label);
            holder.like = (ImageButton) convertView.findViewById(R.id.pouce_vert);
            holder.dislike = (ImageButton) convertView.findViewById(R.id.pouce_rouge);

            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Criterion criterion = criterions.get(position);

        holder.name.setText(criterion.getName());

        return convertView;

    }

    public void changeCriterions(ArrayList<Criterion> crit){
        criterions = crit;
    }

    private static class ViewHolder{
        TextView name;
        ImageButton like;
        ImageButton dislike;
        boolean gooOrBad;
    }
}
