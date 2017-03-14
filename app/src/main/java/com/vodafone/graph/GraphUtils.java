package com.vodafone.graph;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

import com.vodafone.graph.model.PointModel;

/**
 * Created by abdullah on 3/12/17.
 */

public class GraphUtils {
    private static final int PERCENTAGE = 100;

    public static double getPercentOfPointOnLine(double startPoint, double endPoint, double point) {
        double length = endPoint - startPoint;
        double pointValuePerLine = endPoint / length;
        double pointValuePerPercent = PERCENTAGE / length;
        double pointWithRespectToPointValuePerLine = (point - startPoint) * pointValuePerLine;
        return (pointWithRespectToPointValuePerLine * pointValuePerPercent) / pointValuePerLine;
    }

    public static double getPointFromPercentOnLine(double startPoint, double endPoint, double percent) {
        double length = endPoint - startPoint;
        double pointValuePerLine = endPoint / length;
        double pointValuePerPercent = PERCENTAGE / length;
        double pointWithRespectToPointValuePerLine = (percent * pointValuePerLine) / pointValuePerPercent;
        return startPoint + (pointWithRespectToPointValuePerLine / pointValuePerLine);
    }

    public static PointModel getMidPoint(double xStart, double yStart, double xEnd, double yEnd) {
        double mx = (xStart + xEnd) / 2;
        double my = (yStart + yEnd) / 2;
        return new PointModel(mx, my);
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
