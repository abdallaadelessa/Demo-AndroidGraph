package com.vodafone.graph;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vodafone.graph.model.AxisPointModel;
import com.vodafone.graph.model.CircleModel;
import com.vodafone.graph.model.LineModel;
import com.vodafone.graph.model.PointModel;
import com.vodafone.graph.model.ShapeModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        graphView = (GraphView) findViewById(R.id.graphview);
        ArrayList<AxisPointModel> xPoints = new ArrayList<>();
        xPoints.add(new AxisPointModel(0, "Day1"));
        xPoints.add(new AxisPointModel(1, "Day2"));
        xPoints.add(new AxisPointModel(2, "Day3"));
        xPoints.add(new AxisPointModel(3, "Day4"));
        xPoints.add(new AxisPointModel(4, "Day5"));
        ArrayList<AxisPointModel> yPoints = new ArrayList<>();
        yPoints.add(new AxisPointModel(0, "1GB"));
        yPoints.add(new AxisPointModel(1, "2GB"));
        yPoints.add(new AxisPointModel(2, "3GB"));
        yPoints.add(new AxisPointModel(3, "4GB"));
        yPoints.add(new AxisPointModel(4, "5GB"));
        ArrayList<ShapeModel> shapes = new ArrayList<>();
        shapes.add(new LineModel(new PointModel(1, 1), new PointModel(2.5, 2.5), Color.RED, LineModel.DASH));
        shapes.add(new CircleModel(new PointModel(1, 1), 20, ContextCompat.getColor(this, android.R.color.black), CircleModel.TYPE_FILLED));
        shapes.add(new CircleModel(new PointModel(2.5, 2.5), 20, ContextCompat.getColor(this, android.R.color.black), CircleModel.TYPE_FILLED));
        //  shapes.add(new LineModel(new PointModel(3, 3), new PointModel(4, 4), ContextCompat.getColor(this, android.R.color.black), LineModel.NORMAL));
        graphView.setData(xPoints, yPoints, shapes);
    }
}
