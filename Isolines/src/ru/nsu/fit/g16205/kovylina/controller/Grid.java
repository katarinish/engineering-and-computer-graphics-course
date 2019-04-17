package ru.nsu.fit.g16205.kovylina.controller;

import ru.nsu.fit.g16205.kovylina.logic.CustomFunction;

public class Grid {
    private CustomFunction function;

    private int height;
    private int width;

    private Cell[][] cells;

    public Grid(int height, int width, CustomFunction f) {
        this.height = height;
        this.width = width;

        this.function = f;

        initCells();
    }

    public Cell[][] getCells() {
        return cells;
    }

    private void initCells() {
        int deltaW = function.getAbsDomainWidth() / height;
        int deltaH = function.getAbsDomainHeight() / height;

        cells = new Cell[height][width];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Cell cell = new Cell();

                int topLeftX = i * deltaH;
                int topLeftY = j * deltaW;

                cell.setLeftTopCorner(function.getValue(topLeftX, topLeftY));
                cell.setRightTopCorner(function.getValue(topLeftX, topLeftY + deltaW));
                cell.setRigthBottomCorner(function.getValue(topLeftX + deltaH, topLeftY + deltaW));
                cell.setLeftBottomCorner(function.getValue(topLeftX + deltaH, topLeftY));

                cells[i][j] = cell;
            }
        }
    }
}
