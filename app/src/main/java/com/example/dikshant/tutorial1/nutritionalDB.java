package com.example.dikshant.tutorial1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dikshant on 2/19/2017.
 */

public class nutritionalDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "USDA_Nutrition_DB";
    private static final int DB_VERSION = 1;

    // table name
    private static final String TABLE_NAME = "nutritionInfo";

    // columns: Shrt_Desc	Carbohydrt_(g)	Water_(g)	Energ_Kcal	Protein_(g)	Fiber_TD_(g)	Sugar_Tot_(g)	Cholestrl_(mg)	GmWt_1	GmWt_Desc2
    private static String DESC = "Shrt_Desc";
    private static String CARB = "Carbohydrt_(g)";
    private static String WATER = "Water_(g)";
    private static String ENERGY = "Energ_Kcal";
    private static String PROTEIN = "Protein_(g)";
    private static String FIBER = "Fiber_TD_(g)";
    private static String SUGAR = "Sugar_Tot_(g)";
    private static String CHOL = "Cholestrl_(mg)";
    private static String WT = "GmWt_1";
    private static String WT_DESC = "GmWt_Desc2";


    private static Context cxt;



    public nutritionalDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        cxt = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =  "CREATE TABLE " + TABLE_NAME + "(" + DESC + " TEXT," + CARB + " TEXT,"
                + WATER + " TEXT," + ENERGY + " TEXT," + PROTEIN + " TEXT," + FIBER + " TEXT," +
                SUGAR + " TEXT," + CHOL + " TEXT," + WT + " TEXT," + WT_DESC + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
        readCSV();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
// Creating tables again
        //onCreate(db);
    }

    private void readCSV(){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            InputStream is = cxt.getAssets().open("USDA_DB_ABBREV.txt");
            //File f = new File(path.toURI());
            //File f = new File(path.getFile());
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));//new BufferedReader(new FileReader("ABBREV_2.txt"));
            String line = "";
            String tableName = DB_NAME;
            String columns = "_id, name, dt1, dt2, dt3";
            String str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";
            String str2 = ");";
            while ((line = buffer.readLine()) != null) {
                String parts[] = line.split("\t");
                List<String> nutrition = new ArrayList();
                for (int i = 1; i < parts.length; i++){
                    nutrition.add(parts[i]);
                }
                db.beginTransaction();
                while ((line = buffer.readLine()) != null) {
                    StringBuilder sb = new StringBuilder(str1);
                    String[] str = line.split(" ");
                    sb.append("'" + str[0] + "',");
                    sb.append(str[1] + "',");
                    sb.append(str[2] + "',");
                    sb.append(str[3] + "'");
                    sb.append(str[4] + "'");
                    sb.append(str[5] + "'");
                    sb.append(str[6] + "'");
                    sb.append(str[7] + "'");
                    sb.append(str[8] + "'");
                    sb.append(str[9] + "'");
                    sb.append(str2);
                    db.execSQL(sb.toString());
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                //db.put(parts[0], nutrition);
                //keys.add(parts[0]);
            }
            buffer.close();
        }
        catch (Exception e){

        }
    }
}
