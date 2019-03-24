package ru.nsu.fit.g16205.kovylina.model;

import ru.nsu.fit.g16205.kovylina.utils.ImageZoneModel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScaledZoneModel extends ImageZoneModel {
    public ScaledZoneModel() {
        super();
    }

    public void createImage(BufferedImage image, Point point) {
        BufferedImage scaledImage;

        if ((point.x + width > image.getWidth())
            || (point.y + height > image.getHeight())){
            int diffX = (point.x + width) - image.getWidth();
            int diffY = (point.y + height) - image.getHeight();

            int overheadX = diffX > 0 ? diffX : 0;
            int overheadY = diffY > 0 ? diffY : 0;

            scaledImage = image.getSubimage(point.x - overheadX, point.y - overheadY,
                    width, height);
        } else {
            scaledImage = image.getSubimage(point.x, point.y,
                    width, height);
        }

        setImage(scaledImage);
    }
}
