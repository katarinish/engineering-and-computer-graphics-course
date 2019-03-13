package ru.nsu.fit.g16205.kovylina.controller;

import ru.nsu.fit.g16205.kovylina.model.ModifiedZoneModel;
import ru.nsu.fit.g16205.kovylina.model.OriginalZoneModel;
import ru.nsu.fit.g16205.kovylina.model.ScaledZoneModel;
import ru.nsu.fit.g16205.kovylina.view.*;

public class Controller {
    private MainWindowView mainWindowView;
    private ClientContainerView clientContainerView;

    private OriginalZoneView originalZoneView;
    private ScaledZoneView scaledZoneView;
    private ModifiedZoneView modifiedZoneView;

    private OriginalZoneModel originalZoneModel;
    private ScaledZoneModel scaledZoneModel;
    private ModifiedZoneModel modifiedZoneModel;

    public Controller() {
        initModels();
        initView();
    }

    public void start() {
        mainWindowView.pack();
        mainWindowView.setLocationRelativeTo(null);
        mainWindowView.setVisible(true);
    }

    private void initView() {
        originalZoneView = new OriginalZoneView(originalZoneModel);
        scaledZoneView = new ScaledZoneView(scaledZoneModel);
        modifiedZoneView = new ModifiedZoneView(modifiedZoneModel);

        clientContainerView = new ClientContainerView(
                originalZoneView, scaledZoneView,  modifiedZoneView);

        mainWindowView = new MainWindowView(this, clientContainerView);
    }

    private void initModels() {
        originalZoneModel = new OriginalZoneModel();
        scaledZoneModel = new ScaledZoneModel();
        modifiedZoneModel = new ModifiedZoneModel();
    }
}
