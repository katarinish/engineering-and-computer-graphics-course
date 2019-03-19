package ru.nsu.fit.g16205.kovylina.buisness_logic.Filters;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;

import java.awt.image.BufferedImage;

public class Contour extends Filter {

    @Override
    protected void initParameters() {
        matrix = new int[][] {
                {0, -1, 0},
                {-1, 4, -1},
                {0, -1, 0}};
    }

    @Override
    public BufferedImage applyFilter(BufferedImage img) {
        BufferedImage bwImage = new BlackAndWhite().applyFilter(img);
        return super.applyFilter(bwImage);
    }
}
