package ru.nsu.fit.g16205.kovylina.utils;

import javax.swing.*;
import java.awt.*;

public class ImageZone extends JPanel {
    public ImageZone() {
        initViewParameters();
    }

    private void initViewParameters() {
        this.setBorder(BorderFactory.createLineBorder(
                Constants.BORDER_COLOR, Constants.BORDER_THICKNESS));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Constants.ZONE_SIZE, Constants.ZONE_SIZE);
    }
}
