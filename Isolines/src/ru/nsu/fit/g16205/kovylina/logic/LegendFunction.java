package ru.nsu.fit.g16205.kovylina.logic;

import ru.nsu.fit.g16205.kovylina.utils.Configuration;

public class LegendFunction extends CustomFunction {

    public LegendFunction(Configuration configuration, int viewWidth, int viewHeight, int n) {
        super(configuration, viewWidth, viewHeight, n);
    }

    @Override
    public double foo(double x, double y) {
        return x;
    }
}
