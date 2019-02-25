package ru.nsu.cg.kovylina.utils;

import ru.nsu.cg.kovylina.view.Span;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class DrawingUtils {
    public static Color boundaryColor = new Color(22, 73, 201);
    public static Color aliveCellColor = new Color(107, 51, 176);
    public DrawingUtils() {
    }

    public static void drawBresenhamLine(BufferedImage image,
                                         int x1, int y1, int x2, int y2) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setStroke(new BasicStroke(10, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND, 10));
        g.setColor(boundaryColor);

        int deltaX = Math.abs(x2 - x1);
        int deltaY = Math.abs(y2 - y1);

        int error = 0;

        if (deltaX > deltaY) {
            int deltaErr = 2 * deltaY; // (deltaY / deltaX) * deltaX

            int directionY = (y2 - y1) ;
            if (directionY > 0) directionY = 1;
            if (directionY < 0) directionY = -1;

            int directionX = (x2 - x1);
            if (directionX > 0) directionX = 1;
            if (directionX < 0) directionX = -1;

            for (int i = 0; i < deltaX; ++i) {
                image.setRGB(x1, y1, boundaryColor.getRGB());
//                g.drawLine(x1, y1, x1, y1);
                x1 += directionX;
                error += deltaErr;

                if (error > deltaX) {
                    error -= 2 * deltaX;
                    y1 += directionY;
                }

            }
        } else {
            int deltaErr = 2 * deltaX; // (deltaY / deltaX) * deltaX

            int directionY = (y2 - y1) ;
            if (directionY > 0) directionY = 1;
            if (directionY < 0) directionY = -1;

            int directionX = (x2 - x1);
            if (directionX > 0) directionX = 1;
            if (directionX < 0) directionX = -1;

            for (int i = 0; i < deltaY; ++i) {
//                g.drawLine(x1, y1, x1, y1);
                image.setRGB(x1, y1, boundaryColor.getRGB());
                y1 += directionY;
                error += deltaErr;

                if (error > deltaX) {
                    error -= 2 * deltaY;
                    x1 += directionX;
                }
            }
        }
    }

    public static void fillWithSpan(BufferedImage image,
                                   Color colorToFill,
                                   int seedX, int seedY) {
        if (image.getRGB(seedX, seedY) == DrawingUtils.boundaryColor.getRGB()) return;

        Stack <Span> spanStack = new Stack<>();
        spanStack.push(DrawingUtils.getSpan(image, seedX, seedY));

        while(!spanStack.isEmpty()) {
            Span span = spanStack.pop();
            span.fill(DrawingUtils.aliveCellColor);

            boolean isAbove = false;
            boolean isBelow = false;

            int y = span.getY();
            for (int x = span.getX0() + 1; x < span.getX1(); x++) {

                // pixel above is not a bound and not filled with desired color yet
                if (image.getRGB(x, y - 1) != DrawingUtils.boundaryColor.getRGB()
                        && image.getRGB(x, y - 1) != colorToFill.getRGB()) {

                    if (!isAbove) {
                        spanStack.push(DrawingUtils.getSpan(image, x, y - 1));
                        isAbove = true;
                    }
                } else {
                    isAbove = false;
                }

                // pixel below is not a bound and not filled with desired color yet
                if (image.getRGB(x, y + 1) != DrawingUtils.boundaryColor.getRGB()
                        && image.getRGB(x, y + 1) != colorToFill.getRGB()) {
                    if (!isBelow) {
                        spanStack.push(DrawingUtils.getSpan(image, x, y + 1));
                        isBelow = true;
                    }
                } else {
                    isBelow = false;
                }
            }
        }
    }

    public static Span getSpan(BufferedImage image,
                               int x0, int y0) {
        int xRight = x0;
        int xLeft = x0;

        while (image.getRGB(xLeft, y0) != DrawingUtils.boundaryColor.getRGB()) {
            xLeft--;
        }

        while (image.getRGB(xRight, y0) != DrawingUtils.boundaryColor.getRGB()) {
            xRight++;
        }

        return (new Span(y0, xLeft, xRight, image));
    }
}


