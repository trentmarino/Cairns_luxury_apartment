package com.example.trentmarino.cairns_luxury_apartment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by trentmarino on 13/04/16.
 */
public class CustomRoomAdapter extends BaseAdapter {
    public Context ctx;
    public CustomRoomAdapter(Context context){
        this.ctx = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        View row = convertView;
//        if(row == null)
//        {
//            LayoutInflater inflater = ctx.getLayoutInflater();
//            row = inflater.inflate(R.layout.room_item_view, parent, false);
//
//
//            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
//            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
//
//
//        }
//        else
//        {
//            holder = (WeatherHolder)row.getTag();
//        }

        return null;
    }
}
