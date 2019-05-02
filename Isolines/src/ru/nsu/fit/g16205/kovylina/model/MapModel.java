package ru.nsu.fit.g16205.kovylina.model;

import ru.nsu.fit.g16205.kovylina.logic.Grid;
import ru.nsu.fit.g16205.kovylina.logic.CustomFunction;
import ru.nsu.fit.g16205.kovylina.utils.Constants;

import java.awt.*;
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
    private Color[] colors = null;

    public MapModel() {
        this.width = Constants.WIDTH;
        this.height = Constants.HEIGHT_MAP;

        this.k = Constants.K;
        this.m = Constants.M;

        this.n = Constants.N;
        this.colors = Constants.COLORS;

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

    public void setWidth(int width) {
        if (width == this.width) return;

        this.width = width;
        updateImages();
    }

    public void setHeight(int height) {
        if (height == this.height) return;

        this.height = height;
        updateImages();
    }

    private void initParameters() {
        function = new CustomFunction(width, height);
        grid = new Grid(k - 1, m - 1, function);

        calculateKeyValues();
        initMapImage();
        initGridImage();
    }

    private void initMapImage() {
        mapImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < mapImage.getHeight(); ++x) {
            for (int y = 0; y < mapImage.getWidth(); ++y) {
                double value = function.getValue(x, y);
                int color = getColor(value);
                mapImage.setRGB(y, x, color);
            }
        }
    }

    private void initGridImage() {
        gridImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) gridImage.getGraphics();

        int deltaX = width / k;
        int deltaY = height / m;
        int imageWidth = gridImage.getWidth();
        int imageHeight = gridImage.getHeight();

        for (int x = 0; x < imageWidth; x += deltaX) {
            g.drawLine(x, 0, x, imageHeight);
        }
        for (int y = 0; y < imageHeight; y += deltaY) {
            g.drawLine(0, y, imageWidth, y);
        }
    }

    private int getColor(double value) {
        for (int i = 1; i < keyIsovalues.length; ++i) {
            if (keyIsovalues[i] > value) return colors[i - 1].getRGB();
        }

        return colors[keyIsovalues.length - 1].getRGB();
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

    private void updateImages() {
        function.setViewHeight(this.height);
        function.setViewWidth(this.width);

        initMapImage();
        initGridImage();
    }
}
