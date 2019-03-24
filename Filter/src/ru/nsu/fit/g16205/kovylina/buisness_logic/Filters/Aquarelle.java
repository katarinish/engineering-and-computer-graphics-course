package ru.nsu.fit.g16205.kovylina.buisness_logic.Filters;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;
import ru.nsu.fit.g16205.kovylina.utils.ColorChannel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class Aquarelle extends Filter {

    @Override
    protected void initParameters() {
        matrix = new int[][] {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}};
    }

    @Override
    public BufferedImage applyFilter(BufferedImage img) {
        BufferedImage medianFilteredImg = super.applyFilter(img);
        return new Sharp().applyFilter(medianFilteredImg);
    }

    @Override
    protected int multiplyMatrix(BufferedImage subImage, ColorChannel channel) {
        ArrayList<Integer> arrayList = new ArrayList<>();

        for (int y = 0; y < kernelSize; ++y) {
            for (int x = 0; x < kernelSize; ++x) {
                int channelValue = 0;
                Color c = new Color(subImage.getRGB(x, y));

                channelValue = getChannelColor(c, channel);

                arrayList.add(channelValue);
            }
        }

        int length = kernelSize * kernelSize;
        int medium = length / 2 + (length % 2);
        Collections.sort(arrayList);

        int mColor = arrayList.get(medium);
        if (medium % 2 == 0) {
            mColor = (mColor + arrayList.get(medium + 1)) / 2;
        }

        return mColor;
    }
}
