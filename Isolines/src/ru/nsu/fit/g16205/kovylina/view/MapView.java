package ru.nsu.fit.g16205.kovylina.view;

import ru.nsu.fit.g16205.kovylina.model.MapModel;

import javax.swing.*;
import java.awt.*;

public class MapView extends JPanel {
    private MapModel model;

    public MapView(MapModel model) {
        this.model = model;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(model.getWidth(), model.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        model.setWidth(this.getWidth());
        model.setHeight(this.getHeight());

        g.drawImage(model.getMapImage(), 0, 0 , null);
//        g.drawImage(model.getGridImage(), 0, 0, null);
        setBackground(Color.CYAN);
    }
}
