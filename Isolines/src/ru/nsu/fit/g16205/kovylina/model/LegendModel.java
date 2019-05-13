package ru.nsu.fit.g16205.kovylina.model;

import ru.nsu.fit.g16205.kovylina.logic.LegendFunction;
import ru.nsu.fit.g16205.kovylina.utils.Configuration;
import ru.nsu.fit.g16205.kovylina.utils.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class LegendModel extends MapModel {
    private BufferedImage keyValuesImage = null;
    private Double[] keyIsovalues;

    public LegendModel(Configuration configuration, Double[] keyIsovalues) {
        this.keyIsovalues = keyIsovalues;
        this.configuration = configuration;

        this.width = Constants.WIDTH;
        this.height = Constants.HEIGHT_LEGEND;

        this.n = configuration.getN();
        this.colors = configuration.getColors();

        initParameters();
    }

    public BufferedImage getKeyValuesImage() {
        return keyValuesImage;
    }

    @Override
    public void setWidth(int width) {
        if (width == this.width) return;

        this.width = width;
        updateImages();
    }

    @Override
    public void setHeight(int height) {
        if (height == this.height) return;

        this.height = height;
        updateImages();
    }

    @Override
    protected void initParameters() {
        function = new LegendFunction(configuration, width, height, n);
        initMapImage();
        initInterpolatedImage();
        initKeyValuesImage();
    }

    private void initKeyValuesImage() {
        keyValuesImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d= (Graphics2D) keyValuesImage.getGraphics();

        int deltaWidth = width / keyIsovalues.length;
        for (int i = 1; i < keyIsovalues.length; ++i) {

            int x = i * deltaWidth;

            DecimalFormat df = new DecimalFormat("###.###");
            g2d.drawString(df.format(keyIsovalues[i]), x - 7, 15);
        }
    }

    @Override
    protected void updateImages() {
        function.setViewHeight(this.height);
        function.setViewWidth(this.width);

        if (state.isWithInterpolation()) {
            initInterpolatedImage();
        } else {
            initMapImage();
        }

        initKeyValuesImage();
    }
}
