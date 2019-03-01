package ru.nsu.cg.kovylina.model;

import ru.nsu.cg.kovylina.buisness_logic.Cell;
import ru.nsu.cg.kovylina.buisness_logic.CellState;
import ru.nsu.cg.kovylina.buisness_logic.LifeParameters;
import ru.nsu.cg.kovylina.utils.Constants;
import ru.nsu.cg.kovylina.utils.Mode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameFieldModel {
    private int columns;
    private int rows;

    private BufferedImage image;
    private Mode mode = null;

    //TODO: проверять корректность параметров
    private Map<LifeParameters, Double> lifeParameters = new HashMap<>();

    private Cell[][] field = null;
    private ArrayList<Cell> activeCells = new ArrayList<>();

    private HexagonModel hexagonModel;

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
                        Constants.START_IMPACT,
                        lifeParameters);
            }
        }
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public BufferedImage getImage() {
        return image;
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

    public Cell[][] getField() {
        return field;
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

        return field[row][column];
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
