package ru.nsu.fit.g16205.kovylina.logic;

import ru.nsu.fit.g16205.kovylina.utils.Constants;

public class CustomFunction {
    private int domainA;
    private int domainB;
    private int domainC;
    private int domainD;

    private int absDomainWidth;
    private int absDomainHeight;

    private Double maxValue = null;
    private Double minValue = null;

    private double[][] values;

    public CustomFunction() {
        initParameters();
        calculateDomainValues();
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

    private double foo(int x, int y) {
        return Math.cos(x) * Math.sin(y);
    }

    private void initParameters() {
        domainA = Constants.A;
        domainB = Constants.B;
        domainC = Constants.C;
        domainD = Constants.D;

        absDomainWidth = Math.abs(domainB - domainA) + 1;
        absDomainHeight = Math.abs(domainD - domainC) + 1;

        values = new double[absDomainHeight][absDomainWidth];
    }

    private void calculateDomainValues() {
        for (int i = 0; i < absDomainHeight; ++i) {
            for (int j = 0; j < absDomainWidth; ++j) {
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
