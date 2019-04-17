package ru.nsu.fit.g16205.kovylina.model;

import ru.nsu.fit.g16205.kovylina.controller.Grid;
import ru.nsu.fit.g16205.kovylina.logic.CustomFunction;
import ru.nsu.fit.g16205.kovylina.utils.Constants;

import java.awt.image.BufferedImage;

public class MapModel {
    private CustomFunction function = null;

    private BufferedImage mapImage = null;
    private BufferedImage isolinesImage = null;
    private BufferedImage gridImage = null;

    private int width;
    private int height;

    // Grid size
    private int k;
    private int m;
    private Grid grid = null;

    //Number of isovalues
    private int n;
    private double[] keyIsovalues = null;

    public MapModel() {
        this.width = Constants.WIDTH;
        this.height = Constants.HEIGHT_MAP;

        this.k = Constants.K;
        this.m = Constants.M;

        this.n = Constants.N;

        initParameters();
    }

    public BufferedImage getMapImage() {
        return mapImage;
    }

    public BufferedImage getIsolinesImage() {
        return isolinesImage;
    }

    public BufferedImage getGridImage() {
        return gridImage;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    private void initParameters() {
        function = new CustomFunction();
        grid = new Grid(k - 1, m - 1, function);

        calculateKeyValues();
        initIsolinesImage();
    }

    private void initIsolinesImage() {
        isolinesImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

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
