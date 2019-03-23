package ru.nsu.fit.g16205.kovylina.buisness_logic.Filters;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;
import ru.nsu.fit.g16205.kovylina.utils.ColorChannel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OrderedDithering extends Filter {

    @Override
    protected void initParameters() {
        matrix = new int [][] {
//                {0, 32, 8, 40, 2, 34, 10 ,42},
//                {48, 16, 56, 24, 50, 18, 58, 26},
//                {12, 44, 4, 36, 14, 46, 6, 38},
//                {60, 28, 52, 20, 62, 30, 54, 22},
//                {3, 35, 11, 43, 1, 33, 9, 41},
//                {51, 19, 59, 27, 49, 17, 57, 25},
//                {15, 47, 7, 39, 13, 45, 5, 37},
//                {63, 31, 55, 23, 61, 29, 53, 21}

                {0, 8, 2, 10},
                {12, 4, 14, 6},
                {3, 11, 1, 9},
                {15, 7, 13, 5}
        };
    }

    @Override
    public BufferedImage applyFilter(BufferedImage img) {
        if (matrix == null) return null;

        BufferedImage filteredImage = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < filteredImage.getHeight(); ++y) {
            for (int x = 0; x < filteredImage.getWidth(); x++) {
                Color c = new Color(img.getRGB(x, y));
                int newColor = applyMatrix(new Point(x, y), c);
                filteredImage.setRGB(x, y, newColor);
            }
        }

        return filteredImage;    }

    private int applyMatrix(Point point, Color c) {
        int RED = multiplyMatrix(point, c, ColorChannel.RED);
        int GREEN = multiplyMatrix(point, c, ColorChannel.GREEN);
        int BLUE = multiplyMatrix(point, c, ColorChannel.BLUE);

        return new Color(RED, GREEN, BLUE).getRGB();
    }

    private int multiplyMatrix(Point point, Color c, ColorChannel channel) {
        int x = point.x % kernelSize;
        int y = point.y % kernelSize;

        int matrixValue = matrix[y][x];
        int channelValue = 0;

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
        int halftones = kernelSize * kernelSize + 1;
        int normalizedValue = (channelValue / halftones)
                + (channelValue % halftones == 0 ? 0 : 1);

        return normalizedValue > matrixValue ? 255 : 0;
    }
}
