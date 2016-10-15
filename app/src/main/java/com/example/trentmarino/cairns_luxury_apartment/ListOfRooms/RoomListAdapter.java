package com.example.trentmarino.cairns_luxury_apartment.ListOfRooms;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trentmarino.cairns_luxury_apartment.NavagationSingleTon;
import com.example.trentmarino.cairns_luxury_apartment.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.logging.Handler;


public class RoomListAdapter extends ArrayAdapter<String> {

    Activity context;
    ArrayList<String> itemname;
    ArrayList<String> minPrice = new ArrayList<>();
    ArrayList<String> imgUrl = new ArrayList<>();
    ArrayList<String> desc = new ArrayList<>();
    private Handler handler;
    public RoomListAdapter(Activity context, ArrayList<String> itemname, ArrayList<String> prices, ArrayList<String> url, ArrayList<String> desc) {
        super(context, R.layout.room_item_view, itemname);
        this.context=context;
        this.itemname=itemname;
        this.minPrice = prices;
        this.imgUrl = url;
        this.desc = desc;


    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.room_item_view, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.room_name);
        TextView price = (TextView) rowView.findViewById(R.id.Price);
        TextView descText = (TextView) rowView.findViewById(R.id.room_descript);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.room_thumb);
        if(itemname.get(position).matches("")){
            txtTitle.setText("No Avalible Rooms");
            return rowView;
        }else{
            ImageLoader.getInstance().displayImage(imgUrl.get(position), imageView);
            txtTitle.setText(itemname.get(position));
            descText.setText(desc.get(position));
            price.setText("$"+minPrice.get(position));
            return rowView;
        }





    };
}