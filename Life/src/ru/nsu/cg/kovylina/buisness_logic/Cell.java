package ru.nsu.cg.kovylina.buisness_logic;

import java.awt.*;
import java.util.Map;

public class Cell {
    private int rowPosition;
    private int columnPosition;
    private int centerX;
    private int centerY;
    private Point[] vertexes;

    private CellState cellState;
    private double impact;
    private Map<LifeParameters, Double> lifeParameters;

    public Cell(Point position,
                Point center, Point[] vertexes,
                CellState cellState, double impact, Map lifeParams) {
        this(position, center, vertexes, impact, lifeParams);
        this.cellState = cellState;
    }

    public Cell(Point position,
                Point center,
                Point[] vertexes,
                double impact, Map lifeParams) {
        this.rowPosition = position.x;
        this.columnPosition = position.y;

        this.centerX = center.x;
        this.centerY = center.y;
        this.vertexes = vertexes;

        this.impact = impact;
        this.cellState = CellState.DEAD;
        this.lifeParameters = lifeParams;

        defineCellState();
    }

    private void defineCellState() {
        if (cellState == CellState.DEAD
            && impact >= lifeParameters.get(LifeParameters.BIRTH_BEGIN)
            && impact <= lifeParameters.get(LifeParameters.BIRTH_END)){
            cellState = CellState.READY_TO_BORN;
        }

        if (impact >= lifeParameters.get(LifeParameters.LIVE_BEGIN)
            && impact <= lifeParameters.get(LifeParameters.LIVE_END)) {
            cellState = CellState.ALIVE;
        }

        if (impact < lifeParameters.get(LifeParameters.LIVE_BEGIN)) {
            cellState = CellState.LONELY;
        }

        if (impact > lifeParameters.get(LifeParameters.LIVE_END)) {
            cellState = CellState.OVERPOPULATED;
        }
    }

    public void setVertexes(Point[] vertexes) {
        this.vertexes = vertexes;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    public void setImpact(double impact) {
        this.impact = impact;

//        defineCellState();
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public Point[] getVertexes() {
        return vertexes;
    }

    public CellState getCellState() {
        return cellState;
    }

    public double getImpact() {
        return impact;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        Cell otherCell = (Cell) obj;
        return (centerX == otherCell.centerX) && (centerY == otherCell.centerY);
    }
}
