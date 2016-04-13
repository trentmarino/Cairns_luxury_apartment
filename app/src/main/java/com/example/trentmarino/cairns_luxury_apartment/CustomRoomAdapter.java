package com.example.trentmarino.cairns_luxury_apartment;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Handler;


public class CustomRoomAdapter extends ArrayAdapter<String> {

    Activity context;
    String[] itemname;
    private Handler handler;
    public CustomRoomAdapter(Activity context, String[] itemname) {
        super(context, R.layout.room_item_view, itemname);
        this.context=context;
        this.itemname=itemname;


    }

    public View getView(int position,View view,ViewGroup parent) {


        Log.i("split",itemname[position]);
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.room_item_view, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.room_name);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.room_thumb);
            txtTitle.setText(itemname[position]);
            return rowView;

    };
}