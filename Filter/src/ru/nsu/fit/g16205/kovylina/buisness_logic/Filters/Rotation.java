package ru.nsu.fit.g16205.kovylina.buisness_logic.Filters;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Rotation extends Filter {
    private double angle = 45.0;

    @Override
    public BufferedImage applyFilter(BufferedImage img) {
        if (matrix == null) return null;

        BufferedImage filteredImage = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = filteredImage.createGraphics();

        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(angle),
                (double) img.getWidth() / 2, (double) img.getHeight() / 2);

        g2d.setTransform(at);
        g2d.drawImage(img, 0 , 0, null);

        g2d.dispose();

        return filteredImage;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
