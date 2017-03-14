package com.vodafone.graph.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Comparator;

/**
 * Created by abdullah on 3/12/17.
 */

public abstract class ShapeModel {
    private int color;

    public ShapeModel() {
    }

    public ShapeModel(int color) {
        this.color = color;
    }

    //=================>

    public int getColor() {
        return color;
    }

    //=================>
}
