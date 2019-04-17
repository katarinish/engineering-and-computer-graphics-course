package ru.nsu.fit.g16205.kovylina.model;

import ru.nsu.fit.g16205.kovylina.logic.CustomFunction;
import ru.nsu.fit.g16205.kovylina.utils.Constants;

import java.awt.image.BufferedImage;

public class MapModel {
    private CustomFunction function = new CustomFunction();

    private BufferedImage map = null;
    private BufferedImage isolines = null;
    private BufferedImage grid = null;

    private int width;
    private int height;

    // Grid size
    private int k;
    private int m;

    //Number of isovalues
    private int n;
    private double[] keyIsovalues;

    public MapModel() {
        this.width = Constants.WIDTH;
        this.height = Constants.HEIGHT_MAP;

        this.k = Constants.K;
        this.m = Constants.M;

        this.n = Constants.N;

        initParameters();
    }

    public BufferedImage getMapImage() {
        return map;
    }

    public BufferedImage getIsolinesImage() {
        return isolines;
    }

    public BufferedImage getGridImage() {
        return grid;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    private void initParameters() {
        calculateKeyValues();
    }

    private void calculateKeyValues() {
        int arrLength = n + 1;
        keyIsovalues = new double[arrLength];

        double absLength = function.getMaxValue() - function.getMinValue();
        double delta = absLength / (n + 1);
        double startValue = function.getMinValue();

        for (int i = 0; i < arrLength; ++i ) {
            keyIsovalues[i] = startValue + delta * i;
        }
    }
}
