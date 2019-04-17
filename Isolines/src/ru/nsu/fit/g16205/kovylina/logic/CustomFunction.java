package ru.nsu.fit.g16205.kovylina.logic;

import ru.nsu.fit.g16205.kovylina.utils.Constants;

public class CustomFunction {
    private int domainA;
    private int domainB;
    private int domainC;
    private int domainD;

    private int absWidth;
    private int absHeight;

    private Double maxValue = null;
    private Double minValue = null;

    private double[][] values;

    public CustomFunction() {
        initParameters();
        calculateDomainValues();
    }

    public int getAbsWidth() {
        return absWidth;
    }

    public int getAbsHeight() {
        return absHeight;
    }

    private double foo(int x, int y) {
        return Math.cos(x) * Math.sin(y);
    }

    private void initParameters() {
        domainA = Constants.A;
        domainB = Constants.B;
        domainC = Constants.C;
        domainD = Constants.D;

        absWidth = Math.abs(domainB - domainA) + 1;
        absHeight = Math.abs(domainD - domainC) + 1;

        values = new double[absHeight][absWidth];
    }

    private void calculateDomainValues() {
        for (int i = 0; i < absHeight; ++i) {
            for (int j = 0; j < absWidth; ++j) {
                int x = domainA + j;
                int y = domainC + i;

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

}
