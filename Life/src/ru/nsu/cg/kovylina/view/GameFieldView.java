package ru.nsu.cg.kovylina.view;

import ru.nsu.cg.kovylina.buisness_logic.Cell;
import ru.nsu.cg.kovylina.utils.Constants;
import ru.nsu.cg.kovylina.utils.DrawingUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class GameFieldView extends JPanel {
    private BufferedImage image;
    private HexagonView hexagonView;

    public GameFieldView(HexagonView hexagonView, BufferedImage image) {
        this.hexagonView = hexagonView;
        this.image = image;

        this.setBackground(Constants.BACKGROUND_COLOR);
    }

    public void drawField(Cell[][] cellField) {
        for (Cell[] aCellField : cellField) {
            for (Cell cell : aCellField) {
                if (cell == null) continue;

                hexagonView.drawFullCell(cell);
            }
        }

        repaint();
    }

    public void updateField() {
        repaint();
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        updateField();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }
}
