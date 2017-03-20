package com.vodafone.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.vodafone.graph.model.AxisPointModel;
import com.vodafone.graph.model.CircleModel;
import com.vodafone.graph.model.ConnectedCirclesModel;
import com.vodafone.graph.model.LineModel;
import com.vodafone.graph.model.PointModel;
import com.vodafone.graph.model.ShapeModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by abdullah on 3/12/17.
 */

public class GraphView extends View {
    private int xAxisLeftPadding;
    private int xAxisRightPadding;
    private int yAxisTopPadding;
    private int yAxisBottomPadding;
    private List<AxisPointModel> xAxisPoints;
    private List<AxisPointModel> yAxisPoints;
    private List<ShapeModel> shapes;
    //=====>
    private AxisPointModel xAxisStart;
    private AxisPointModel xAxisEnd;
    private AxisPointModel yAxisStart;
    private AxisPointModel yAxisEnd;
    //=====>
    private Paint graphBackgroundLinesPaint;
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
        xAxisPoints = new ArrayList<>();
        yAxisPoints = new ArrayList<>();
        shapes = new ArrayList<>();

        xAxisLeftPadding = GraphUtils.dpToPx(getContext(), 30);
        xAxisRightPadding = GraphUtils.dpToPx(getContext(), 30);
        yAxisTopPadding = GraphUtils.dpToPx(getContext(), 10);
        yAxisBottomPadding = GraphUtils.dpToPx(getContext(), 10);

        graphBackgroundLinesPaint = new Paint();
        xAxisPaint = new Paint();
        yAxisPaint = new Paint();
        shapePaint = new Paint();

        graphBackgroundLinesPaint.setStrokeWidth(1);
        graphBackgroundLinesPaint.setColor(Color.RED);

        xAxisPaint.setTextSize(40);
        xAxisPaint.setStrokeWidth(10);
        xAxisPaint.setColor(Color.RED);

        yAxisPaint.setTextSize(40);
        yAxisPaint.setStrokeWidth(10);
        yAxisPaint.setColor(Color.RED);

