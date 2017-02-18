package com.example.dikshant.tutorial1;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        db = super.userInfo;

        return_to_main = (Button) findViewById(R.id.return_to_start_button);
        return_to_main.setText("Return to Start Page");
        return_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                resultsPage.super.introScreen();
            }
        });
    }

    public resultsPage(String food, String carbs){

        db = super.userInfo;

        setFood(food);
        setCards(carbs);

        super.returnToMainButton();
    }

    public void setFood(String food){
        selectedFood = food;
        foodFlag = true;
    }
    public  void setCards(String carbs){
        carbContent = carbs;
        carbFlag = true;
    }

    public void showResultsPage() {

        if (foodFlag & carbFlag){
            updateDB();
        }

        setContentView(R.layout.final_result);
        results = (TextView) findViewById(R.id.results_text);
        results.setText(selectedFood);
        Log.d("Flow", "Final Results Page");
    }

    public void updateDB(){
        db.addEntry(selectedFood, carbContent);
    }

}
