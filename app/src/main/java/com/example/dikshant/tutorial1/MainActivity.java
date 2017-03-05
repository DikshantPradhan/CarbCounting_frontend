package com.example.dikshant.tutorial1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clarifai2.dto.prediction.Concept;

public class MainActivity extends AppCompatActivity {

    // general things
    userDB userInfo;
    clarifaiHandler clarifai;
    nutritionalDB nutrInfo;
    densityDB densityInfo;

    // main page
    TextView mainMessageText;
    Button picture;
    Button myData;
    Button nutritionalDatabase;
    Button support;

    public static final int PICK_IMAGE = 100;

    // picture selection page
    Button picSelection;
    Button postSelection;
    ImageView imageSelected;

    // clarification page
    TextView clarificationInstructions;
    Button changetextbtn2;
    Spinner foodSpinner;

    // results page
    Button finalResultButton;
    Button return_to_main;
    TextView results;

    Spinner nutritionalSpinner;
    Spinner densitySpinner;

    //resultsPage rPage;

    // my data page
    TextView dataText;
    Button monthly;
    Button weekly;
    Button daily;

    // ndb page
    Button ndbSearch;
    Button ndbCategories;

    //private ClarifaiClient client;

    //ADDED FOR  DATABASE FUNCTIONALITY
    Map<String, List<String>> db; // IMPORTANT MEMBER : database

    List<String> keys; // IMPORTANT MEMBER : keys to database

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        createDatabases();

