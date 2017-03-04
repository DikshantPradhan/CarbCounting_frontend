package com.example.dikshant.tutorial1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dikshant on 2/5/2017.
 */

public class userDB extends DBHandler {

    private static final String TABLE_NAME = "userHist_";
    // Table Column Names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_FOOD = "food";
    private static final String KEY_CARBS = "carbs";

    public userDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //@Override
    public void addEntry(String food, String carbs, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("DB", "found writable");

        //Calendar cal = Calendar.getInstance();
        //int date = cal.DATE;
        //String date = "2";

        // value creation
        ContentValues values = new ContentValues();

        values.put(KEY_DATE, date);
        values.put(KEY_FOOD, food);
        values.put(KEY_CARBS, carbs);

        // db insertion
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    @Override
    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();

        //Cursor cs = db.query(USER_TABLE, new String[]{})
        String countQuery = "SELECT * FROM "  + TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();

        //return 0;
    }

    @Override
    public void clear(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }

    // need reading functions and export and others???
}
