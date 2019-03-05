package ru.nsu.cg.kovylina.controller;

import ru.nsu.cg.kovylina.buisness_logic.*;
import ru.nsu.cg.kovylina.model.*;
import ru.nsu.cg.kovylina.utils.*;
import ru.nsu.cg.kovylina.view.*;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;

public class LifeGameController {
    private Configuration config;

    private HexagonView hexagonView;
    private GameFieldView gameFieldView;

    private HexagonModel hexagonModel;
    private GameFieldModel gameFieldModel;

    Timer timer;

//    private boolean isLifeRunning = false;

    public LifeGameController(Configuration c) {
        this.config = c;
        timer = new Timer(Constants.TIMER_DELAY, e -> {
            if (!gameFieldModel.isLifeRunning()) {
                timer.stop();
                return;
            }

            nextGeneration();
        });

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
        MainWindowView window = new MainWindowView(this.gameFieldView, this);
        gameFieldView.drawField(gameFieldModel.getField());
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

        hexagonView.fillCell(clickedCell);
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
            hexagonView.drawFullCell(activeCell);
            if (activeCell.getImpact() == Constants.NON_ACTIVE_IMPACT) {
                gameFieldModel.getActiveCells().remove(activeCell);
            }
        }

//        for (Cell[] aCellField : gameFieldModel.getField()) {
//            for (Cell cell : aCellField) {
//                if (cell == null) continue;
//                hexagonView.drawFullCell(cell);
//            }
//        }
        gameFieldView.updateField();
    }

    public void handleNextGen() {
        gameFieldModel.setLifeRunning(true);
        nextGeneration();
    }
}
