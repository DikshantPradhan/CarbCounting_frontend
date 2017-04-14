package com.example.dikshant.tutorial1;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Date;
import java.util.List;

/**
 * Created by Dikshant on 4/4/2017.
 */

public class graphviewAdapter {

    GraphView graph;

    public graphviewAdapter(GraphView graph){
        this.graph = graph;
    }

    public void graph(List<Double> X, List<Double> Y){
        int length = X.size();
        DataPoint[] values = new DataPoint[length];

        for (int i = 0; i < length; i++){
            values[i] = new DataPoint(X.get(i), Y.get(i));
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(values);
        graph.addSeries(series);
    }

    public void graph(LineGraphSeries<DataPoint> series){
        graph.addSeries(series);
    }

    public void graphDate(List<Date> X, List<Double> Y){
        int length = X.size();
        DataPoint[] values = new DataPoint[length];

        for (int i = 0; i < length; i++){
            values[i] = new DataPoint(X.get(i).getDate(), Y.get(i));
        }

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(values);
        graph.addSeries(series);

        // set date label formatter
        //graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        //graph.getGridLabelRenderer().setNumHorizontalLabels(length); // only 4 because of the space

// set manual x bounds to have nice steps
        graph.getViewport().setMinX(X.get(0).getDay());
        graph.getViewport().setMaxX(X.get(length - 1).getDay());
        //graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(500);
        //graph.getViewport().setYAxisBoundsManual(true);

        //String[] stockArr = new String[X.size()];
        //stockArr = X.toArray(stockArr);
        //graph.getGridLabelRenderer().


// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Date (April)");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Carbohydrates Consumed per Day");
        graph.setTitle("Carbohydrate Consumption History");
    }
}
