package com.vodafone.graph.model;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by abdullah on 3/12/17.
 */

public class LineModel extends ShapeModel {
    public static final int NORMAL = 0;
    public static final int DASH = 1;
    private PointModel startPoint;
    private PointModel endPoint;
    private int type;

    public LineModel(PointModel startPoint, PointModel endPoint, int color, int type) {
        super(color);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.type = type;
    }

    public PointModel getStartPoint() {
        return startPoint;
    }

    public PointModel getEndPoint() {
        return endPoint;
    }

    public int getType() {
        return type;
    }

    //=================>
}
