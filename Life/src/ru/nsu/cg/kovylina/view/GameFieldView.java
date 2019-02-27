package ru.nsu.cg.kovylina.view;

import ru.nsu.cg.kovylina.buisness_logic.Cell;
import ru.nsu.cg.kovylina.utils.DrawingUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class GameFieldView extends JPanel {
    private BufferedImage image;
    private HexagonView hexagonView;

    public GameFieldView(HexagonView hexagonView) {
        this.hexagonView = hexagonView;

        //Dm. шестиуголника создавать тут?

    }

    private void drawField(Cell[][] cellField) {
        for (int i = 0; i < cellField.length; ++i) {
            for (int j = 0; j < cellField[i].length; ++j) {
                Cell cell = cellField[i][j];
                if (cell == null) continue;

                hexagonView.drawFullCell(cell);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBackground(Color.LIGHT_GRAY);

        g.drawImage(image, 0, 0, null);
    }
}
