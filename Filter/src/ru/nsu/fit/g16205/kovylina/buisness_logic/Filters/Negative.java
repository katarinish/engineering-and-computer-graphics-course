package ru.nsu.fit.g16205.kovylina.buisness_logic.Filters;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Negative extends Filter {
    public Negative() {}

    @Override
    public BufferedImage applyFilter(BufferedImage img) {
        BufferedImage filteredImage = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < img.getHeight(); ++y) {
            for (int x = 0; x < img.getWidth(); ++x) {
                Color color = new Color(img.getRGB(x, y));

                int red = 255 - color.getRed();
                int green = 255 - color.getGreen();
                int blue = 255 - color.getBlue();

                Color newColor = new Color(red, green, blue);
                filteredImage.setRGB(x, y , newColor.getRGB());
            }
        }

        return filteredImage;
    }
}
