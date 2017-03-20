package com.vodafone.graph.model;

import android.graphics.drawable.shapes.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdullah on 3/20/17.
 */

public class ConnectedCirclesModel extends ShapeModel {
    private List<ConnectedCircle> circles;

    public ConnectedCirclesModel() {
        circles = new ArrayList<>();
    }

    public void addCircle(ConnectedCircle circleModel) {
        this.circles.add(circleModel);
    }

    public List<ConnectedCircle> getCircles() {
        return circles;
    }

    public static class ConnectedCircle extends CircleModel {
        private int nextLineColor;
        private int nextLineType;

        public ConnectedCircle(PointModel origin, double radius, int color, int type) {
            super(origin, radius, color, type);
        }

        public ConnectedCircle(PointModel origin, double radius, int color, int type, int nextLineColor, int nextLineType) {
            super(origin, radius, color, type);
            this.nextLineColor = nextLineColor;
            this.nextLineType = nextLineType;
        }

        public int getNextLineColor() {
            return nextLineColor;
        }

        public int getNextLineType() {
            return nextLineType;
        }
    }
}
