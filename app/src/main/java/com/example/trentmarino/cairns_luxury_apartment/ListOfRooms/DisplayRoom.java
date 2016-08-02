package com.example.trentmarino.cairns_luxury_apartment.ListOfRooms;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.trentmarino.cairns_luxury_apartment.DAO.BookingDB;
import com.example.trentmarino.cairns_luxury_apartment.NavagationSingleTon;
import com.example.trentmarino.cairns_luxury_apartment.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class DisplayRoom extends AppCompatActivity {
    private MenuItem item;
    String propertyID;
    String roomSelected;
    TextView testRoomSelected, display;
    private BookingDB bookingDB;
    ArrayList<String> retrievedRoom;
    RoomListAdapter roomListAdapter;
    ListView roomListView;
    String priceOrder = "defaultPriceOrder";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        testRoomSelected = (TextView) findViewById(R.id.textView3);
        testRoomSelected.setText(NavagationSingleTon.getInstance().getPropertyLocationName());
//        new JSONTask().execute(" https://cla-cms.herokuapp.com/get_property_info_based_off_selected.php");

            new RoomListAsyncTask(this).execute("http://cla-cms.me/get_property_info_based_off_selected.php");


        bookingDB = new BookingDB(this);
        Cursor cursor = bookingDB.getAllCursor();
        Log.i("returned ", cursor.toString());
        ListView listView = (ListView) findViewById(R.id.listView);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.item_view, cursor,
                new String[]{"check_in", "check_out", "no_of_guests"},
                new int[]{R.id.CheckIn, R.id.CheckOut, R.id.noGuests},
                0);
        listView.setAdapter(adapter);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if( id == R.id.home){
            return true;
        }
        else if(id == R.id.priceLowToHigh){
            Log.i("priceLow", "chyea boi");
            NavagationSingleTon.getInstance().setPriceOrder("low");
            Intent intent = getIntent();
            finish();
            startActivity(intent);

        } else if (id == R.id.priceHighToLow){
            Log.i("priceHigh", "chyea boi");
            NavagationSingleTon.getInstance().setPriceOrder("high");
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}