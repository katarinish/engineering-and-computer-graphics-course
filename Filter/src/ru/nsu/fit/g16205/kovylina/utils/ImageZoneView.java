package ru.nsu.fit.g16205.kovylina.utils;

import javax.swing.*;
import java.awt.*;

public class ImageZoneView extends JPanel {
    protected ImageZoneModel model;

    public ImageZoneView(ImageZoneModel model) {
        this.model = model;

        initViewParameters();
    }

    public void updateZone() {
        repaint();
    }

    public void displayImage() {
        updateZone();
    }

    private void initViewParameters() {
        this.setBorder(BorderFactory.createLineBorder(
                Constants.BORDER_COLOR, Constants.BORDER_THICKNESS));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(model.getWidth(), model.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(model.getImage(), 0, 0, null);
    }
}
