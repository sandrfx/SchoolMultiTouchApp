package ru.bilchuk.multitouchapp;

import android.graphics.PointF;

public class Circle {

    private PointF mOrigin;
    private float mRadius;
    private int mColor;

    public Circle(PointF origin, float radius, int color) {
        mOrigin = origin;
        mRadius = radius;
        mColor = color;
    }

    public PointF getOrigin() {
        return mOrigin;
    }

    public void setOrigin(PointF origin) {
        mOrigin = origin;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float radius) {
        mRadius = radius;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }
}
