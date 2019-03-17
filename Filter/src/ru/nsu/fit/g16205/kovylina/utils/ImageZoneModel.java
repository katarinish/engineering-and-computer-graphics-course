package ru.nsu.fit.g16205.kovylina.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageZoneModel {
    protected BufferedImage image = null;

    protected int width;
    protected int height;

    public ImageZoneModel() {
        width = Constants.ZONE_WIDTH;
        height = Constants.ZONE_HEIGHT;
    }

    public boolean isInImage(Point p) {
        if (image == null) return false;

        return (p.x <= image.getWidth() && (p.y <= image.getHeight()));
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

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
