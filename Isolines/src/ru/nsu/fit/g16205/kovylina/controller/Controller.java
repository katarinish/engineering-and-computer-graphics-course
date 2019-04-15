package ru.nsu.fit.g16205.kovylina.controller;

import ru.nsu.fit.g16205.kovylina.model.LegendModel;
import ru.nsu.fit.g16205.kovylina.model.MapModel;
import ru.nsu.fit.g16205.kovylina.view.ClientContainerView;
import ru.nsu.fit.g16205.kovylina.view.LegendView;
import ru.nsu.fit.g16205.kovylina.view.MainWindowView;
import ru.nsu.fit.g16205.kovylina.view.MapView;

public class Controller {
    private MainWindowView mainWindowView;
    private ClientContainerView clientContainerView;

    private MapView mapView;
    private LegendView legendView;

    private MapModel mapModel;
    private LegendModel legendModel;

    public Controller() {
        initModels();
        initViews();
    }

    public void start() {
        mainWindowView.pack();
        mainWindowView.setLocationRelativeTo(null);
        mainWindowView.setVisible(true);
    }

    //TODO: implement method
    public void implementMeLater() {
    }

    private void initModels() {
        mapModel = new MapModel();
        legendModel = new LegendModel();
    }

    private void initViews() {
        mapView = new MapView(mapModel);
        legendView = new LegendView(legendModel);

        clientContainerView = new ClientContainerView(mapView, legendView);
        mainWindowView = new MainWindowView(this, clientContainerView);
    }
}
