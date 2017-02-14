package com.example.dikshant.tutorial1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // main page
    TextView mainMessageText;
    Button picture;
    Button myData;
    Button nutritionalDatabase;
    Button support;

    // clarification page
    TextView clarificationInstructions;
    Button changetextbtn2;
    Spinner foodSpinner;

    // results page
    Button finalResultButton;
    Button return_to_main;
    TextView results;

    // my data page
    TextView dataText;

    //private ClarifaiClient client;

    //ADDED FOR  DATABASE FUNCTIONALITY
    Map<String, List<String>> db; // IMPORTANT MEMBER : database

    List<String> keys; // IMPORTANT MEMBER : keys to database

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        introScreen();
    }

    private void introScreen() {
        setContentView(R.layout.activity_main);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.potato_list, android.R.layout.simple_spinner_item);

        try{
            readCSVToMap("ABBREV_2.txt");
            Log.d("Food Databse", "created");
        }
        catch (Exception e){
            Log.d("Food Database", "CSV not read into database");
        }

        mainMessageText = (TextView) findViewById(R.id.message_1);

        picture = (Button) findViewById(R.id.button_1);
        picture.setText("Select an Image");
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMessageText.setText(getString(R.string.user_message));
                userClarification(adapter);
            }
        });

        myData = (Button) findViewById(R.id.my_data);
        myData.setText("My Data");
        myData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // to be implemented
            }
        });

        nutritionalDatabase = (Button) findViewById(R.id.nutritional_database);
        nutritionalDatabase.setText("Nutritional" + "\n" + "Database");
        nutritionalDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // to be implemented
            }
        });

        support = (Button) findViewById(R.id.support_faqs);
        support.setText("Support & FAQs");
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // to be implemented
            }
        });
    }

    private void userClarification(ArrayAdapter<CharSequence> adapter) {
        setContentView(R.layout.user_clarification);

        finalResultButton = (Button) findViewById(R.id.finalResult);
        finalResultButton.setText("Show Final Result");

        foodSpinner = (Spinner) findViewById(R.id.spinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodSpinner.setAdapter(adapter);

        //final String selectedFood = foodSpinner.getSelectedItem().toString();
        //Log.d("Spinner", selectedFood);

        foodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //Object item = parent.getItemAtPosition(pos);
                final String selectedFood = foodSpinner.getItemAtPosition(pos).toString();
                Log.d("Spinner", selectedFood);

                finalResultButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.final_result);
                        results = (TextView) findViewById(R.id.results_text);
                        results.setText(selectedFood);
                        Log.d("Flow", "Final Results Page");

                        return_to_main = (Button) findViewById(R.id.return_to_start_button);
                        return_to_main.setText("Return to Start Page");
                        return_to_main.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setContentView(R.layout.activity_main);
                                introScreen();
                            }
                        });
                    }
                });
            }
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Spinner", "nothing selected");
            }
        });

        clarificationInstructions = (TextView) findViewById(R.id.message_2);
        changetextbtn2 = (Button) findViewById(R.id.button_2);
        changetextbtn2.setText("Select Potato");

        changetextbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clarificationInstructions.setText("Use the Drop-Down Menu");
            }
        });
    }

    //ADDED FUNCTION FOR DATABASE
    protected void readCSVToMap(String filename) throws Exception{ // IMPORTANT FUNCTION
        db = new HashMap<String, List<String>>();
        keys = new ArrayList<String>();
        InputStream is = getAssets().open("ABBREV_2.txt");
        //File f = new File(path.toURI());
        //File f = new File(path.getFile());
        BufferedReader in = new BufferedReader(new InputStreamReader(is));//new BufferedReader(new FileReader("ABBREV_2.txt"));
        String line = "";
        while ((line = in.readLine()) != null) {
            String parts[] = line.split("\t");
            List<String> nutrition = new ArrayList();
            for (int i = 1; i < parts.length; i++){
                nutrition.add(parts[i]);
            }
            db.put(parts[0], nutrition);
            keys.add(parts[0]);
        }
        in.close();

    }

    protected List<String> listOfKeys(String food){ // IMPORTANT FUNCTION
        food = food.toLowerCase();
        List<String> trueKeys = new ArrayList<String>();

        for (String key: keys){ // may want to pass in keys as parameter?
            if (key.toLowerCase().contains(food)){
                trueKeys.add(key);
            }
        }

        return trueKeys;
    }
}
