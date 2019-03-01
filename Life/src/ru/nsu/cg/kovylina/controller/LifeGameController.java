package ru.nsu.cg.kovylina.controller;

import ru.nsu.cg.kovylina.MainWindow;
import ru.nsu.cg.kovylina.buisness_logic.Cell;
import ru.nsu.cg.kovylina.model.*;
import ru.nsu.cg.kovylina.utils.Configuration;
import ru.nsu.cg.kovylina.utils.Constants;
import ru.nsu.cg.kovylina.view.*;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

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
                this.hexagonView,
                this.gameFieldModel.getImage(),
                this);
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
        MainWindow window = new MainWindow(this.gameFieldView);
        gameFieldView.drawField(gameFieldModel.getField());
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void handleGameFieldClick(MouseEvent e) {
        // TODO: Добавить клетки в список активных
        BufferedImage image = gameFieldModel.getImage();
        int x = e.getX();
        int y = e.getY();

        if (x >= image.getWidth() || y >= image.getHeight()) return;
        if (image.getRGB(x, y) == Constants.BACKGROUND_COLOR.getRGB()) return;

        Cell clickedCell = gameFieldModel.getCell(x, y);
        if (clickedCell == null) return;

        clickedCell.setImpact(3.0);

        hexagonView.fillCell(clickedCell);
        gameFieldView.updateField();
    }
}
