package com.example.dikshant.tutorial1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

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
    private static final String KEY_MEAL = "meal";

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

    public void addEntry(String food, String carbs, String date, String meal){
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
        values.put(KEY_MEAL, meal);

        // db insertion
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void addEntry(nutrMeal meal, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("DB", "found writable");

        //Calendar cal = Calendar.getInstance();
        //int date = cal.DATE;
        //String date = "2";

        // value creation
        ContentValues values = new ContentValues();

        //values.put(KEY_DATE, date);
        //values.put(KEY_FOOD, food);
        //values.put(KEY_CARBS, carbs);
        //values.put(KEY_MEAL, meal);
        List<String> foods = meal.getFoods();
        List<Double> carbFactors = meal.getCarbFactors();
        List<Double> volumes = meal.getVolumes();

        int length = foods.size();

        for (int i = 0; i < length; i++){
            values.put(KEY_DATE, date);
            values.put(KEY_FOOD, foods.get(i));
            values.put(KEY_CARBS, carbFactors.get(i)*volumes.get(i));

            // db insertion
            db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }

        // db insertion
        //db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
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

    public Cursor queryContainingFood(String word){
        Log.d("nDB", "querying");

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_DATE, KEY_FOOD, KEY_CARBS},
                KEY_FOOD + " LIKE ?", new String[] {"%" + word + "%"},
                null, null, null);

        Log.d("nDB", String.valueOf(cursor.getCount()));

        //Log.d("nDB query", cursor.getString(cursor.getColumnIndex(DESC)));

        return cursor;
    }

    public Cursor queryCarb(String word){
        Log.d("nDB", "querying");

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_DATE, KEY_FOOD, KEY_CARBS},
                KEY_CARBS + " LIKE ?", new String[] {"%" + word + "%"},
                null, null, null);

        Log.d("nDB", String.valueOf(cursor.getCount()));

        //Log.d("nDB query", cursor.getString(cursor.getColumnIndex(DESC)));

        return cursor;
    }

    public Cursor queryDate(String word){ // need to change this one
        Log.d("nDB", "querying");

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_DATE, KEY_FOOD, KEY_CARBS},
                "*", new String[] {"%" + word + "%"},
                null, null, null);

        Log.d("nDB", String.valueOf(cursor.getCount()));

        //Log.d("nDB query", cursor.getString(cursor.getColumnIndex(DESC)));

        return cursor;
    }

    // need reading functions and export and others???
}
