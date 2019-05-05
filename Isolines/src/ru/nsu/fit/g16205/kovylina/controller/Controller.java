package ru.nsu.fit.g16205.kovylina.controller;

import ru.nsu.fit.g16205.kovylina.model.LegendModel;
import ru.nsu.fit.g16205.kovylina.model.MapModel;
import ru.nsu.fit.g16205.kovylina.utils.Configuration;
import ru.nsu.fit.g16205.kovylina.utils.Constants;
import ru.nsu.fit.g16205.kovylina.view.ClientContainerView;
import ru.nsu.fit.g16205.kovylina.view.LegendView;
import ru.nsu.fit.g16205.kovylina.view.MainWindowView;
import ru.nsu.fit.g16205.kovylina.view.MapView;

import java.awt.event.MouseEvent;

public class Controller {
    private Configuration configuration = new Configuration(
            Constants.N,
            Constants.K,
            Constants.M,
            Constants.A,
            Constants.B,
            Constants.C,
            Constants.D,
            Constants.COLORS
    );

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

    public void handleToggleGrid() {
        mapModel.state.setWithGrid(!mapModel.state.isWithGrid());
        mapView.updateView();
    }

    public void handleToggleIsilones() {
        mapModel.state.setWithIsoline(!mapModel.state.isWithIsoline());
        mapView.updateView();
    }

    public void handleInterpolation() {
        mapModel.state.setWithInterpolation(!mapModel.state.isWithInterpolation());
        legendModel.state.setWithInterpolation(!legendModel.state.isWithInterpolation());
        mapView.updateView();
        legendView.updateView();
    }

    public void handleIntersection() {
        mapModel.state.setWithIntersection(!mapModel.state.isWithIntersection());
        mapView.updateView();
    }

    public void handleCustomIsolineClick(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        mapModel.buildIsoline(x, y);
        mapView.updateView();
    }

    public void handleDynamicDrag(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        mapModel.buildDynamicIsoline(x, y);
        mapView.updateView();
    }

    private void initModels() {
        mapModel = new MapModel(configuration);

        legendModel = new LegendModel(configuration, mapModel.getKeyValues());
    }

    private void initViews() {
        mapView = new MapView(mapModel, this);
        legendView = new LegendView(legendModel);

        clientContainerView = new ClientContainerView(mapView, legendView);
        mainWindowView = new MainWindowView(this, clientContainerView);
    }

    public void handleEnableCustomIsolines() {
        mapModel.state.setWithCustomIsoline(!mapModel.state.isWithCustomIsoline());
        mapView.updateView();
    }

    public void handleEnableDynamicIsolines() {
        mapModel.state.setWithDynamicIsoline(!mapModel.state.isWithDynamicIsoline());
        mapView.updateView();
    }

    public void hadleOpenSettings() {
    }

    public void setNewSettings(Configuration configuration) {
        this.configuration = configuration;
        initModels();
        initViews();
    }
}
