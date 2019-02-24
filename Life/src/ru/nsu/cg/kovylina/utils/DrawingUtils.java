package ru.nsu.cg.kovylina.utils;

import java.awt.*;

public class DrawingUtils {
    public DrawingUtils() {

    }

    public static void drawBresenhamLine(Graphics g, int x1, int y1, int x2, int y2) {
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
                g.drawLine(x1, y1, x1, y1);

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
                g.drawLine(x1, y1, x1, y1);

                y1 += directionY;
                error += deltaErr;

                if (error > deltaX) {
                    error -= 2 * deltaY;
                    x1 += directionX;
                }
            }
        }
    }
}
