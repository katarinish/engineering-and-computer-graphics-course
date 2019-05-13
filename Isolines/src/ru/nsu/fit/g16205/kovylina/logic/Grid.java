package ru.nsu.fit.g16205.kovylina.logic;

public class Grid {
    private CustomFunction function;

    private int deltaX;
    private int deltaY;

    private int k;
    private int m;

    private Cell[][] cells;

    public Grid(CustomFunction f, int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;

        this.function = f;

        initCells();
    }

    private void initCells() {
        int width = function.getViewWidth();
        int height = function.getViewHeight();

        this.k = width / deltaX;
        this.m = height / deltaY;

        cells = new Cell[m][k];

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < k; ++j) {
                Cell cell = new Cell();

                int topLeftX = deltaX * j;
                int topLeftY = deltaY * i;

                cell.setLeftTopCorner(function.getValue(topLeftY, topLeftX));
                cell.setRightTopCorner(function.getValue(topLeftY,topLeftX + deltaX));
                cell.setRigthBottomCorner(function.getValue(topLeftY + deltaY,topLeftX + deltaX));
                cell.setLeftBottomCorner(function.getValue(topLeftY + deltaY, topLeftX));

                cells[i][j] = cell;
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }
}
