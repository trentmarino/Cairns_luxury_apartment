package com.example.trentmarino.cairns_luxury_apartment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.trentmarino.cairns_luxury_apartment.DAO.BookingDB;

public class history extends AppCompatActivity {

    private BookingDB bookingDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Booking History");
        bookingDB = new BookingDB(this);
        Cursor cursor = bookingDB.getAllCursor();
        ListView listView = (ListView) findViewById(R.id.listView);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.item_view, cursor,
                new String[]{"bookingDate","check_in", "check_out", "no_of_guests","pricePaid","room"},
                new int[]{R.id.date,R.id.CheckIn, R.id.CheckOut, R.id.noGuests,R.id.pricePaid, R.id.room},
                0);
        listView.setAdapter(adapter);
    }

}
