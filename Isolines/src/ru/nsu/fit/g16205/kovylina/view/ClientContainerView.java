package ru.nsu.fit.g16205.kovylina.view;

import javax.swing.*;
import java.awt.*;

public class ClientContainerView extends JPanel {

    private MapView mapView;
    private LegendView legendView;


    public ClientContainerView() {
        setLayout(new BorderLayout());
    }

    public ClientContainerView(MapView mapView, LegendView legendView) {
        this();

        this.mapView = mapView;
        this.legendView = legendView;

        initLayout();
    }

    private void initLayout() {
        add(mapView, BorderLayout.CENTER);
        add(legendView, BorderLayout.PAGE_END);
    }
}
