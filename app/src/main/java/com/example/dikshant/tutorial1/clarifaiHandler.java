package com.example.dikshant.tutorial1;

/**
 * Created by Dikshant on 2/14/2017.
 */

import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;


//more clarifai stuff
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

public class clarifaiHandler {

    @Nullable
    private ClarifaiClient client;
    private ConceptModel foodModel;
    private List<Concept> predictionsList;

    private boolean predictionsFlag;

    public clarifaiHandler(){
        //client = new ClarifaiBuilder("qeNitO9lCdNO7k7UixA6yUXKIIX3-MolxrXUL4Oq", "zOxPlyS5BfqFCp_CVW1Y2ZkZyNW81IMQ6sAOkodR")
                //.client(new OkHttpClient()) // OPTIONAL. Allows customization of OkHttp by the user
         //       .buildSync(); // or use .build() to get a Future<ClarifaiClient>
        Log.d("Clar", "start");

        predictionsFlag = false;

        client = new ClarifaiBuilder("qeNitO9lCdNO7k7UixA6yUXKIIX3-MolxrXUL4Oq", "zOxPlyS5BfqFCp_CVW1Y2ZkZyNW81IMQ6sAOkodR")
                // Optionally customize HTTP client via a custom OkHttp instance
                .client(new OkHttpClient.Builder()
                        .readTimeout(30, TimeUnit.SECONDS) // Increase timeout for poor mobile networks

                        // Log all incoming and outgoing data
                        // NOTE: You will not want to use the BODY log-level in production, as it will leak your API request details
                        // to the (publicly-viewable) Android log
                        .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                            @Override public void log(String logString) {
                                Timber.e(logString);
                            }
                        }).setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build()
                )
                .buildSync();
        Log.d("Clar", "built");

                // if a Client is registered as a default instance, it will be used
                // automatically, without the user having to keep it around as a field.
                // This can be omitted if you want to manually manage your instance
                //.registerAsDefaultInstance();

        foodModel = client.getDefaultModels().foodModel();
        Log.d("Clar", "model");
    }

    public List<Concept> getPredictions(@NonNull final byte[] imageBytes){

        Log.d("predictions", "inside method");

        ClarifaiResponse<List<ClarifaiOutput<Concept>>> response =  foodModel.predict()
                .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(imageBytes)))
                .executeSync();

        Log.d("predictions", "got response");
        List<ClarifaiOutput<Concept>> predictions = response.get();
        Log.d("predictions", "got list");
        return predictions.get(0).data();
    }

    public void onImagePicked(@NonNull final byte[] imageBytes) {
        // Now we will upload our image to the Clarifai API
        //setBusy(true);

        // Make sure we don't show a list of old concepts while the image is being uploaded
        //adapter.setData(Collections.<Concept>emptyList());

        new AsyncTask<Void, Void, ClarifaiResponse<List<ClarifaiOutput<Concept>>>>() {
            @Override protected ClarifaiResponse<List<ClarifaiOutput<Concept>>> doInBackground(Void... params) {
                // The default Clarifai model that identifies concepts in images
                //final ConceptModel generalModel = App.get().clarifaiClient().getDefaultModels().foodModel();

                // Use this model to predict, with the image that the user just selected as the input

                Log.d("predictions", "something happened");
                return foodModel.predict()
                        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(imageBytes)))
                        .executeSync();
            }

            @Override protected void onPostExecute(ClarifaiResponse<List<ClarifaiOutput<Concept>>> response) {
                //setBusy(false);
                if (!response.isSuccessful()) {
                    //showErrorSnackbar(R.string.error_while_contacting_api);
                    return;
                }
                final List<ClarifaiOutput<Concept>> predictions = response.get();
                if (predictions.isEmpty()) {
                    //showErrorSnackbar(R.string.no_results_from_api);
                    return;
                }
                predictionsFlag = true;
                Log.d("predictions", "set flag");
                predictionsList = predictions.get(0).data();
            }
            //private void showErrorSnackbar(@StringRes int errorString) {
            //    Snackbar.make(
            //            root,
            //            errorString,
            //            Snackbar.LENGTH_INDEFINITE
            //    ).show();
            //}
        }.execute();
    }

    public List<Concept> returnPredictions(){
        return predictionsList;
    }

    public String[] predictionsArray(){
        if (predictionsList.size() == 0 || (predictionsFlag == false)){
            return null;
        }
        int size = predictionsList.size();

        String[] predictionArray = new String[size];
        for (int i = 0; i < size; i++){
            predictionArray[i] = predictionsList.get(i).name();
        }

        return predictionArray;
    }

    public boolean hasPredictions(){
        return predictionsFlag;
    }
}