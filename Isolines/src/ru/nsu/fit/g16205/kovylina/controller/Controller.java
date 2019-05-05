package ru.nsu.fit.g16205.kovylina.controller;

import ru.nsu.fit.g16205.kovylina.model.LegendModel;
import ru.nsu.fit.g16205.kovylina.model.MapModel;
import ru.nsu.fit.g16205.kovylina.utils.Configuration;
import ru.nsu.fit.g16205.kovylina.utils.Constants;
import ru.nsu.fit.g16205.kovylina.utils.FileUtils;
import ru.nsu.fit.g16205.kovylina.view.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

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
        new Options(mapModel, this).open();
    }

    public void setNewSettings(Configuration configuration) {
        mainWindowView.setVisible(false);

        this.configuration = configuration;

        initModels();
        initViews();

        mapView.updateView();
        legendView.updateView();

        mainWindowView.pack();
        mainWindowView.setLocationRelativeTo(null);
        mainWindowView.setVisible(true);
    }

    public void handleReadModel() {
        File fileToOpen = FileUtils.getOpenFile(mainWindowView);
        if (fileToOpen == null) return;
        
        try {
            FileReader fr = new FileReader(fileToOpen);
            Scanner scanner = new Scanner(fr);
            String[] strArr;

            strArr = scanner.nextLine().split(" ");
            int k = Integer.parseInt(strArr[0]);
            int m = Integer.parseInt(strArr[0]);

            strArr = scanner.nextLine().split(" ");
            int n = Integer.parseInt(strArr[0]);

            Color[] colors = new Color[n + 1];
            for (int i = 0; i <= n; ++i) {
                strArr = scanner.nextLine().split(" ");
                int r = Integer.parseInt(strArr[0]);
                int g = Integer.parseInt(strArr[1]);
                int b = Integer.parseInt(strArr[2]);

                Color color = new Color(r, g, b);
                colors[i] = color;
            }

            Configuration newConfig = new Configuration(n, k, m,
                    configuration.getA(), configuration.getB(), configuration.getC(), configuration.getD(),
                    colors);

            setNewSettings(newConfig);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
