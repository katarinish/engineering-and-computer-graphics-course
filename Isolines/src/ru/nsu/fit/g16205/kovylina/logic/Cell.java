package ru.nsu.fit.g16205.kovylina.logic;

import java.awt.*;
import java.util.ArrayList;

public class Cell {
    private double leftTopCorner;
    private double rightTopCorner;
    private double rightBottomCorner;
    private double leftBottomCorner;

    public double getLeftTopCorner() {
        return leftTopCorner;
    }

    public double getRightTopCorner() {
        return rightTopCorner;
    }

    public double getRigthBottomCorner() {
        return rightBottomCorner;
    }

    public double getLeftBottomCorner() {
        return leftBottomCorner;
    }

    public void setLeftTopCorner(double leftTopCorner) {
        this.leftTopCorner = leftTopCorner;
    }

    public void setRightTopCorner(double rightTopCorner) {
        this.rightTopCorner = rightTopCorner;
    }

    public void setRigthBottomCorner(double rigthBottomCorner) {
        this.rightBottomCorner = rigthBottomCorner;
    }

    public void setLeftBottomCorner(double leftBottomCorner) {
        this.leftBottomCorner = leftBottomCorner;
    }

    public boolean isSignumCenter(double isovalue) {
        double signumCenter = Math.signum(leftTopCorner + rightTopCorner
                + rightBottomCorner + leftTopCorner);

        return signumCenter == Math.signum(leftTopCorner - isovalue);

    }

    public ArrayList<Point> getCrossingPoint(double isovalue, int dx, int dy) {
        ArrayList<Point> intersects = new ArrayList<>();

        if (isCrossedWithIsoline(isovalue, leftTopCorner, rightTopCorner)) {
            double x = dx * (isovalue - leftTopCorner) / (rightTopCorner - leftTopCorner);
            intersects.add(new Point((int) x, 0));
        }

        if (isCrossedWithIsoline(isovalue, rightTopCorner, rightBottomCorner)) {
            double y = dy * (isovalue - rightTopCorner) / (rightBottomCorner - rightTopCorner);
            intersects.add(new Point(dx,(int) y));
        }

        if (isCrossedWithIsoline(isovalue, rightBottomCorner, leftBottomCorner)) {
            double x = dx * (isovalue - rightBottomCorner) / (leftBottomCorner - rightBottomCorner);
            intersects.add(new Point((int) x, dy));
        }

        if (isCrossedWithIsoline(isovalue, leftBottomCorner, leftTopCorner)) {
            double y = dy * (isovalue - leftBottomCorner) / (leftTopCorner - leftBottomCorner);
            intersects.add(new Point(0, (int) y));
        }

        return intersects;
    }

    private boolean isCrossedWithIsoline(double isovalue, double f1, double f2) {
        return f1 <= isovalue && isovalue <= f2
                || f2 <= isovalue && isovalue <= f1;
    }
}