        shapePaint.setStyle(Paint.Style.STROKE);
        shapePaint.setStrokeWidth(10);
        shapePaint.setColor(Color.RED);
    }

    public void setData(List<AxisPointModel> xPoints, List<AxisPointModel> yPoints, List<ShapeModel> shapes) {
        this.xAxisPoints = xPoints;
        this.yAxisPoints = yPoints;
        this.shapes = shapes;

        if (xPoints != null && !xPoints.isEmpty()) {
            Collections.sort(xPoints, new Comparator<AxisPointModel>() {
                @Override
                public int compare(AxisPointModel o1, AxisPointModel o2) {
                    return Double.compare(o1.getValue(), o2.getValue());
                }
            });
            xAxisStart = xPoints.get(0);
            xAxisEnd = xPoints.get(xPoints.size() - 1);
        }
        if (yPoints != null && !yPoints.isEmpty()) {
            Collections.sort(yPoints, new Comparator<AxisPointModel>() {
                @Override
                public int compare(AxisPointModel o1, AxisPointModel o2) {
                    return Double.compare(o1.getValue(), o2.getValue());
                }
            });
            yAxisStart = yPoints.get(0);
            yAxisEnd = yPoints.get(yPoints.size() - 1);
        }
        invalidate();
    }

    //===================>

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawBackgroundLines(canvas);
        drawXAxis(canvas);
        drawYAxis(canvas);
        drawShapes(canvas);
    }

    //==> Graph

    private void drawBackgroundLines(Canvas canvas) {
        for (AxisPointModel point : yAxisPoints) {
            if (point.isHidden()) continue;
            float y = toViewYCoord(point.getValue());
            canvas.drawLine(getViewXStart(), y, getViewXEnd(), y, graphBackgroundLinesPaint);
            canvas.drawCircle(getViewXStart(), y, 12, graphBackgroundLinesPaint);
        }
        for (AxisPointModel point : xAxisPoints) {
            if (point.isHidden()) continue;
            float x = toViewXCoord(point.getValue());
            canvas.drawLine(x, getViewYEnd(), x, getViewYStart(), graphBackgroundLinesPaint);
            canvas.drawCircle(x, getViewYEnd(), 12, graphBackgroundLinesPaint);
        }
    }

    private void drawYAxis(Canvas canvas) {
        for (AxisPointModel point : yAxisPoints) {
            if (point.isHidden()) continue;
            int x = getViewXStart();
            float y = toViewYCoord(point.getValue());
            String text = point.getText();
            //==>
            Rect textRect = GraphUtils.getTextRect(yAxisPaint, text);
            x -= textRect.right / 2;
            y -= textRect.bottom / 2;
            canvas.drawText(text, x, y, yAxisPaint);
        }
    }

    private void drawXAxis(Canvas canvas) {
        for (AxisPointModel point : xAxisPoints) {
            if (point.isHidden()) continue;
            float x = toViewXCoord(point.getValue());
            int y = getViewYEnd();
            String text = point.getText();
            //==>
            Rect textRect = GraphUtils.getTextRect(xAxisPaint, text);
            x -= textRect.right / 2;
            y += textRect.bottom / 2;
            canvas.drawText(text, x, y, xAxisPaint);
        }
    }

    //==> Shapes

    private void drawShapes(Canvas canvas) {
        for (ShapeModel shape : shapes) {
            shapePaint.setColor(shape.getColor());
            if (shape instanceof ConnectedCirclesModel) {
                drawConnectedCirclesModel(canvas, (ConnectedCirclesModel) shape);
            } else if (shape instanceof LineModel) {
                drawLineSegment(canvas, (LineModel) shape);
            } else if (shape instanceof CircleModel) {
                drawCircle(canvas, (CircleModel) shape);
            }
        }
    }

    private void drawCircle(Canvas canvas, CircleModel shape) {
        PointModel origin = shape.getOrigin();
        double radius = shape.getRadius();
        switch (shape.getType()) {
            case CircleModel.TYPE_FILLED:
                shapePaint.setStyle(Paint.Style.FILL);
                break;
            case CircleModel.TYPE_STROKED:
                shapePaint.setStyle(Paint.Style.STROKE);
                break;
        }
        shapePaint.setColor(shape.getColor());
        canvas.drawCircle(toViewXCoord(origin.getX()), toViewYCoord(origin.getY()), (float) radius, shapePaint);
    }

    private void drawLineSegment(Canvas canvas, LineModel shape) {
        PointModel startPoint = shape.getStartPoint();
        PointModel endPoint = shape.getEndPoint();
        Path path = new Path();
        path.moveTo(toViewXCoord(startPoint.getX()), toViewYCoord(startPoint.getY()));
        path.lineTo(toViewXCoord(endPoint.getX()), toViewYCoord(endPoint.getY()));
        switch (shape.getType()) {
            case LineModel.NORMAL:
                shapePaint.setStyle(Paint.Style.STROKE);
                shapePaint.setPathEffect(null);
                break;
            case LineModel.DASH:
                shapePaint.setStyle(Paint.Style.STROKE);
                shapePaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
                break;
        }
        shapePaint.setColor(shape.getColor());
        canvas.drawPath(path, shapePaint);
    }

    private void drawConnectedCirclesModel(Canvas canvas, ConnectedCirclesModel shape) {
        int size = shape.getCircles().size();
        for (int i = 0; i < size; i = i + 1) {
            ConnectedCirclesModel.ConnectedCircle currentCircle = shape.getCircles().get(i);
            ConnectedCirclesModel.ConnectedCircle nextCircle = i + 1 < size ? shape.getCircles().get(i + 1) : null;
            if (nextCircle != null) {
                float r1 = (float) (currentCircle.getRadius() - shapePaint.getStrokeWidth());
                float r2 = (float) (nextCircle.getRadius() - shapePaint.getStrokeWidth());

                float lineXStart = toViewXCoord(currentCircle.getOrigin().getX());
                float lineYStart = toViewYCoord(currentCircle.getOrigin().getY());
                float lineXEnd = toViewXCoord(nextCircle.getOrigin().getX());
                float lineYEnd = toViewYCoord(nextCircle.getOrigin().getY());

//                float degree = CircleUtil.getDegree(new PointF(lineXStart, lineYStart), new PointF(lineXEnd, lineYEnd));
//                PointF startTangentPoint = CircleUtil.getTangentPointOfCircle(r1, degree);
//                PointF endTangentPoint = CircleUtil.getTangentPointOfCircle(r2, degree);
//
//                PointF point1 = CircleUtil.getDrawingCenterPointInViewGroup(new PointF(lineXStart, lineYStart), startTangentPoint);
//                PointF point2 = CircleUtil.getDrawingCenterPointInViewGroup(new PointF(lineXEnd, lineYEnd), endTangentPoint);
//                lineXStart = point1.x;
//                lineYStart = point1.y;
//                lineXEnd = point2.x;
//                lineYEnd = point2.y;

//                lineXStart += r1;
//                lineYStart += r1;
//                lineXEnd -= r2;
//                lineYEnd -= r2;

                drawLineSegment(canvas, new LineModel(
                        new PointModel(toGraphXCoord(lineXStart), toGraphYCoord(lineYStart))
                        , new PointModel(toGraphXCoord(lineXEnd), toGraphYCoord(lineYEnd))
                        , currentCircle.getNextLineColor(), currentCircle.getNextLineType()));
                drawCircle(canvas, nextCircle);
            }
            drawCircle(canvas, currentCircle);
        }
    }

    //============>

    private float toViewXCoord(double xPoint) {
        double percent = GraphUtils.getPercentOfPointOnLine(xAxisStart.getValue(), xAxisEnd.getValue(), xPoint);
        return (float) GraphUtils.getPointFromPercentOnLine(getViewXStart(), getViewXEnd(), percent);
    }

    private float toViewYCoord(double yPoint) {
        double percent = 100 - GraphUtils.getPercentOfPointOnLine(yAxisStart.getValue(), yAxisEnd.getValue(), yPoint);
        return (float) GraphUtils.getPointFromPercentOnLine(getViewYStart(), getViewYEnd(), percent);
    }

    private float toGraphXCoord(double xValue) {
        double percent = GraphUtils.getPercentOfPointOnLine(getViewXStart(), getViewXEnd(), xValue);
        return (float) GraphUtils.getPointFromPercentOnLine(xAxisStart.getValue(), xAxisEnd.getValue(), percent);
    }

    private float toGraphYCoord(double yValue) {
        double percent = 100 - GraphUtils.getPercentOfPointOnLine(getViewYStart(), getViewYEnd(), yValue);
        return (float) GraphUtils.getPointFromPercentOnLine(yAxisStart.getValue(), yAxisEnd.getValue(), percent);
    }

    private int getViewXStart() {
        return getPaddingLeft() + xAxisLeftPadding;
    }

    private int getViewXEnd() {
        return getWidth() - getPaddingRight() - xAxisRightPadding;
    }

    private int getViewYStart() {
        return getPaddingTop() + yAxisTopPadding;
    }

    private int getViewYEnd() {
        return getHeight() - getPaddingBottom() - yAxisBottomPadding;
    }

    //============>

}
