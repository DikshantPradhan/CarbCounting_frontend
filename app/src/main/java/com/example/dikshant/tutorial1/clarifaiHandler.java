package com.example.dikshant.tutorial1;

/**
 * Created by Dikshant on 2/14/2017.
 */

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;


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
        client = new ClarifaiBuilder("qeNitO9lCdNO7k7UixA6yUXKIIX3-MolxrXUL4Oq", "zOxPlyS5BfqFCp_CVW1Y2ZkZyNW81IMQ6sAOkodR")
                .client(new OkHttpClient()) // OPTIONAL. Allows customization of OkHttp by the user
                .buildSync(); // or use .build() to get a Future<ClarifaiClient>

                // if a Client is registered as a default instance, it will be used
                // automatically, without the user having to keep it around as a field.
                // This can be omitted if you want to manually manage your instance
                //.registerAsDefaultInstance();

        foodModel = client.getDefaultModels().foodModel();
    }

    public List<Concept> getPredictions(@NonNull final byte[] imageBytes){
        ClarifaiResponse<List<ClarifaiOutput<Concept>>> response =  foodModel.predict()
                .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(imageBytes)))
                .executeSync();
        List<ClarifaiOutput<Concept>> predictions = response.get();

        return predictions.get(0).data();
    }



}
