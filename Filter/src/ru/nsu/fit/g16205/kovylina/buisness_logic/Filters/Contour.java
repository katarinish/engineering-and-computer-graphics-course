package ru.nsu.fit.g16205.kovylina.buisness_logic.Filters;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;

import java.awt.image.BufferedImage;

public class Contour extends Filter {

    int c = 30;

    @Override
    protected void initParameters() {
        matrix = new int[][] {
                {0, -1, 0},
                {-1, 4, -1},
                {0, -1, 0}};

//        matrix = new int[][] {
//                {-1, 0, 1},
//                {-2, 0, 2},
//                {-1, 0, 1}};
//
//        div = 4;
    }

    @Override
    public BufferedImage applyFilter(BufferedImage img) {
        BufferedImage bwImage = new BlackAndWhite().applyFilter(img);
        return super.applyFilter(bwImage);
    }

    @Override
    protected int validateColorScale(int color) {
        return color > c ? 255 : 0;
    }

    public void setC(int c) {
        this.c = c;
    }
}
