package com.example.trentmarino.cairns_luxury_apartment.RoomPage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.trentmarino.cairns_luxury_apartment.NavagationSingleTon;
import com.example.trentmarino.cairns_luxury_apartment.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class SelectedRoom extends AppCompatActivity {


    String propertyID;
    String roomSelected;
    int numberOfRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        propertyID = NavagationSingleTon.getInstance().getPropertyLocationID();
        roomSelected = NavagationSingleTon.getInstance().getRoomName();
        numberOfRooms = NavagationSingleTon.getInstance().getNumberOfRooms();
        Log.i("Passed String", roomSelected);
        Log.i("paseed Number", propertyID);
        Log.i("number of rooms", "" + numberOfRooms);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
        new RoomPageAsyncTask(this).execute("http://cla-cms.me/get_page_info.php");
    }

}
