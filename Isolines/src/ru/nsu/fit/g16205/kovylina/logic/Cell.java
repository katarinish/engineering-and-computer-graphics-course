package ru.nsu.fit.g16205.kovylina.logic;

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
}
