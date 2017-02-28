package com.example.dikshant.tutorial1;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Dikshant on 2/18/2017.
 */

public class resultsPage extends MainActivity {

    // results page
    Button return_to_main;
    TextView results;

    String selectedFood;
    String carbContent;

    boolean foodFlag;
    boolean carbFlag;

    DBHandler db;

    public resultsPage(){

        Log.d("results page", "database try");

        db = super.userInfo;
    }

    public resultsPage(String food, String carbs){

        db = super.userInfo;

        setFood(food);
        setCarbs(carbs);

        super.returnToMainButton();
    }

    public void setFood(String food){
        selectedFood = food;
        foodFlag = true;
    }
    public  void setCarbs(String carbs){
        carbContent = carbs;
        carbFlag = true;
    }

    public void showResultsPage() {

        Log.d("results page", "update db");

        if (foodFlag & carbFlag){
            //updateDB();
        }

        Log.d("results page", "change view");
        //super.setContentView(R.layout.final_result);

        //setContentView(R.layout.final_result);

        Log.d("results page", "return_to_main try");
        return_to_main = (Button) findViewById(R.id.return_to_start_button);
        return_to_main.setText("Return to Start Page");
        return_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                resultsPage.super.introScreen();
            }
        });

        Log.d("results page", "results_text try");

        results = (TextView) findViewById(R.id.results_text);
        results.setText(selectedFood);
        Log.d("Flow", "Final Results Page");
    }

    public void updateDB(){
        Calendar cal = Calendar.getInstance();
        String date = String.valueOf(cal.DATE) + "_" + String.valueOf(cal.HOUR_OF_DAY)
                + "_" + String.valueOf(cal.MINUTE) + "_" + String.valueOf(cal.SECOND);
        db.addEntry(selectedFood, carbContent, date);
    }

}
