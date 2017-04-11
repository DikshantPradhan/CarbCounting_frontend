package com.example.dikshant.tutorial1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dikshant on 4/2/2017.
 */

public class nutrMeal {

    private List<String> foods;
    private List<Double> carbFactors;
    private List<Double> volumes;
    private String name;

    public nutrMeal(){
        foods = new ArrayList<String>();
        carbFactors = new ArrayList<Double>();
        volumes = new ArrayList<Double>();
        name = "NA";
    }

    public nutrMeal(String food, double carbFactor, double volume){
        foods = new ArrayList<String>();
        carbFactors = new ArrayList<Double>();
        volumes = new ArrayList<Double>();

        foods.add(food);
        carbFactors.add(carbFactor);
        volumes.add(volume);
    }

    public void addMeal(String food, double carbFactor, double volume){
        foods.add(food);
        carbFactors.add(carbFactor);
        volumes.add(volume);
    }

    public void setName(String name_entry){
        name = name_entry;
    }

    public List<String> getFoods(){
        return foods;
    }

    public List<Double> getCarbFactors(){
        return carbFactors;
    }

    public List<Double> getVolumes(){
        return volumes;
    }

    public String getName(){
        return name;
    }

    public void clear(){
        foods = new ArrayList<String>();
        carbFactors = new ArrayList<Double>();
        volumes = new ArrayList<Double>();
    }
}
