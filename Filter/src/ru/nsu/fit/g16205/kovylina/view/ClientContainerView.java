package ru.nsu.fit.g16205.kovylina.view;

import ru.nsu.fit.g16205.kovylina.utils.Constants;
import ru.nsu.fit.g16205.kovylina.utils.ImageZoneView;

import javax.swing.*;
import java.awt.*;

public class ClientContainerView extends JPanel {

    private ImageZoneView originalZoneView;
    private ImageZoneView scaledZoneView;
    private ImageZoneView modifiedZoneView;


    public ClientContainerView() {
        setLayout(new GridLayout(1, 3,
                Constants.MARGIN, Constants.MARGIN));
    }

    public ClientContainerView(ImageZoneView originalZoneView,
                               ImageZoneView scaledZoneView,
                               ImageZoneView modifiedZoneView) {
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
}
