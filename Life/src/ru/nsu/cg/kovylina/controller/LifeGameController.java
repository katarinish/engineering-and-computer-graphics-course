package ru.nsu.cg.kovylina.controller;

import ru.nsu.cg.kovylina.MainWindow;
import ru.nsu.cg.kovylina.model.*;
import ru.nsu.cg.kovylina.utils.Configuration;
import ru.nsu.cg.kovylina.view.*;

public class LifeGameController {
    private Configuration config;

    private HexagonView hexagonView;
    private GameFieldView gameFieldView;

    private HexagonModel hexagonModel;
    private GameFieldModel gameFieldModel;

    public LifeGameController(Configuration c) {
        this.config = c;

        initModels();
        initViews();
    }

    private void initViews() {
        this.hexagonView = new HexagonView(
                config.getBoundaryWidth(), gameFieldModel.getImage());
        this.gameFieldView = new GameFieldView(
                this.hexagonView, this.gameFieldModel.getImage());
    }

    private void initModels() {
        this.hexagonModel = new HexagonModel(
                config.getHexSide(),config.getBoundaryWidth());
        this.gameFieldModel = new GameFieldModel(
                config.getColumns(),
                config.getRows(),
                config.getMode(),
                this.hexagonModel);
    }

    public void start() {
//        MainWindowController windowController = new MainWindowController();
        MainWindow window = new MainWindow(this.gameFieldView);
        gameFieldView.drawField(gameFieldModel.getField());
        window.pack();
        window.setVisible(true);
    }
}
