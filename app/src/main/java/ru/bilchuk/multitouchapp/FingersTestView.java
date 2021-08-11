package ru.bilchuk.multitouchapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.Stack;

public class FingersTestView extends View {

    private static final float CIRCLE_RADIUS = 80f;
    private static final float STROKE_WIDTH = 12f;

    private final SparseArray<Circle> mCircles = new SparseArray<>();
    private final Stack<Integer> mColors = new Stack<>();
    private final Paint mPaint = new Paint();

    public FingersTestView(Context context) {
        this(context, null);
    }

    public FingersTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUpColors();
        setUpPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mCircles.size(); i++) {
            Circle circle = mCircles.valueAt(i);
            mPaint.setColor(circle.getColor());
            canvas.drawCircle(circle.getOrigin().x, circle.getOrigin().y, circle.getRadius(), mPaint);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        Circle currentCircle;

        switch(actionMasked) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                PointF currentPoint = new PointF(event.getX(pointerIndex), event.getY(pointerIndex));
                currentCircle = new Circle(currentPoint, CIRCLE_RADIUS, getNextColor());
                mCircles.put(pointerId, currentCircle);
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    int id = event.getPointerId(i);
                    currentCircle = mCircles.get(id);
                    currentCircle.setOrigin(new PointF(event.getX(i), event.getY(i)));
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                currentCircle = mCircles.get(pointerId);
                returnColor(currentCircle.getColor());
                mCircles.delete(pointerId);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setUpColors();
                mCircles.clear();
                break;
        }
        invalidate();
        return true;
    }

    private void setUpColors() {
        mColors.clear();
        mColors.addAll(Arrays.asList(
                Color.GRAY,
                Color.DKGRAY,
                Color.LTGRAY,
                Color.BLACK,
                Color.YELLOW,
                Color.CYAN,
                Color.RED,
                Color.BLUE,
                Color.MAGENTA,
                Color.GREEN
        ));
    }

    private void setUpPaint() {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(STROKE_WIDTH);
        mPaint.setAntiAlias(true);
    }

    private int getNextColor() {
        Integer value = mColors.pop();
        if (value == null) {
            return Color.BLACK;
        } else {
            return value;
        }
    }

    private void returnColor(int color) {
        mColors.push(color);
    }
}
