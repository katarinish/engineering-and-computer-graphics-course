package ru.nsu.fit.g16205.kovylina.buisness_logic;

import ru.nsu.fit.g16205.kovylina.utils.ColorChannel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Filter {
    protected int[][] matrix = null;
    protected int kernelSize ;
    protected double div = 1;
    protected int extension;

    public Filter() {
        initParameters();

        kernelSize = matrix.length;
        extension = (kernelSize /2);
    }

    public BufferedImage applyFilter(BufferedImage img) {
        if (matrix == null) return null;

        BufferedImage filteredImage = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        BufferedImage extendedImage = getExtendedImage(img);

        for (int y = 0; y < filteredImage.getHeight(); ++y) {
            for (int x = 0; x < filteredImage.getWidth(); x++) {
                BufferedImage subImage = getSubImage(extendedImage,
                        new Point(x + extension, y + extension));
                int newColor = applyMatrix(subImage);
                filteredImage.setRGB(x, y, newColor);
            }
        }

        return filteredImage;
    }

    protected void initParameters() {
        matrix = new int[][] {
                {0, 0 , 0},
                {0, 1, 0},
                {0, 0, 0}};
    }

    private BufferedImage getExtendedImage(BufferedImage image) {
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

    private BufferedImage getSubImage(BufferedImage img, Point pixel) {
        return img.getSubimage(pixel.x - extension, pixel.y - extension,
                kernelSize, kernelSize);
    }

    protected int applyMatrix(BufferedImage subImage) {

        int RED = multiplyMatrix(subImage, ColorChannel.RED);
        int GREEN = multiplyMatrix(subImage, ColorChannel.GREEN);
        int BLUE = multiplyMatrix(subImage, ColorChannel.BLUE);

        return new Color(RED, GREEN, BLUE).getRGB();
    }

    protected int multiplyMatrix(BufferedImage subImage, ColorChannel channel) {
        int sum = 0;

        for (int y = 0; y < kernelSize; ++y) {
            for (int x = 0; x < kernelSize; ++x) {
                int channelValue = 0;
                Color c = new Color(subImage.getRGB(x, y));

                switch (channel) {
                    case RED:
                        channelValue = c.getRed();
                        break;
                    case BLUE:
                        channelValue = c.getBlue();
                        break;
                    case GREEN:
                        channelValue = c.getGreen();
                        break;
                }

                sum += channelValue * matrix[y][x];
            }
        }

        sum = (int)(sum * (1 / div));

        return validateColorScale(sum);
    }

    protected int validateColorScale(int color) {
        color = color > 255 ? 255 : color;
        color = color < 0 ? 0 : color;

        return color;
    }
}
