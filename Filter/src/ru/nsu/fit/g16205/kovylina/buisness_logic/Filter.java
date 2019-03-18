package ru.nsu.fit.g16205.kovylina.buisness_logic;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Filter {
    private int[][] matrix = null;
    private int kernelSize = 50;

    public Filter() {
    }

    public BufferedImage applyMatrix(BufferedImage img) {
        if (matrix == null) return null;

        BufferedImage filteredImage = getExtendedImage(img);
        return filteredImage;
    }


    private BufferedImage getExtendedImage(BufferedImage image) {
        int extension= (kernelSize / 2);
        BufferedImage extendedImage = new BufferedImage(image.getWidth() + extension * 2,
                image.getHeight() + extension * 2 , BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = extendedImage.createGraphics();

        //Drawing an existing part
        g2d.drawImage(image, extension, extension, null);

        //Filling up extended lines
        for (int i = 0; i < extension; ++i) {
            // Top
            g2d.drawImage(
                    image.getSubimage(0, 0, image.getWidth(), 1),
                    extension, i, null);

            // Bottom
            g2d.drawImage(
                    image.getSubimage(0, image.getHeight() - 1, image.getWidth(), 1),
                    extension, extendedImage.getHeight() - 1 - i, null);

            //Left
            g2d.drawImage(
                    image.getSubimage(0, 0, 1, image.getHeight()),
                    i, extension, null);

            //Right
            g2d.drawImage(
                    image.getSubimage(image.getWidth() - 1, 0,
                            1, image.getHeight()),
                    extendedImage.getWidth() - 1 - i, extension, null);
        }

        // Filling up corners
        for (int i = 0; i < extension; ++i) {
            // Top left corner
            g2d.drawImage(
                    extendedImage.getSubimage(extension, 0, 1, extension),
                    i, 0, null);

            // Top right corner
            g2d.drawImage(
                    extendedImage.getSubimage(extendedImage.getWidth() - 1 - extension, 0,
                            1, extension),
                    extendedImage.getWidth() - extension + i, 0,
                    1, extension, null);

            //Left bottom corner
            g2d.drawImage(
                    extendedImage.getSubimage(extension, extendedImage.getHeight() - 1 - extension,
                            1, extension),
                    i, extendedImage.getHeight() - extension,
                    1, extension, null);

            // Right bottom corner
            g2d.drawImage(
                    extendedImage.getSubimage(
                            extendedImage.getWidth() - 1 - extension,
                            extendedImage.getHeight() - 1 - extension,
                            1, extension),
                    extendedImage.getWidth() - extension + i,
                    extendedImage.getHeight() - extension,
                    1,  extension, null);
        }

        g2d.dispose();
        return extendedImage;
    }
}
