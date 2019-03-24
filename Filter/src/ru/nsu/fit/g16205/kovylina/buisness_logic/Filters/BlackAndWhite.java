package ru.nsu.fit.g16205.kovylina.buisness_logic.Filters;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlackAndWhite extends Filter {

    public BlackAndWhite() {}

    @Override
    public BufferedImage applyFilter(BufferedImage img) {
        BufferedImage filteredImage = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < img.getHeight(); ++y) {
            for (int x = 0; x < img.getWidth(); ++x) {
                Color color = new Color(img.getRGB(x, y));

                int red = (int) (color.getRed() * 0.299);
                int green = (int) (color.getGreen() * 0.587);
                int blue = (int) (color.getBlue() * 0.114);

                int grayScale = red + green + blue;
                grayScale = validateColorScale(grayScale);

                Color newColor = new Color(grayScale, grayScale, grayScale);
                filteredImage.setRGB(x, y , newColor.getRGB());
            }
        }

        return filteredImage;
    }
}