        try {
            clarifai = new clarifaiHandler();
        }
        catch (Exception e){
            Log.d("failure", "Clarifai could not initialize; most likely no internet connection");
        }
        introScreen();
    }

    private void createDatabases() {


        userInfo = new userDB(getBaseContext());
        //Log.d("DB", String.valueOf(userInfo.getCount()));
        userInfo.clear();
        //Log.d("DB", String.valueOf(userInfo.getCount()));
        //userInfo.getCount();
        Calendar cal = Calendar.getInstance();
        String date = String.valueOf(cal.DATE) + "_" + String.valueOf(cal.HOUR_OF_DAY)
                + "_" + String.valueOf(cal.MINUTE) + "_" + String.valueOf(cal.SECOND);
        //userInfo.addEntry("potato", "30", date);
        //Log.d("DB", String.valueOf(userInfo.getCount()));
        nutrInfo = new nutritionalDB(getBaseContext());
        //nutrInfo.clear();
        densityInfo = new densityDB(getBaseContext());

        try {
            SQLiteDatabase test = nutrInfo.getReadableDatabase();
            Log.d("nDB", "got readable");
            if (nutrInfo.getCount() < 8000){
                nutrInfo.readCSV();
            }
            //Log.d("nDB", String.valueOf(nutrInfo.getCount()));
        } catch (Exception e) {
            Log.d("nDB", "DNE");
            // database doesn't exist yet.
        }

        try {
            SQLiteDatabase test = densityInfo.getReadableDatabase();
            Log.d("dDB", "got readable");
            if (densityInfo.getCount() < 1){
                densityInfo.readCSV();
            }
            //Log.d("dDB", String.valueOf(densityInfo.getCount()));
        } catch (Exception e) {
            Log.d("dDB", "DNE");
            // database doesn't exist yet.
        }

        //Cursor potatoes = densityInfo.queryContaining("potato");
        //Log.d("DBquery", potatoes.toString());

    }

    public void introScreen() {
        setContentView(R.layout.activity_main);
        //final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.potato_list, android.R.layout.simple_spinner_item);

        try{
            //readCSVToMap("ABBREV_2.txt");
            //Log.d("Food Database", "created");
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
                //startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"), PICK_IMAGE);
                imageSelection();
                //setContentView(R.layout.image_selection);
                //mainMessageText.setText(getString(R.string.user_message));
                //userClarification();
            }
        });

        myData = (Button) findViewById(R.id.my_data);
        myData.setText("My Data");
        myData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                myDataPage();
            }
        });

        nutritionalDatabase = (Button) findViewById(R.id.nutritional_database);
        nutritionalDatabase.setText("Nutritional" + "\n" + "Database");
        nutritionalDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                nutritionalDatabasePage();
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

    private void imageSelection() {
        setContentView(R.layout.image_selection);

        imageSelected = (ImageView) findViewById(R.id.image);

        picSelection = (Button) findViewById(R.id.select_image);
        picSelection.setText("Select from Gallery");
        picSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"), PICK_IMAGE);
            }
        }
        );

        postSelection = (Button) findViewById(R.id.post_image_selection);
        postSelection.setText("Proceed to Clarification");
        postSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClarification();
            }
        }
        );
    }

    private void nutritionalDatabasePage() {
        setContentView(R.layout.nutritional_database_page);

        ndbCategories = (Button) findViewById(R.id.ndb_categories);
        ndbSearch = (Button) findViewById(R.id.ndb_search);

        ndbCategories.setText("Categories");
        ndbSearch.setText("Search");
    }

    private void myDataPage() {
        setContentView(R.layout.my_data_page);

        monthly = (Button) findViewById(R.id.data_monthly);
        weekly = (Button) findViewById(R.id.data_weekly);
        daily = (Button) findViewById(R.id.data_daily);

        monthly.setText("Monthly Data");
        weekly.setText("Weekly Data");
        daily.setText("Daily Data");
    }

    private void userClarification() {

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.potato_list, android.R.layout.simple_spinner_item);

        setContentView(R.layout.user_clarification);

        List<Concept> potentialFoods;

        try {
            potentialFoods = clarifai.returnPredictions();
            Log.d("predictions", "went into try loop");
            if (clarifai.hasPredictions()){
                Log.d("predictions", "went into if statement");
                if (clarifai.predictionsList() == null){
                    Log.d("adapter", "null predictions");
                }
                String[] predictionArray = clarifai.predictionsArray();
                List<String> predictionsArrayList = clarifai.predictionsList();
                Log.d("adapter", String.valueOf(predictionArray.length));
                //adapter.clear();
                final ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, predictionArray);

                Log.d("predictions", "created new adapter");
            }
        }
        catch (Exception e){
            Log.d("predictions", "prediction probably failed");
        }

        finalResultButton = (Button) findViewById(R.id.finalResult);
        finalResultButton.setText("Show Final Result");

        foodSpinner = (Spinner) findViewById(R.id.spinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        String[] predictionArray = clarifai.predictionsArray();
        List<String> predictionsArrayList = clarifai.predictionsList();
        Log.d("adapter", String.valueOf(predictionArray.length));
        //adapter.clear();
        final ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, predictionArray);

        foodSpinner.setAdapter(adapter2);

        //final String selectedFood = foodSpinner.getSelectedItem().toString();
        //Log.d("Spinner", selectedFood);

        foodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //Object item = parent.getItemAtPosition(pos);
                final String selectedFood = foodSpinner.getItemAtPosition(pos).toString();
                Log.d("Spinner", selectedFood);
                //Log.d("results page", "trying to create rpage");
                //final resultsPage rpage = new resultsPage();

                finalResultButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //rpage.setCarbs("30");
                        //rpage.setFood(selectedFood);

                        Log.d("results page", "created rpage");
                        //setContentView(R.layout.final_result);
                        //rpage.showResultsPage();
                        resultsPage(selectedFood);
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

    private void resultsPage(String selectedFood) {
        setContentView(R.layout.final_result);
        results = (TextView) findViewById(R.id.results_text);
        results.setText(selectedFood);

        // set spinner
        nutritionalSpinner = (Spinner) findViewById(R.id.nutrSpinner);
        densitySpinner = (Spinner) findViewById(R.id.densitySpinner);

        // get cursors
        Cursor nutrition = nutrInfo.queryContaining(selectedFood);
        Cursor density = densityInfo.queryContaining(selectedFood);

        // get maps
        final Map<String, Double> nutrMap = nutrInfo.getMapFromCursor(nutrition);
        final Map<String, Double> densityMap = densityInfo.getMapFromCursor(density);

        // get keys
        List<String> nutrKeys = nutrInfo.getKeysFromCursor(nutrition);
        List<String> densityKeys = densityInfo.getKeysFromCursor(density);

        Log.d("results size", String.valueOf(nutrKeys.size()));
        Log.d("results size", String.valueOf(densityKeys.size()));

        String[] nutrArr = new String[nutrKeys.size()];
        nutrArr = nutrKeys.toArray(nutrArr);

        String[] densArr = new String[densityKeys.size()];
        densArr = densityKeys.toArray(densArr);

        Log.d("results size", String.valueOf(nutrArr.length));
        Log.d("results size", String.valueOf(densArr.length));


        // set adapters

        final ArrayAdapter<CharSequence> nutrAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, nutrArr);
        nutritionalSpinner.setAdapter(nutrAdapter);
        final ArrayAdapter<CharSequence> densAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, densArr);
        densitySpinner.setAdapter(densAdapter);

        //final String selectedNutr;

        nutritionalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //Object item = parent.getItemAtPosition(pos);
                final String selectedNutr = nutritionalSpinner.getItemAtPosition(pos).toString();
                Log.d("nutrSpinner", selectedNutr);
                //Log.d("results page", "trying to create rpage");
                //final resultsPage rpage = new resultsPage();
                densitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        Double nutrdensity = nutrMap.get(selectedNutr);

                        //Object item = parent.getItemAtPosition(pos);
                        final String selectedDens = densitySpinner.getItemAtPosition(pos).toString();
                        Log.d("nutrSpinner", selectedDens);

                        Double fooddensity = densityMap.get(selectedDens);

                        results.setText(String.valueOf(nutrdensity*fooddensity));
                        //Log.d("results page", "trying to create rpage");
                        //final resultsPage rpage = new resultsPage();


                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                        Log.d("Spinner", "nothing selected");
                    }
                });
            }
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Spinner", "nothing selected");
            }
        });

        //Log.d("results", nutrKeys.get(1));
        //Log.d("results", densityKeys.get(1));

        //Log.d("results", String.valueOf(nutrMap.get(nutrKeys.get(1))));
        //Log.d("results", String.valueOf(densityMap.get(densityKeys.get(1))));

        Log.d("Flow", "Final Results Page");

        returnToMainButton();
    }

    public void returnToMainButton() {
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

    //@OnClick(R.id.fab)
    void pickImage() {
        startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"), PICK_IMAGE);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch(requestCode) {
            case PICK_IMAGE:
                final byte[] imageBytes = retrieveSelectedImage(this, data);
                if (imageBytes != null) {
                    //onImagePicked(imageBytes);
                    Log.d("prediction", "attempting");
                    clarifai.onImagePicked(imageBytes);
                    imageSelected.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
                    //List<Concept> predictions = clarifai.getPredictions(imageBytes);
                    //Log.d("predictions", predictions.get(0).toString());
                    Log.d("predictions", "predicted");
                }
                break;
        }
    }

    @Nullable
    public static byte[] retrieveSelectedImage(@NonNull Context context, @NonNull Intent data) {
        InputStream inStream = null;
        Bitmap bitmap = null;
        try {
            inStream = context.getContentResolver().openInputStream(data.getData());
            bitmap = BitmapFactory.decodeStream(inStream);
            final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            return outStream.toByteArray();
        } catch (FileNotFoundException e) {
            return null;
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException ignored) {
                }
            }
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
    }
}
