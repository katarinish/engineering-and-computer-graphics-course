package ru.nsu.fit.g16205.kovylina.view;

import ru.nsu.fit.g16205.kovylina.model.LegendModel;

import javax.swing.*;
import java.awt.*;

public class LegendView extends JPanel {
    private LegendModel model;

    public LegendView(LegendModel model) {
        this.model = model;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(model.getWidth(), model.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(Color.BLUE);
    }
}
