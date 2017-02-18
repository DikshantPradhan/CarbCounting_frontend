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

    DBHandler db;

    public resultsPage(){
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

    public void setFood(String food){
        selectedFood = food;
    }

    public void showResultsPage() {
        setContentView(R.layout.final_result);
        results = (TextView) findViewById(R.id.results_text);
        results.setText(selectedFood);
        Log.d("Flow", "Final Results Page");
    }

    public void updateDB(){
        //db.addEntry();
    }

}
