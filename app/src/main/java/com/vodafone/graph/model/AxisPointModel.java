package com.vodafone.graph.model;

/**
 * Created by abdullah on 3/12/17.
 */

public class AxisPointModel {
    private double value;
    private String text;
    private boolean hidden;

    public AxisPointModel(double value) {
        this.value = value;
        this.text = String.valueOf(value);
    }

    public AxisPointModel(double value, String text) {
        this.value = value;
        this.text = text;
    }

    public AxisPointModel(double value, boolean hidden) {
        this.value = value;
        this.hidden = hidden;
    }

    public AxisPointModel(double value, String text, boolean hidden) {
        this.value = value;
        this.text = text;
        this.hidden = hidden;
    }


    public double getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public boolean isHidden() {
        return hidden;
    }
}
