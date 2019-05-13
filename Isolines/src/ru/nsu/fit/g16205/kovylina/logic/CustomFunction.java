package ru.nsu.fit.g16205.kovylina.logic;

import ru.nsu.fit.g16205.kovylina.utils.Configuration;
import ru.nsu.fit.g16205.kovylina.utils.Constants;

public class CustomFunction {
    private Configuration configuration;
    protected int domainA;
    protected int domainB;
    protected int domainC;
    protected int domainD;

    private int absDomainWidth;
    private int absDomainHeight;

    private int viewWidth;
    private int viewHeight;

    private Double maxValue = null;
    private Double minValue = null;

    private double[][] values;

    private int n;
    private Double[] keyIsovalues = null;

    public CustomFunction(Configuration configuration,int viewWidth, int viewHeight, int n) {
        this.configuration = configuration;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;

        this.n = n;

        initParameters();
    }

    public Double getValue(int x, int y) {
        if (y >= viewWidth) {
             y = viewWidth - 1;
        }

        if (x >= viewHeight) {
            x = viewHeight - 1;
        }
        return values[x][y];
    }

    public double foo(double x, double y) {
//        return Math.sin(x) * Math.sin(y);
//                    return x * x - y * y;
//            return -(y - 0.5) * Math.abs(Math.sin(3 * Math.atan((y - 0.5) / (x - 0.5))));
        return x * Math.sin(y);

    }

    private void initParameters() {
        domainA = configuration.getA();
        domainB = configuration.getB();
        domainC = configuration.getC();
        domainD = configuration.getD();

        absDomainWidth = Math.abs(domainB - domainA) + 1;
        absDomainHeight = Math.abs(domainD - domainC) + 1;

        initFieldValues();
        calculateKeyValues();
    }

    private void initFieldValues() {
        values = new double[viewHeight][viewWidth];
        calculateDomainValues();
    }

    private void calculateKeyValues() {
        int arrLength = n + 1;
        keyIsovalues = new Double[arrLength];

        double absLength = maxValue - minValue;
        double delta = absLength / (n + 1);

        for (int i = 0; i < arrLength; ++i ) {
            keyIsovalues[i] = minValue + delta * i;
        }
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

    public int getViewWidth() {
        return viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public Double[] getKeyIsovalues() {
        return keyIsovalues;
    }
}
