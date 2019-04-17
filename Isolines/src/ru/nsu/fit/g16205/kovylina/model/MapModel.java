package ru.nsu.fit.g16205.kovylina.model;

import ru.nsu.fit.g16205.kovylina.logic.CustomFunction;
import ru.nsu.fit.g16205.kovylina.utils.Constants;

import java.awt.image.BufferedImage;

public class MapModel {
    private CustomFunction function = new CustomFunction();

    private BufferedImage image = null;

    private int width;
    private int height;

    public MapModel() {
        this.width = Constants.WIDTH;
        this.height = Constants.HEIGHT_MAP;
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
