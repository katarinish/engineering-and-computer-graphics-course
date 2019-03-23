package ru.nsu.fit.g16205.kovylina.buisness_logic.Filters;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;
import ru.nsu.fit.g16205.kovylina.utils.ColorChannel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SobelOperator extends Filter {

    @Override
    public BufferedImage applyFilter(BufferedImage img) {
        return super.applyFilter(new BlackAndWhite().applyFilter(img));
    }

    @Override
    protected void initParameters() {
        matrix = new int[][] {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
    }

    @Override
    protected int multiplyMatrix(BufferedImage subImage, ColorChannel channel) {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int f = 0;
        int g = 0;
        int h = 0;
        int i = 0;

        switch (channel) {
            case RED:
                a = new Color(subImage.getRGB(0, 0)).getRed();
                b = new Color(subImage.getRGB(0, 1)).getRed();
                c = new Color(subImage.getRGB(0, 2)).getRed();
                d = new Color(subImage.getRGB(1, 0)).getRed();
                f = new Color(subImage.getRGB(1, 2)).getRed();
                g = new Color(subImage.getRGB(2, 0)).getRed();
                h = new Color(subImage.getRGB(2, 1)).getRed();
                i = new Color(subImage.getRGB(2, 2)).getRed();

                break;
            case BLUE:
                a = new Color(subImage.getRGB(0, 0)).getBlue();
                b = new Color(subImage.getRGB(0, 1)).getBlue();
                c = new Color(subImage.getRGB(0, 2)).getBlue();
                d = new Color(subImage.getRGB(1, 0)).getBlue();
                f = new Color(subImage.getRGB(1, 2)).getBlue();
                g = new Color(subImage.getRGB(2, 0)).getBlue();
                h = new Color(subImage.getRGB(2, 1)).getBlue();
                i = new Color(subImage.getRGB(2, 2)).getBlue();

                break;
            case GREEN:
                a = new Color(subImage.getRGB(0, 0)).getGreen();
                b = new Color(subImage.getRGB(0, 1)).getGreen();
                c = new Color(subImage.getRGB(0, 2)).getGreen();
                d = new Color(subImage.getRGB(1, 0)).getGreen();
                f = new Color(subImage.getRGB(1, 2)).getGreen();
                g = new Color(subImage.getRGB(2, 0)).getGreen();
                h = new Color(subImage.getRGB(2, 1)).getGreen();
                i = new Color(subImage.getRGB(2, 2)).getGreen();

                break;
        }

        double Sx = (c + 2 * f + i) - (a + 2 * d + g);
        double Sy = (g + 2 * h + i) - (a + 2 * b + c);

        int res = (int) Math.sqrt(Sx * Sx + Sy * Sy);

        return validateColorScale(res);
    }

}
