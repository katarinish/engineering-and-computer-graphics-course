package ru.nsu.cg.kovylina.model;

import ru.nsu.cg.kovylina.buisness_logic.*;
import ru.nsu.cg.kovylina.utils.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class GameFieldModel {
    private int columns;
    private int rows;

    private BufferedImage image;
    private Mode mode;

    //TODO: проверять корректность параметров
    private Map<LifeParameters, Double> lifeParameters = new HashMap<>();

    private Cell[][] field = null;
    private HashSet<Cell> activeCells = new HashSet<>();

    private HexagonModel hexagonModel;
    private boolean isLifeRunning = false;

    public GameFieldModel(int columns, int rows, Mode mode, HexagonModel hexModel) {
        this.columns = columns;
        this.rows = rows;
        this.mode = mode;

        this.hexagonModel = hexModel;

        initImage();
        initLifeParameters(
                Constants.LIFE_BEGIN, Constants.LIFE_END,
                Constants.BIRTH_BEGIN, Constants.BIRTH_END,
                Constants.FIRST_IMPACT, Constants.SECOND_IMPACT);
        initField();
    }

    private void initImage() {
        int width = (columns + 1) * hexagonModel.getWidth();
        int add = (rows / 2 == 1 ? 2 : 1);
        int height = (rows / 2 + add) * hexagonModel.getHeight()
                + (rows / 2 + add) * hexagonModel.getSize();
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    private void initLifeParameters(
            double lBegin, double lEnd,
            double bBegin, double bEnd,
            double fstImpact, double sndImpact) {
        lifeParameters.put(LifeParameters.LIVE_BEGIN, lBegin);
        lifeParameters.put(LifeParameters.LIVE_END, lEnd);
        lifeParameters.put(LifeParameters.BIRTH_BEGIN, bBegin);
        lifeParameters.put(LifeParameters.BIRTH_END, bEnd);
        lifeParameters.put(LifeParameters.FST_IMPACT, fstImpact);
        lifeParameters.put(LifeParameters.SND_IMPACT, sndImpact);
    }

    private void initField() {
        field = new Cell[this.rows][this.columns];

        for (int i = 0; i < this.rows; ++i) {
            int addOffset = i % 2 == 1 ? hexagonModel.getR() : 0;
            int actualColumns = i % 2 == 1 ? this.columns - 1 : this.columns;

            for (int j = 0; j < actualColumns; ++j ) {
                int centerX = addOffset + ((j + 1) * hexagonModel.getHor());
                int centerY = (i + 1) * hexagonModel.getVert();

                field[i][j] = new Cell(
                        new Point(i, j),
                        new Point(centerX, centerY),
                        hexagonModel.getAllVertexes(centerX, centerY),
                        CellState.DEAD,
                        Constants.START_DEAD_IMPACT,
                        lifeParameters);
            }
        }
    }

    public ArrayList<Cell> firstNeighboursRing(Cell cell) {
        int x = cell.getRowPosition();
        int y = cell.getColumnPosition();
        int oddRowOffset = x % 2;
        Point[] coordinates = new Point[6];

        coordinates[0] = new Point(x - 1, y - 1 + oddRowOffset);
        coordinates[1] = new Point(x - 1, y + oddRowOffset);
        coordinates[2] = new Point(x, y + 1);
        coordinates[3] = new Point(x + 1, y + oddRowOffset);
        coordinates[4] = new Point(x + 1, y - 1 + oddRowOffset);
        coordinates[5] = new Point(x, y - 1);

        ArrayList<Cell> neighbors = new ArrayList<>();
        for (Point coordinate: coordinates) {
            if (!isInField(coordinate.x, coordinate.y)) continue;
            neighbors.add(field[coordinate.x][coordinate.y]);
        }

        return neighbors;
    }

    public ArrayList<Cell> secondNeighboursRing(Cell cell) {
        int x = cell.getRowPosition();
        int y = cell.getColumnPosition();
        int oddRowOffset = x % 2;
        Point[] coordinates = new Point[6];

        coordinates[0] = new Point(x - 2, y);
        coordinates[1] = new Point(x - 1, y + 1 + oddRowOffset);
        coordinates[2] = new Point(x + 1 , y + 1 + oddRowOffset);
        coordinates[3] = new Point(x + 2, y);
        coordinates[4] = new Point(x + 1, y - 2 + oddRowOffset);
        coordinates[5] = new Point(x - 1, y - 2 + oddRowOffset);


        ArrayList<Cell> neighbors = new ArrayList<>();
        for (Point coordinate: coordinates) {
            if (!isInField(coordinate.x, coordinate.y)) continue;
            neighbors.add(field[coordinate.x][coordinate.y]);
        }

        return neighbors;
    }

    private boolean isInField(int x, int y) {
        if (x >= rows || y >= columns
                || x < 0 || y < 0) return false;

        Cell cell = field[x][y];
        return cell != null;
    }

    private double calculateImpact(Cell cell) {
        double impact = 0.0;
        ArrayList<Cell> firstNghbrs = firstNeighboursRing(cell);
        ArrayList<Cell> secondNghbrs = secondNeighboursRing(cell);

        //TODO: брать импакт из конфига
        for (Cell fstN: firstNghbrs) {
            if (fstN.getCellState() == CellState.ALIVE) impact += Constants.FIRST_IMPACT;
        }
        for (Cell sndN: secondNghbrs) {
            if (sndN.getCellState() == CellState.ALIVE) impact += Constants.SECOND_IMPACT;
        }

//        return impact;
        return new BigDecimal(impact).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

    public Cell getCell(int x, int y) {
        if (x >= image.getWidth() || y >= image.getHeight()) return null;

        int offsetX = hexagonModel.getR();
        int offsetY = hexagonModel.getSize() / 2;
        // This is necessary for moving hexagon grid to the (0,0) point
        x -= offsetX;
        y -= offsetY;

        int rectWidth = hexagonModel.getWidth();
        int rectHeight = hexagonModel.getSize()/2 + hexagonModel.getSize();

        int row = y / rectHeight;
        boolean isOddRow = (row % 2) == 1;
        int oddRowOffset = rectWidth/2;
        int column;

        if (isOddRow) {
            column = (x - oddRowOffset) / rectWidth;
        } else {
            column = x / rectWidth;
        }

        double relY = y - (row * rectHeight);
        double relX;
        if (isOddRow) {
            relX = (x - (column * rectWidth)) - (double)oddRowOffset;
        } else {
            relX = (x - (column * rectWidth));
        }

        double c = (double) (hexagonModel.getSize() / 2);
        double r = (double) (hexagonModel.getWidth() / 2);
        double m = c / r;

        if (relY <  (-m * relX) + c) {
            row--;
            if (!isOddRow) column--;
        } else if (relY < (m * relX) - c) {
            row--;
            if(isOddRow) column++;
        }

        if (!isInField(row, column)) return null;

        return field[row][column];
    }

    @SuppressWarnings("Duplicates")
    public void nextGeneration() {
        if (activeCells.isEmpty()) {
            isLifeRunning = false;
            return;
        }

//        for (Cell[] aCellField : field) {
//            for (Cell cell : aCellField) {
//                if (cell == null) continue;
//
//                double newImpact = calculateImpact(cell);
//                cell.setImpact(newImpact);
//            }
//        }
//
//        for (Cell[] aCellField : field) {
//            for (Cell cell : aCellField) {
//                if (cell == null) continue;
//                cell.defineCellState();
//            }
//        }

//        Update all (active + potentially active) cells impact
        for (Cell activeCell: new HashSet<>(activeCells)) {
//            if (activeCell.getImpact() == Constants.NON_ACTIVE_IMPACT) {
//                activeCells.remove(activeCell);
//                continue;
//            }

            //Updating own impact
            double newImpact = calculateImpact(activeCell);
            activeCell.setImpact(newImpact);

            //Updating first-ring slave cells impact
            for (Cell slaveCell: firstNeighboursRing(activeCell)) {
                if (activeCells.contains(slaveCell)) continue;

                double slaveImpact = calculateImpact(slaveCell);
                slaveCell.setImpact(slaveImpact);

                //If slave cell is being active then add it to active list
                if (slaveImpact == Constants.NON_ACTIVE_IMPACT) continue;
                activeCells.add(slaveCell);
            }
            //Updating second-ring slave cells impact
            for (Cell slaveCell: secondNeighboursRing(activeCell)) {
                if (activeCells.contains(slaveCell)) continue;

                double slaveImpact = calculateImpact(slaveCell);
                slaveCell.setImpact(slaveImpact);

                //If slave cell is being active then add it to active list
                if (slaveImpact == Constants.NON_ACTIVE_IMPACT) continue;
                activeCells.add(slaveCell);
            }
        }

        for (Cell activeCell: activeCells) {
            activeCell.defineCellState();
        }
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public boolean isLifeRunning() {
        return isLifeRunning;
    }

    public Mode getMode() {
        return mode;
    }

    public BufferedImage getImage() {
        return image;
    }

    public HashSet<Cell> getActiveCells() {
        return activeCells;
    }

    public void setColumns(int columns) {
        this.columns = columns;
        initImage();
        initField();

        //уведомить View о смене параметра
    }

    public void setRows(int rows) {
        this.rows = rows;
        initImage();
        initField();

        //уведомить View о смене параметра
    }

    public void setLifeRunning(boolean lifeRunning) {
        isLifeRunning = lifeRunning;
    }

    public Cell[][] getField() {
        return field;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
