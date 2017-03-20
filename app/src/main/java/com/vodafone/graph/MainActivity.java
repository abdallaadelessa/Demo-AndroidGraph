package com.vodafone.graph;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vodafone.graph.model.AxisPointModel;
import com.vodafone.graph.model.CircleModel;
import com.vodafone.graph.model.ConnectedCirclesModel;
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

        //==> X Axis
        ArrayList<AxisPointModel> xAxisPoints = new ArrayList<>();
        xAxisPoints.add(new AxisPointModel(0, "08.02.17", false));
        xAxisPoints.add(new AxisPointModel(15, "Heute", false));
        xAxisPoints.add(new AxisPointModel(30, "06.03.17", false));

        //==> Y Axis
        ArrayList<AxisPointModel> yAxisPoints = new ArrayList<>();
        yAxisPoints.add(new AxisPointModel(0, "", false));
        yAxisPoints.add(new AxisPointModel(1, "1GB", false));
        yAxisPoints.add(new AxisPointModel(2, "2GB", false));
        yAxisPoints.add(new AxisPointModel(3, "3GB", false));

        //==> Graph
        ArrayList<ShapeModel> shapes = new ArrayList<>();
        ConnectedCirclesModel connectedCirclesModel = new ConnectedCirclesModel();
        connectedCirclesModel.addCircle(new ConnectedCirclesModel.ConnectedCircle(new PointModel(0, 0), 20, Color.BLUE, CircleModel.TYPE_STROKED, Color.BLACK, LineModel.NORMAL));
        connectedCirclesModel.addCircle(new ConnectedCirclesModel.ConnectedCircle(new PointModel(5, 0), 20, Color.BLUE, CircleModel.TYPE_STROKED, Color.BLACK, LineModel.NORMAL));
        connectedCirclesModel.addCircle(new ConnectedCirclesModel.ConnectedCircle(new PointModel(5, 0.2), 20, Color.BLUE, CircleModel.TYPE_STROKED, Color.BLACK, LineModel.NORMAL));
        connectedCirclesModel.addCircle(new ConnectedCirclesModel.ConnectedCircle(new PointModel(10, 0.8), 30, Color.GREEN, CircleModel.TYPE_STROKED, Color.YELLOW, LineModel.DASH));
        connectedCirclesModel.addCircle(new ConnectedCirclesModel.ConnectedCircle(new PointModel(15, 1.9), 50, Color.MAGENTA, CircleModel.TYPE_FILLED, Color.BLUE, LineModel.DASH));
        connectedCirclesModel.addCircle(new ConnectedCirclesModel.ConnectedCircle(new PointModel(20, 2.4), 0, Color.GREEN, CircleModel.TYPE_FILLED, Color.RED, LineModel.NORMAL));
        connectedCirclesModel.addCircle(new ConnectedCirclesModel.ConnectedCircle(new PointModel(25, 2.9), 20, Color.RED, CircleModel.TYPE_FILLED));
        shapes.add(connectedCirclesModel);
        // Add Data
        graphView.setData(xAxisPoints, yAxisPoints, shapes);
    }
}
