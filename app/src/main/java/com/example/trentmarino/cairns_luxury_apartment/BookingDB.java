package com.example.trentmarino.cairns_luxury_apartment;

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
        super(context,"BookingData", null, 1);
    }
public void insertCursor(String checkIn, String checkOut){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("check_in", checkIn);
    values.put("check_out", checkOut);
    db.insert("tmp_booking", null, values);
    db.close();
    Log.i("insertCursor", "" + getReadableDatabase().rawQuery("SELECT * FROM tmp_booking;", null));
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
        db.execSQL("CREATE TABLE tmp_booking (_id INTERGER PRIMARY KEY, check_in DATE, check_out DATE);");
    }

    public Cursor getAllCursor() {
        return getReadableDatabase().rawQuery("SELECT * FROM tmp_booking;", null);
    }
}
