package ru.nsu.cg.kovylina.utils;

public class Configuration {
    private int rows;
    private int columns;
    private int hexSide;
    private int boundaryWidth;

    private Mode mode;

    public Configuration(int rows, int columns, int hexSide, int boundaryWidth,
                         Mode mode) {
        this.rows = rows;
        this.columns = columns;
        this.hexSide = hexSide;
        this.boundaryWidth = boundaryWidth;
        this.mode = mode;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getHexSide() {
        return hexSide;
    }

    public int getBoundaryWidth() {
        return boundaryWidth;
    }

    public Mode getMode() {
        return mode;
    }

}
