package ru.nsu.cg.kovylina;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class GameField extends JPanel {
    private Graphics g = null;

    public GameField() {
    }

    private void drawBresenhamLine(int x1, int y1, int x2, int y2) {
        int x = x1;
        int y = y1;

        int deltaX = Math.abs(x2 - x1);
        int deltaY = Math.abs(y2 - y1);
        int maxDelta = deltaX > deltaY ? deltaX : deltaY;

        if (deltaY > deltaX) {

        }

        int error = 0;
        int deltaErr = 2 * deltaY; // (deltaY / deltaX) * deltaX

        int directionY = (y2 - y1) ;
        if (directionY > 0) directionY = 1;
        if (directionY < 0) directionY = -1;

        int directionX = (x2 - x1);
        if (directionX > 0) directionX = 1;
        if (directionX < 0) directionX = -1;

        for (int i = 0; i < maxDelta; ++i) {
            this.g.drawLine(x, y, x, y);

            x += directionX;
            error += deltaErr;

            if (error > deltaX) {
                error -= 2 * deltaX;
                y += directionY;
            }

        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        this.g = g;
        super.paintComponent(g);

        int w = getBounds().width;
        int h = getBounds().height;


        this.setBackground(Color.LIGHT_GRAY);
        drawBresenhamLine(200, 25, 205, 50);

//        g.drawLine(0, 0, getBounds().width - 1, getBounds().height - 1);
//        g.drawLine(0, getBounds().height - 1, getBounds().width - 1, 0);

//        g.drawLine(0, 0, 10, 10);
    }
}
