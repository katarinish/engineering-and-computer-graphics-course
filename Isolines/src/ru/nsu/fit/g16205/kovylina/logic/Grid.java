package ru.nsu.fit.g16205.kovylina.logic;

public class Grid {
    private CustomFunction function;

    private int height;
    private int width;

    private int k;
    private int m;

    private Cell[][] cells;
    private double[][] values;

    public Grid(CustomFunction f, int k, int m) {
    }

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
        double deltaW = width / (double) k;
        double deltaH = height / (double) m;

        cells = new Cell[m][k];

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < k; ++j) {
                Cell cell = new Cell();

                double topLeftX = function.getDomainA() + j * deltaH;
                double topLeftY = function.getDomainC() + i  * deltaW;

                cell.setLeftTopCorner(function.foo(topLeftX, topLeftY));
                cell.setRightTopCorner(function.foo(topLeftX, topLeftY + deltaW));
                cell.setRigthBottomCorner(function.foo(topLeftX + deltaH, topLeftY + deltaW));
                cell.setLeftBottomCorner(function.foo(topLeftX + deltaH, topLeftY));

                cells[i][j] = cell;
            }
        }
    }
}
