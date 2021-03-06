package ru.nsu.cg.kovylina.view;

import ru.nsu.cg.kovylina.buisness_logic.Cell;
import ru.nsu.cg.kovylina.controller.LifeGameController;
import ru.nsu.cg.kovylina.utils.ColorMode;
import ru.nsu.cg.kovylina.utils.Constants;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class GameFieldView extends JPanel {
    private BufferedImage image;
    private HexagonView hexagonView;

    private LifeGameController controller;

    public GameFieldView(HexagonView hexagonView,
                         BufferedImage image,
                         LifeGameController controller) {
        this.hexagonView = hexagonView;
        this.image = image;
        this.controller = controller;

        this.setBackground(Constants.BACKGROUND_COLOR);
        addEventListeners();
    }

    private void addEventListeners() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.handleGameFieldClick(e);
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            private Cell currentCell = null;

            @Override
            public void mouseDragged(MouseEvent e) {
                Cell clickedCell = controller.getCellByCoordinates(e.getPoint());
                if (clickedCell.equals(currentCell)) return;
                currentCell = clickedCell;
                controller.handleGameFieldClick(e);
            }
        });
    }

    public void drawField(Cell[][] cellField, ColorMode colorMode, boolean showImpact) {
        for (Cell[] aCellField : cellField) {
            for (Cell cell : aCellField) {
                if (cell == null) continue;

                hexagonView.drawFullCell(cell, colorMode, showImpact);
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

        revalidate();
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
