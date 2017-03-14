package com.vodafone.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.vodafone.graph.model.AxisPointModel;
import com.vodafone.graph.model.CircleModel;
import com.vodafone.graph.model.LineModel;
import com.vodafone.graph.model.PointModel;
import com.vodafone.graph.model.ShapeModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by abdullah on 3/12/17.
 */

public class GraphView extends View {
    private int xAxisLeftPadding;
    private int yAxisBottomPadding;
    private List<AxisPointModel> xPoints;
    private List<AxisPointModel> yPoints;
    private List<ShapeModel> shapes;
    //=====>
    private AxisPointModel xStart;
    private AxisPointModel xEnd;
    private AxisPointModel yStart;
    private AxisPointModel yEnd;
    //=====>
    private Paint xAxisPaint;
    private Paint yAxisPaint;
    private Paint shapePaint;

    public GraphView(Context context) {
        super(context);
        init();
    }

    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    //===================>

    private void init() {
        xPoints = new ArrayList<>();
        yPoints = new ArrayList<>();
        shapes = new ArrayList<>();
        xAxisLeftPadding = GraphUtils.dpToPx(getContext(), 30);
        yAxisBottomPadding = GraphUtils.dpToPx(getContext(), 30);
        xAxisPaint = new Paint();
        yAxisPaint = new Paint();
        shapePaint = new Paint();

        xAxisPaint.setTextSize(50);
        xAxisPaint.setStrokeWidth(10);
        xAxisPaint.setColor(Color.RED);

        yAxisPaint.setTextSize(50);
        yAxisPaint.setStrokeWidth(10);
        yAxisPaint.setColor(Color.RED);

        shapePaint.setStyle(Paint.Style.STROKE);
        shapePaint.setStrokeWidth(10);
        shapePaint.setColor(Color.RED);
    }

    public void setData(List<AxisPointModel> xPoints, List<AxisPointModel> yPoints, List<ShapeModel> shapes) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.shapes = shapes;
        if (xPoints != null && !xPoints.isEmpty()) {
            Collections.sort(xPoints, new Comparator<AxisPointModel>() {
                @Override
                public int compare(AxisPointModel o1, AxisPointModel o2) {
                    return Double.compare(o1.getValue(), o2.getValue());
                }
            });
            xStart = xPoints.get(0);
            xEnd = xPoints.get(xPoints.size() - 1);
        }
        if (yPoints != null && !yPoints.isEmpty()) {
            Collections.sort(yPoints, new Comparator<AxisPointModel>() {
                @Override
                public int compare(AxisPointModel o1, AxisPointModel o2) {
                    return Double.compare(o1.getValue(), o2.getValue());
                }
            });
            yStart = yPoints.get(0);
            yEnd = yPoints.get(yPoints.size() - 1);
        }
        invalidate();
    }

    //===================>

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawXAxis(canvas);
        drawYAxis(canvas);
        drawShapes(canvas);
    }

    private void drawYAxis(Canvas canvas) {
        for (AxisPointModel point : yPoints) {
            canvas.drawText(point.getText(), getViewXStart(), toViewYCoord(point.getValue()), yAxisPaint);
        }
    }

    private void drawXAxis(Canvas canvas) {
        for (AxisPointModel point : xPoints) {
            float x = toViewXCoord(point.getValue());
            int y = getViewYEnd();
            canvas.drawText(point.getText(), x, y, xAxisPaint);
        }
    }

    private void drawShapes(Canvas canvas) {
        for (ShapeModel shape : shapes) {
            shapePaint.setColor(shape.getColor());
            if (shape instanceof LineModel) {
                LineModel lineModel = (LineModel) shape;
                PointModel startPoint = lineModel.getStartPoint();
                PointModel endPoint = lineModel.getEndPoint();
                Path path = new Path();
                path.moveTo(toViewXCoord(startPoint.getX()), toViewYCoord(startPoint.getY()));
                path.lineTo(toViewXCoord(endPoint.getX()), toViewYCoord(endPoint.getY()));
                switch (lineModel.getType()) {
                    case LineModel.NORMAL:
                        shapePaint.setStyle(Paint.Style.STROKE);
                        shapePaint.setPathEffect(null);
                        break;
                    case LineModel.DASH:
                        shapePaint.setStyle(Paint.Style.STROKE);
                        shapePaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
                        break;
                }
                canvas.drawPath(path, shapePaint);
            } else if (shape instanceof CircleModel) {
                CircleModel circleModel = (CircleModel) shape;
                PointModel origin = circleModel.getOrigin();
                double radius = circleModel.getRadius();
                switch (circleModel.getType()) {
                    case CircleModel.TYPE_FILLED:
                        shapePaint.setStyle(Paint.Style.FILL);
                        break;
                    case CircleModel.TYPE_STROKED:
                        shapePaint.setStyle(Paint.Style.STROKE);
                        break;
                }
                canvas.drawCircle(toViewXCoord(origin.getX()), toViewYCoord(origin.getY()), (float) radius, shapePaint);
            }
        }
    }

    //============>

    private float toViewXCoord(double xValue) {
        double viewStart = getViewXStart();
        double viewEnd = getViewXEnd();
        double xPercentOnAxis = GraphUtils.getPercentOfPointOnLine(xStart.getValue(), xEnd.getValue() + 1, xValue);
        return (float) GraphUtils.getPointFromPercentOnLine(viewStart, viewEnd, xPercentOnAxis) + xAxisLeftPadding;
    }

    private float toViewYCoord(double yValue) {
        double viewStart = getViewYStart();
        double viewEnd = getViewYEnd();
        double yPercentOnAxis = 100 - GraphUtils.getPercentOfPointOnLine(yStart.getValue(), yEnd.getValue() + 1, yValue);
        return (float) GraphUtils.getPointFromPercentOnLine(viewStart, viewEnd, yPercentOnAxis) - yAxisBottomPadding;
    }

    private int getViewXStart() {
        return getPaddingLeft();
    }

    private int getViewXEnd() {
        return getWidth() - getPaddingRight();
    }

    private int getViewYStart() {
        return getPaddingTop();
    }

    private int getViewYEnd() {
        return getHeight() - getPaddingBottom();
    }

    //============>

}
