package com.example.titos.movieguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by TITOS on 4/30/2016.
 */

public class Trialer_Adpter extends BaseAdapter{

    Context context ;
    ArrayList<Mytreiler> trailerList;
    public Trialer_Adpter(Context context ,ArrayList<Mytreiler> trailerList) {
        this.context = context;
        this.trailerList = trailerList;
    }

    @Override
    public int getCount() {
        return trailerList.size();
    }

    @Override
    public Object getItem(int position) {
        return trailerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.treliler_item, null);
        }
        TextView trialerName = (TextView)convertView.findViewById(R.id.Trialertext);
        trialerName.setText(trailerList.get(position).getName());

        return convertView;

    }
}
