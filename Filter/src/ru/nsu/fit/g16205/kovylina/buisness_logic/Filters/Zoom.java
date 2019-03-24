package ru.nsu.fit.g16205.kovylina.buisness_logic.Filters;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;

import java.awt.image.BufferedImage;

public class Zoom extends Filter {

    @Override
    public BufferedImage applyFilter(BufferedImage img) {
        if (matrix == null) return null;

        BufferedImage filteredImage = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < filteredImage.getHeight(); y += 2) {
            for (int x = 0; x < filteredImage.getWidth(); x += 2) {

                int shiftedX = img.getWidth() / 4 + x / 2;
                int shiftedY = img.getHeight() / 4 + y / 2;
                int color = img.getRGB(shiftedX, shiftedY);

                for (int i = 0; i < 2; ++i) {
                    for (int j = 0; j < 2; ++j) {
                        filteredImage.setRGB(x + i, y + j, color);
                    }
                }
            }
        }

        return filteredImage;
    }
}
