package com.vodafone.graph.model;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by abdullah on 3/12/17.
 */

public class CircleModel extends ShapeModel {
    public static final int TYPE_STROKED = 0;
    public static final int TYPE_FILLED = 1;
    private PointModel origin;
    private double radius;
    private int type;

    public CircleModel(PointModel origin, double radius, int color, int type) {
        super(color);
        this.origin = origin;
        this.radius = radius;
        this.type = type;
    }

    public PointModel getOrigin() {
        return origin;
    }

    public double getRadius() {
        return radius;
    }

    public int getType() {
        return type;
    }

    //=================>
}
