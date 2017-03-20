package com.vodafone.graph;

import android.graphics.PointF;
import android.view.View;

/**
 * Created by mohammad on 3/20/17.
 */

public class CircleUtil {

    public static float getDegree(PointF startingPointF, PointF endingPointF) {
        float y = -1 * (endingPointF.y - startingPointF.y);
        float x = endingPointF.x - startingPointF.x;
        double radiun = Math.atan(y / x);
        return (float) (radiun * (180F / Math.PI));
    }


    public static PointF getTangentPointOfCircle(float radius, float degree, boolean revertSign) {
        double xTangent = (radius) * Math.cos(degree * (Math.PI / 180F));
        double yTangent = -1 * (radius) * Math.sin(degree * (Math.PI / 180F));
        if (revertSign) {
            xTangent *= -1;
            yTangent *= -1;
        }
        return new PointF((float) xTangent, (float) yTangent);
    }

    public static PointF getDrawingTangentPointInViewGroup(PointF centerPoint, PointF tangentPointOfCircle) {
        float xDrawingCenterPoint = centerPoint.x + tangentPointOfCircle.x;
        float yDrawingCenterPoint = centerPoint.y + tangentPointOfCircle.y;
        return new PointF(xDrawingCenterPoint, yDrawingCenterPoint);
    }

    public static PointF getViewCenterPoint(View view) {
        float circleRadius = (view.getWidth() / 2);
        PointF circleGaugeTopLeftPoint = getViewTopLeftPoint(view);
        float centerX = circleGaugeTopLeftPoint.x + circleRadius;
        float centerY = circleGaugeTopLeftPoint.y + circleRadius;
        return new PointF(centerX, centerY);
    }

    public static PointF getViewTopLeftPoint(View view) {
        return new PointF(view.getX(), view.getY());
    }


}
