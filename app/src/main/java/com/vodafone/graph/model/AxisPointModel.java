package com.vodafone.graph.model;

import java.util.Comparator;

/**
 * Created by abdullah on 3/12/17.
 */

public class AxisPointModel {
    private double value;
    private String text;

    public AxisPointModel(double value) {
        this.value = value;
        this.text = String.valueOf(value);
    }

    public AxisPointModel(double value, String text) {
        this.value = value;
        this.text = text;
    }

    public double getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

}
