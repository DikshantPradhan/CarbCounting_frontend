package com.example.dikshant.tutorial1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dikshant on 2/5/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DB_VERSION = 1;

    // Database Name
    private static final String DB_NAME = "userInfo";

    // Table Name
    private static final String USER_TABLE = "userHist";

    // Table Column Names
    private static final String KEY_DATE = "date";
    private static final String KEY_FOOD = "food";
    private static final String KEY_CARBS = "carbs";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =  "CREATE TABLE " + USER_TABLE + "(" + KEY_DATE + " INTEGER PRIMARY KEY," + KEY_FOOD + " TEXT," + KEY_CARBS + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
// Creating tables again
        onCreate(db);
    }

    public void addEntry(String date, String food, String carbs){
        SQLiteDatabase db = this.getWritableDatabase();

        // value creation
        ContentValues values = new ContentValues();

        values.put(KEY_DATE, date);
        values.put(KEY_FOOD, food);
        values.put(KEY_CARBS, carbs);

        // db insertion
        db.insert(USER_TABLE, null, values);
        db.close();
    }

    // need reading functions and export and others???
}
