package ru.nsu.fit.g16205.kovylina.buisness_logic.Filters;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;
import ru.nsu.fit.g16205.kovylina.utils.ColorChannel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Gamma extends Filter {
    double gamma = 1.3;

    @Override
    public BufferedImage applyFilter(BufferedImage img) {
        if (matrix == null) return null;

        BufferedImage filteredImage = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        BufferedImage extendedImage = getExtendedImage(img);

        for (int y = 0; y < filteredImage.getHeight(); ++y) {
            for (int x = 0; x < filteredImage.getWidth(); x++) {
                int newColor = applyMatrix(new Point(x + extension, y + extension), extendedImage);
                filteredImage.setRGB(x, y, newColor);
            }
        }

        return filteredImage;
    }

    private int applyMatrix(Point point, BufferedImage extendedImage) {
        int RED = multiplyMatrix(point, extendedImage, ColorChannel.RED);
        int GREEN = multiplyMatrix(point, extendedImage, ColorChannel.GREEN);
        int BLUE = multiplyMatrix(point, extendedImage, ColorChannel.BLUE);

        return new Color(RED, GREEN, BLUE).getRGB();
    }

    private int multiplyMatrix(Point point, BufferedImage img, ColorChannel channel) {
        Color c = new Color(img.getRGB(point.x, point.y));

        double channelValue = getChannelColor(c, channel);
        int newChannelValue = (int) (255 * (Math.pow(channelValue / 255, gamma)));

        return validateColorScale(newChannelValue);
    }
}
