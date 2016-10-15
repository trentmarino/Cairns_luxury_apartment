package com.example.trentmarino.cairns_luxury_apartment.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nick on 09-Apr-16.
 */
public class BookingDB extends SQLiteOpenHelper{
    public BookingDB(Context context){
        super(context, "BookingData", null, 4);
    }
public void insertCursor(String price, String checkIn, String checkOut,String guests,String room){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    Calendar c = Calendar.getInstance();
    int month = c.get(c.MONTH) + 1;
    String Date = c.get(c.DAY_OF_MONTH) + "/" + month + "/" + c.get(c.YEAR);
    Log.i("date",Date);
    values.put("bookingDate", Date);
    values.put("check_in", checkIn);
    values.put("check_out", checkOut);
    values.put("no_of_guests", guests);
    values.put("pricePaid", "$"+price);
    values.put("room", room);
    db.insert("tmp_booking", null, values);
    db.close();

}
    @Override
    public void onCreate(SQLiteDatabase db) {
        setup(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        setup(db);
    }

    public void setup(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS tmp_booking;");
        db.execSQL("CREATE TABLE tmp_booking (_id INTEGER PRIMARY KEY, bookingDate DATE, check_in DATE, check_out DATE,no_of_guests TEXT, pricePaid TEXT, room TEXT);");
    }

    public Cursor getAllCursor() {
        Log.i("returned", getReadableDatabase().rawQuery("SELECT * FROM tmp_booking;", null).toString());
        return getReadableDatabase().rawQuery("SELECT * FROM tmp_booking;", null);
    }
    public boolean updateBooking(String id, String checkIn, String checkOut,String guests){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id", id);
        values.put("check_in", checkIn);
        values.put("check_out", checkOut);
        values.put("no_of_guests", guests);
        db.update("tmp_booking",values, "_id = ?", new String[] {id});

        return true;
    }
}
