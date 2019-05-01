package ru.nsu.fit.g16205.kovylina.controller;

import ru.nsu.fit.g16205.kovylina.logic.CustomFunction;

public class Grid {
    private CustomFunction function;

    private int height;
    private int width;

    private int k;
    private int m;

    private Cell[][] cells;

    public Grid(int k, int m, CustomFunction f) {
        this.height = f.getAbsDomainHeight();
        this.width = f.getAbsDomainWidth();

        this.k = k;
        this.m = m;

        this.function = f;

        initCells();
    }

    public Cell[][] getCells() {
        return cells;
    }

    private void initCells() {
        int deltaW = width / k;
        int deltaH = height / m;

        cells = new Cell[m][k];

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < k; ++j) {
                Cell cell = new Cell();

                int topLeftX = function.getDomainA() + i * deltaH;
                int topLeftY = function.getDomainC() + j  * deltaW;

                cell.setLeftTopCorner(function.foo(topLeftX, topLeftY));
                cell.setRightTopCorner(function.foo(topLeftX, topLeftY + deltaW));
                cell.setRigthBottomCorner(function.foo(topLeftX + deltaH, topLeftY + deltaW));
                cell.setLeftBottomCorner(function.foo(topLeftX + deltaH, topLeftY));

                cells[i][j] = cell;
            }
        }
    }
}
