package com.example.dikshant.tutorial1;

/**
 * Created by Dikshant on 2/14/2017.
 */

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

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

    public clarifaiHandler(){
        //client = new ClarifaiBuilder("qeNitO9lCdNO7k7UixA6yUXKIIX3-MolxrXUL4Oq", "zOxPlyS5BfqFCp_CVW1Y2ZkZyNW81IMQ6sAOkodR")
                //.client(new OkHttpClient()) // OPTIONAL. Allows customization of OkHttp by the user
         //       .buildSync(); // or use .build() to get a Future<ClarifaiClient>
        Log.d("Clar", "start");

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
        ClarifaiResponse<List<ClarifaiOutput<Concept>>> response =  foodModel.predict()
                .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(imageBytes)))
                .executeSync();
        List<ClarifaiOutput<Concept>> predictions = response.get();

        return predictions.get(0).data();
    }



}
