package ru.nsu.cg.kovylina.controller;

import ru.nsu.cg.kovylina.buisness_logic.*;
import ru.nsu.cg.kovylina.model.*;
import ru.nsu.cg.kovylina.utils.*;
import ru.nsu.cg.kovylina.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;

public class LifeGameController {
    private Configuration config;

    private HexagonView hexagonView;
    private GameFieldView gameFieldView;
    private OptionsView optionsView;

    private HexagonModel hexagonModel;
    private GameFieldModel gameFieldModel;

    private Timer timer;

    public LifeGameController(Configuration c) {
        this.config = c;
        timer = new Timer(Constants.TIMER_DELAY, e -> {
            if (!gameFieldModel.isLifeRunning()) {
                timer.stop();
                return;
            }

            nextGeneration();
        });

        optionsView = new OptionsView(this);

        initModels();
        initViews();
    }

    private void initViews() {
        this.hexagonView = new HexagonView(
                hexagonModel.getBoundaryWidth(), gameFieldModel.getImage());
        this.gameFieldView = new GameFieldView(
                this.hexagonView,
                this.gameFieldModel.getImage(),
                this);
    }

    private void initModels() {
        this.hexagonModel = new HexagonModel(config);
        this.gameFieldModel = new GameFieldModel(config, this.hexagonModel);
    }

    public void start() {
        MainWindowView window = new MainWindowView(this.gameFieldView, this);
        gameFieldView.drawField(gameFieldModel.getField(),
                gameFieldModel.getColorMode(),
                gameFieldModel.isShowImpact());
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void handleGameFieldClick(MouseEvent e) {
        if (gameFieldModel.isLifeRunning()) return;

        BufferedImage image = gameFieldModel.getImage();
        int x = e.getX();
        int y = e.getY();

        if (x >= image.getWidth() || y >= image.getHeight()
            || x < 0 || y < 0) return;
        if (image.getRGB(x, y) == Constants.TRANSPARENT_COLOR_RGB) return;

        Cell clickedCell = gameFieldModel.getCell(x, y);
        if (clickedCell == null) return;

        switch (gameFieldModel.getMode()) {
            case XOR:
                if ((clickedCell.getCellState() == CellState.ALIVE)) {
                    makeItDead(clickedCell);
                } else {
                    makeItAlive(clickedCell);
                }
                break;

            case REPLACE:
                if (clickedCell.getCellState() == CellState.ALIVE) return;
                makeItAlive(clickedCell);
                break;
        }

        hexagonView.fillCell(clickedCell,
                gameFieldModel.getColorMode());
        gameFieldView.updateField();
    }

    public void handleStartGame() {
        gameFieldModel.setLifeRunning(true);
        timer.start();
    }

    public void handlePauseGame() {
        gameFieldModel.setLifeRunning(false);
    }

    public void addAction() {
        System.exit(0);
    }

    private void makeItDead(Cell cell) {
        cell.setImpact(Constants.START_DEAD_IMPACT);
        cell.setCellState(CellState.DEAD);
        cell.defineColor();

        gameFieldModel.getActiveCells().remove(cell);
    }

    private void makeItAlive(Cell cell) {
        cell.setImpact(Constants.START_ALIVE_IMPACT);
        cell.setCellState(CellState.ALIVE);
        cell.defineColor();

        gameFieldModel.getActiveCells().add(cell);
    }

    private void nextGeneration() {
        gameFieldModel.nextGeneration();
        for(Cell activeCell: new HashSet<>(gameFieldModel.getActiveCells())) {
            hexagonView.drawFullCell(activeCell,
                    gameFieldModel.getColorMode(),
                    gameFieldModel.isShowImpact());
            if (activeCell.getImpact() == Constants.NON_ACTIVE_IMPACT) {
                gameFieldModel.getActiveCells().remove(activeCell);
            }
        }

        gameFieldView.updateField();
    }

    public void handleNextGen() {
        gameFieldModel.setLifeRunning(false);
        nextGeneration();
    }

    public void handleOpenOptions() {
        optionsView.open();
    }

    public void handleClearField() {
        gameFieldModel.setLifeRunning(false);

        gameFieldModel.clearField();
        gameFieldView.drawField(gameFieldModel.getField(),
                gameFieldModel.getColorMode(),
                gameFieldModel.isShowImpact());
    }

    public void handleSetXOR() {
        gameFieldModel.setMode(Mode.XOR);
    }

    public void handleSetReplace() {
        gameFieldModel.setMode(Mode.REPLACE);
    }

    public Cell getCellByCoordinates(Point p) {
        return gameFieldModel.getCell(p.x, p.y);
    }

    public boolean handleAcceptSettings(Configuration c) {
        if (!validateParams(c)) return false;

        HashSet<Cell> activeCells = new HashSet<>(gameFieldModel.getActiveCells());

        hexagonModel.setParamsByConfig(c);
        gameFieldModel.setConfiguration(c);

        BufferedImage newImage = gameFieldModel.getImage();

        hexagonView.setImage(newImage);
        hexagonView.setBoundaryWidth(c.getBoundaryWidth());

        gameFieldView.setImage(newImage);
        gameFieldView.drawField(gameFieldModel.getField(),
                gameFieldModel.getColorMode(), gameFieldModel.isShowImpact());

        for (Cell activeCell: activeCells) {
            int x = activeCell.getRowPosition();
            int y = activeCell.getColumnPosition();

            if (gameFieldModel.isInField(x, y)
                    && activeCell.getCellState() == CellState.ALIVE) {
                Cell cellToDraw = gameFieldModel.getField()[x][y];

                makeItAlive(cellToDraw);
                hexagonView.drawFullCell(cellToDraw,
                        gameFieldModel.getColorMode(),
                        gameFieldModel.isShowImpact());
            }
        }

        gameFieldView.updateField();
        return true;
    }

    private boolean validateParams(Configuration c) {
        int rows = c.getRows();
        int cols = c.getColumns();
        int width = c.getBoundaryWidth();

        return !(rows >= Constants.MAX_ROWS
                || cols >= Constants.MAX_COLUMNS
                || width >= Constants.MAX_BOUNDARY);
    }
}
