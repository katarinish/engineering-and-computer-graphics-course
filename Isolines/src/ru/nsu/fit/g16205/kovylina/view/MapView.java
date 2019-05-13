package ru.nsu.fit.g16205.kovylina.view;

import ru.nsu.fit.g16205.kovylina.controller.Controller;
import ru.nsu.fit.g16205.kovylina.model.MapModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MapView extends JPanel {
    private MapModel model;
    private Controller controller;

    public MapView(MapModel model, Controller controller) {
        this.model = model;
        this.controller = controller;

        initEventListeners();
    }

    private void initEventListeners() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!model.state.isWithCustomIsoline()) return;

                controller.handleCustomIsolineClick(e);
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!model.state.isWithDynamicIsoline()) return;

                controller.handleDynamicDrag(e);
                repaint();
            }
        });
    }

    public void updateView() {
        repaint();
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
        g.drawImage(model.getIsolinesImage(), 0, 0, null);
        g.drawImage(model.getCustomIsolineImage(), 0, 0, null);
        g.drawImage(model.getDynamicIsolineImage(), 0, 0 , null);
        g.drawImage(model.getGridImage(), 0, 0, null);
        g.drawImage(model.getIntersectionImage(), 0, 0, null);
    }
}
