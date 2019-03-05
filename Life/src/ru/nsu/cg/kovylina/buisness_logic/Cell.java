package ru.nsu.cg.kovylina.buisness_logic;

import ru.nsu.cg.kovylina.utils.Constants;

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
    private Color color;
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
//        defineColor();
    }

    //TODO: брать значения из конфига --- из лайф параметерс
    // TODO: вынести цвета в константы
    public void defineColor() {
//        if (cellState == CellState.ALIVE) {
//            color = new Color(47,42,64);
//        } else {
//            color = new Color(222,224,226);
//        }
        if (impact >= Constants.BIRTH_BEGIN && impact <= Constants.BIRTH_END) {
            if (cellState == CellState.ALIVE) {
                color = new Color(47,42,64);
            } else {
                color = new Color(141,178,180);
            }
        } else if ((impact >= Constants.LIFE_BEGIN && impact < Constants.BIRTH_BEGIN)
                || (impact > Constants.BIRTH_END && impact <= Constants.LIFE_END)) {
            if (cellState == CellState.ALIVE) {
                color = new Color(144,73,99);
            } else {
                color = new Color(63,113,107);
            }
        } else {
            color = new Color(222,224,226);
        }

//        if (impact >= Constants.BIRTH_BEGIN && impact <= Constants.BIRTH_END) {
//            if (cellState == CellState.ALIVE) {
//                color = Color.GREEN;
//            } else {
//                color = Color.BLUE;
//            }
//        } else if ((impact >= Constants.LIFE_BEGIN && impact < Constants.BIRTH_BEGIN)
//                || (impact > Constants.BIRTH_END && impact <= Constants.LIFE_END)) {
//            if (cellState == CellState.ALIVE) {
//                color = Color.YELLOW;
//            } else {
//                color = Color.PINK;
//            }
//        } else {
//            color = new Color(222,224,226);
//        }
    }

    public void defineCellState() {
        if (cellState != CellState.ALIVE
            && impact >= lifeParameters.get(LifeParameters.BIRTH_BEGIN)
            && impact <= lifeParameters.get(LifeParameters.BIRTH_END)){
            cellState = CellState.ALIVE;
        } else if (impact >= lifeParameters.get(LifeParameters.LIVE_BEGIN)
            && impact <= lifeParameters.get(LifeParameters.LIVE_END)
            && cellState == CellState.ALIVE) {
            cellState = CellState.ALIVE;
        } else if (impact < lifeParameters.get(LifeParameters.LIVE_BEGIN)
                && cellState == CellState.ALIVE) {
            cellState = CellState.LONELY;
        } else if (impact > lifeParameters.get(LifeParameters.LIVE_END)
                && cellState == CellState.ALIVE) {
            cellState = CellState.OVERPOPULATED;
        }

        defineColor();
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

    public Color getColor() {
        return color;
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
