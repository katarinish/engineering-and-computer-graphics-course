package ru.nsu.cg.kovylina.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class GameField extends JPanel {
    private int rows;
    private int columns;
    private int hexSize;

    private BufferedImage image;

    private Hexagon hexagon;

    public GameField(int size, int rows, int columns) {
        this.hexSize = size;
        this.rows = rows;
        this.columns = columns;

        this.hexagon = new Hexagon(size);
        // TODO: configure right picture size
        this.image = new BufferedImage((columns + 1) * hexagon.getWidth(),
                (rows + 1) * hexagon.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
    }

    private void drawField() {
        int offsetX = 20;
        int offsetY = 20;
        for (int i = 1; i <= rows; ++i) {
            int addOffset = i % 2 == 0 ? hexagon.getR() : 0;
            int columns = i % 2 == 0 ? this.columns - 1 : this.columns;

            for (int j = 1; j <= columns; ++j) {
                hexagon.setNewCenterPoint(offsetX + addOffset + (j * hexagon.getHor()),
                        offsetY + (i * hexagon.getVert()));

                hexagon.draw(image);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBackground(Color.LIGHT_GRAY);

        drawField();
        g.drawImage(image, 0, 0, null);
    }
}
