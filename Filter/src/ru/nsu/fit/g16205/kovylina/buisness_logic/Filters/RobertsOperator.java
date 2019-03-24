package ru.nsu.fit.g16205.kovylina.buisness_logic.Filters;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;
import ru.nsu.fit.g16205.kovylina.utils.ColorChannel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RobertsOperator extends Filter {
    private int c = 20;

    public RobertsOperator() {
    }

    @Override
    public BufferedImage applyFilter(BufferedImage img) {
        return super.applyFilter(new BlackAndWhite().applyFilter(img));
    }

    @Override
    protected void initParameters() {
        matrix = new int[][] {
                {1, -1},
                {-1, 1}
        };
    }

    @Override
    protected BufferedImage getSubImage(BufferedImage img, Point pixel) {
        return img.getSubimage(pixel.x, pixel.y,
                kernelSize, kernelSize);
    }

    @Override
    protected int multiplyMatrix(BufferedImage subImage, ColorChannel channel) {
        int ij  = 0;
        int ij1 = 0;
        int i1j = 0;
        int i1j1 = 0;

        switch (channel) {
            case RED:
                ij = new Color(subImage.getRGB(0, 0)).getRed();
                ij1 = new Color(subImage.getRGB(0, 1)).getRed();
                i1j = new Color(subImage.getRGB(1, 0)).getRed();
                i1j1 = new Color(subImage.getRGB(1, 1)).getRed();
                break;
            case BLUE:
                ij = new Color(subImage.getRGB(0, 0)).getBlue();
                ij1 = new Color(subImage.getRGB(0, 1)).getBlue();
                i1j = new Color(subImage.getRGB(1, 0)).getBlue();
                i1j1 = new Color(subImage.getRGB(1, 1)).getBlue();
                break;
            case GREEN:
                ij = new Color(subImage.getRGB(0, 0)).getGreen();
                ij1 = new Color(subImage.getRGB(0, 1)).getGreen();
                i1j = new Color(subImage.getRGB(1, 0)).getGreen();
                i1j1 = new Color(subImage.getRGB(1, 1)).getGreen();
                break;
        }

        int s1 = ij - i1j1;
        int s2 = i1j - ij1;

        double sum = s1 * s1 + s2 * s2;
        int res = (int) Math.sqrt(sum);

        return validateColorScale(res);
    }

    @Override
    protected int validateColorScale(int color) {
        return  color > c ? 0 : 255;
    }

    public void setC(int c) {
        this.c = c;
    }
}
