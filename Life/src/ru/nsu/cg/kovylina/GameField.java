package ru.nsu.cg.kovylina;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class GameField extends JPanel {
    private Graphics g = null;
    private int fieldHeight;
    private int fieldWidth;
    private int hexagonSide = 30;

    public GameField() {
    }

    private void drawField(int width, int height) {
        int cellHeight = 2 * this.hexagonSide;
        int cellWidth = (int)((Math.sqrt(3) / 2) * cellHeight);

        int xStart = 0;
        int yStart = 0;
        int offsetX = cellWidth / 2;
        int offsetY = cellHeight * 3 / 4;

        int columnCount = width / cellWidth;

        for (int i = 0; i < columnCount; ++i) {
            drawHexagon(this.hexagonSide, offsetX + (i * (cellWidth - 1) ) , offsetY);
        }
//        int rowCount =
    }

    private void drawBresenhamLine(int x1, int y1, int x2, int y2) {
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
                this.g.drawLine(x1, y1, x1, y1);

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
                this.g.drawLine(x1, y1, x1, y1);

                y1 += directionY;
                error += deltaErr;

                if (error > deltaX) {
                    error -= 2 * deltaY;
                    x1 += directionX;
                }
            }
        }
    }

    private void drawHexagon(int sideSize, int centerX, int centerY) {
        int [] cornersX = new int[6];
        int [] cornersY = new int[6];

        for (int i = 0; i < 6; ++i) {
            int angleDegree = 60 * i + 30;
            double angleRadian = Math.toRadians(angleDegree);

            cornersX[i] = centerX + (int)(sideSize * Math.cos(angleRadian));
            cornersY[i] = centerY + (int)(sideSize * Math.sin(angleRadian));
        }

        for (int i = 0; i < 6; ++i) {
            int nextIndex = (i + 1) % 6;
            drawBresenhamLine(cornersX[i], cornersY[i], cornersX[nextIndex], cornersY[nextIndex]);
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        this.g = g;
        super.paintComponent(g);

        this.fieldWidth = getBounds().width;
        this.fieldHeight = getBounds().height;


        this.setBackground(Color.LIGHT_GRAY);

        drawField(this.fieldWidth, this.fieldHeight);

//        drawHexagon(this.hexagonSide, 150, 150);
//        drawBresenhamLine(200, 25, 295, 150);
//        g.drawLine(0, 0, getBounds().width - 1, getBounds().height - 1);
//        g.drawLine(0, getBounds().height - 1, getBounds().width - 1, 0);

//        g.drawLine(0, 0, 10, 10);
    }
}
