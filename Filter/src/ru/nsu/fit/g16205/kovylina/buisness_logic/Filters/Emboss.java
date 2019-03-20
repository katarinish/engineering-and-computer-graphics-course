package ru.nsu.fit.g16205.kovylina.buisness_logic.Filters;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;
import ru.nsu.fit.g16205.kovylina.utils.ColorChannel;

import java.awt.image.BufferedImage;

public class Emboss extends Filter {

    @Override
    protected void initParameters() {
        matrix = new int[][] {
                {0, -1, 0},
                {1, 0, -1},
                {0, 1, 0}};
    }

    @Override
    protected int multiplyMatrix(BufferedImage subImage, ColorChannel channel) {
        int kernelSum = super.multiplyMatrix(subImage, channel) + 128;

        return validateColorScale(kernelSum);
    }
}
