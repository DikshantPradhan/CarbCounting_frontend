package com.example.dikshant.tutorial1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Dikshant on 2/5/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DB_VERSION = 1;

    // Database Name
    private static final String DB_NAME = "userInfo_";

    // Table Name
    private static final String USER_TABLE = "userHist_";

    // Table Column Names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_FOOD = "food";
    private static final String KEY_CARBS = "carbs";

    // table name
    private static final String TABLE_NAME = "nutritionInfo";

    // columns: Shrt_Desc	Carbohydrt_(g)	Water_(g)	Energ_Kcal	Protein_(g)	Fiber_TD_(g)	Sugar_Tot_(g)	Cholestrl_(mg)	GmWt_1	GmWt_Desc2
    private static String DESC = "Shrt_Desc";
    private static String CARB = "Carbohydrt_g";
    private static String WATER = "Water_g";
    private static String ENERGY = "Energ_Kcal";
    private static String PROTEIN = "Protein_g";
    private static String FIBER = "Fiber_TD_g";
    private static String SUGAR = "Sugar_Tot_g";
    private static String CHOL = "Cholestrl_mg";
    private static String WT = "GmWt_1";
    private static String WT_DESC = "GmWt_Desc2";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DB", "oncreating");
        String CREATE_TABLE =  "CREATE TABLE " + USER_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT," + KEY_FOOD + " TEXT," + KEY_CARBS + " TEXT" + ")";

        Log.d("DB", "creating ndb");
        String CREATE_TABLE_2 =  "CREATE TABLE " + TABLE_NAME + "(" + DESC + " TEXT," + CARB + " TEXT,"
                + WATER + " TEXT," + ENERGY + " TEXT," + PROTEIN + " TEXT," + FIBER + " TEXT," +
                SUGAR + " TEXT," + CHOL + " TEXT," + WT + " TEXT," + WT_DESC + " TEXT" + ")";

        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
// Creating tables again
        onCreate(db);
    }

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
        db.insertWithOnConflict(USER_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();

        //Cursor cs = db.query(USER_TABLE, new String[]{})
        String countQuery = "SELECT * FROM "  + USER_TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();

        //return 0;
    }

    public void clear(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ USER_TABLE);
    }

    // need reading functions and export and others???
}
