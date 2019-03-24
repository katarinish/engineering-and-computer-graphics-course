package ru.nsu.fit.g16205.kovylina.buisness_logic.Filters;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;
import ru.nsu.fit.g16205.kovylina.utils.ColorChannel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FloydDithering extends Filter {
    int paletteFactor = 1;

    double redError = 0;
    double greenError = 0;
    double blueError = 0;


    @Override
    public BufferedImage applyFilter(BufferedImage img) {
        if (matrix  == null) return null;

        BufferedImage filteredImage = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
//        BufferedImage extendedImage = new BlackAndWhite().applyFilter(getExtendedImage(img));
        BufferedImage extendedImage = getExtendedImage(img);

        for (int y = 0; y < filteredImage.getHeight(); ++y) {
            for (int x = 0; x < filteredImage.getWidth(); ++x) {
                int newColor = applyMatrix(new Point(x + extension, y + extension),
                        extendedImage);
                filteredImage.setRGB(x, y, newColor);
        }
    }

    return filteredImage;
}

    private int applyMatrix(Point point, BufferedImage extendedImage) {
        int RED = multiplyMatrix(point, extendedImage, ColorChannel.RED);
        int GREEN = multiplyMatrix(point, extendedImage, ColorChannel.GREEN);
        int BLUE = multiplyMatrix(point, extendedImage, ColorChannel.BLUE);

        diffuseError(point, extendedImage);

        return new Color(RED, GREEN, BLUE).getRGB();
    }

    private int multiplyMatrix(Point point, BufferedImage image, ColorChannel channel) {
        Color oldColor = new Color(image.getRGB(point.x, point.y));

        double oldChannelColor = getChannelColor(oldColor, channel);
        int newChannelColor = (int) Math.round(paletteFactor * oldChannelColor / 255) * (255 / paletteFactor);

        double channelError = oldChannelColor - newChannelColor;
        setChannelError(channelError, channel);

        return newChannelColor;
    }

    private void diffuseError(Point p, BufferedImage img) {
        diffuse(p, img, 1);
        diffuse(p, img, 3);
        diffuse(p, img, 5);
        diffuse(p, img, 7);
    }

    private void diffuse(Point p, BufferedImage img, int numerator) {
        int x;
        int y;
        switch (numerator) {
            case 1:
                x = p.x + 1;
                y = p.y + 1;

                break;
            case 3:
                x = p.x - 1;
                y = p.y + 1;

                break;
            case 5:
                x = p.x;
                y = p.y + 1;

                break;
            case 7:
                x = p.x + 1;
                y = p.y;

                break;
            default:
                System.out.println("Wrong arguments");
                return;
        }

        Color c = new Color (img.getRGB(x, y));
        int red = c.getRed();
        int green = c.getGreen();
        int blue = c.getBlue();

        red =  validateColorScale(red + (int) Math.round(redError * numerator / 16));
        green =  validateColorScale(green + (int) Math.round(greenError * numerator / 16));
        blue =  validateColorScale(blue + (int) Math.round(blueError * numerator / 16));

        img.setRGB(x, y, new Color(red, green, blue).getRGB());
    }

    private void setChannelError(double error, ColorChannel channel) {
        switch (channel) {
            case RED:
                redError = error;
                break;
            case BLUE:
                blueError = error;
                break;
            case GREEN:
                greenError = error;
                break;
        }
    }

    public void setPaletteFactor(int paletteFactor) {
        this.paletteFactor = paletteFactor;
    }
}
