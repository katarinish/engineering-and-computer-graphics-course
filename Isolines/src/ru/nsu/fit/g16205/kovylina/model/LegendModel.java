package ru.nsu.fit.g16205.kovylina.model;

import ru.nsu.fit.g16205.kovylina.utils.Constants;

import java.awt.image.BufferedImage;

public class LegendModel {
    private BufferedImage image = null;

    private int width;
    private int height;

    public LegendModel() {
        this.width = Constants.WIDTH;
        this.height = Constants.HEIGHT_LEGEND;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
