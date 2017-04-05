package com.example.dikshant.tutorial1;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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
}
