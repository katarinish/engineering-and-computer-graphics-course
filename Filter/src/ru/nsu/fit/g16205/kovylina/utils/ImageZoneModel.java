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

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = validateImageSize(image);
    }

    private BufferedImage validateImageSize(BufferedImage bi) {
        if (bi.getWidth() <= width && bi.getHeight() <= height) return bi;

        Dimension imageSize = new Dimension(bi.getWidth(), bi.getHeight());
        Dimension boundary = new Dimension(width, height);

        Dimension scaledDImension = FileUtils.getScaledDimension(imageSize, boundary);

        Image scaledImage = bi.getScaledInstance(
                scaledDImension.width, scaledDImension.height, Image.SCALE_SMOOTH);

        return FileUtils.toBufferedImage(scaledImage);
    }
}
