package com.example.trentmarino.cairns_luxury_apartment.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Nick on 09-Apr-16.
 */
public class BookingDB extends SQLiteOpenHelper{

    public BookingDB(Context context){
        super(context, "BookingData", null, 4);
    }
public void insertCursor(String checkIn, String checkOut){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("check_in", checkIn);
    values.put("check_out", checkOut);
    db.insert("tmp_booking", null, values);
    db.close();
    Log.i("insertCursor", "" + getReadableDatabase().rawQuery("SELECT * FROM tmp_booking; ", null));

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
        db.execSQL("CREATE TABLE tmp_booking (_id INTEGER PRIMARY KEY, check_in DATE, check_out DATE,no_of_guests TEXT);");
        db.execSQL("INSERT INTO tmp_booking (_id,check_in,check_out,no_of_guests) VALUES (1,1/1/2016,2/1/2016,1);");
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
