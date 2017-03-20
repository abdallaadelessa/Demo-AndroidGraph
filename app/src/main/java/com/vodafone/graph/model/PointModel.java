package com.vodafone.graph.model;

/**
 * Created by abdullah on 3/12/17.
 */

public class PointModel implements Cloneable {
    private double x;
    private double y;

    public PointModel() {
    }

    public PointModel(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public PointModel clone() {
        try {
            return (PointModel) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
