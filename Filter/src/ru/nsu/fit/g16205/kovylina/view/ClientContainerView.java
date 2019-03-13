package ru.nsu.fit.g16205.kovylina.view;

import ru.nsu.fit.g16205.kovylina.utils.Constants;
import ru.nsu.fit.g16205.kovylina.utils.ImageZone;

import javax.swing.*;
import java.awt.*;

public class ClientContainerView extends JPanel {

    private ImageZone originalZoneView;
    private ImageZone scaledZoneView;
    private ImageZone modifiedZoneView;


    public ClientContainerView() {
        setLayout(new GridLayout(1, 3,
                Constants.MARGIN, Constants.MARGIN));
    }

    public ClientContainerView(ImageZone originalZoneView,
                               ImageZone scaledZoneView,
                               ImageZone modifiedZoneView) {
        this();

        this.originalZoneView = originalZoneView;
        this.scaledZoneView = scaledZoneView;
        this.modifiedZoneView = modifiedZoneView;

        initLayoutView();
    }

    private void initLayoutView() {
        this.setBorder(BorderFactory.createEmptyBorder(
                Constants.MARGIN, Constants.MARGIN, Constants.MARGIN, Constants.MARGIN));
        add(originalZoneView);
        add(scaledZoneView);
        add(modifiedZoneView);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                originalZoneView.getPreferredSize().width
                        + scaledZoneView.getPreferredSize().width
                        + originalZoneView.getPreferredSize().width,
                originalZoneView.getPreferredSize().height);
    }
}
