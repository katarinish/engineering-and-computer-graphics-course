package ru.nsu.fit.g16205.kovylina.logic;

import ru.nsu.fit.g16205.kovylina.utils.Constants;

public class CustomFunction {
    private int domainA;
    private int domainB;
    private int domainC;
    private int domainD;

    private int absDomainWidth;
    private int absDomainHeight;

    private int viewWidth;
    private int viewHeight;

    private Double maxValue = null;
    private Double minValue = null;

    private double[][] values;

    public CustomFunction() {}

    public CustomFunction(int viewWidth, int viewHeight) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;

        initParameters();
    }

    public int getAbsDomainWidth() {
        return absDomainWidth;
    }

    public int getAbsDomainHeight() {
        return absDomainHeight;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public Double getMinValue() {
        return minValue;
    }

    public Double getValue(int x, int y) {
        if (x >= absDomainHeight || y >= absDomainWidth) {
            System.out.println("Out of values-array bounds!");
            return null;
        }

        return values[x][y];
    }

    public double foo(double x, double y) {
        return Math.cos(x) * Math.sin(y);
    }

    private void initParameters() {
        domainA = Constants.A;
        domainB = Constants.B;
        domainC = Constants.C;
        domainD = Constants.D;

        absDomainWidth = Math.abs(domainB - domainA) + 1;
        absDomainHeight = Math.abs(domainD - domainC) + 1;

        initFieldValues();
    }

    private void initFieldValues() {
        values = new double[viewHeight][viewWidth];
        calculateDomainValues();
    }

    private void calculateDomainValues() {
        double deltaW = absDomainWidth / (double) viewWidth;
        double deltaH = absDomainHeight / (double) viewHeight;

        for (int i = 0; i < viewHeight; ++i) {
            for (int j = 0; j < viewWidth; ++j) {
                double x = domainA + j * deltaW;
                double y = domainC + i * deltaH;

                double value = foo(x, y);
                checkIfExtremum(value);
                values[i][j] = value;
            }
        }
    }

    private void checkIfExtremum(double value) {
        if ((maxValue == null) || (value > maxValue)) maxValue = value;
        if ((minValue == null) || (value < minValue)) minValue = value;
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
        initFieldValues();
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
        initFieldValues();
    }

    public int getDomainA() {
        return domainA;
    }

    public int getDomainC() {
        return domainC;
    }

}
