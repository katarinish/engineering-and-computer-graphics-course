package ru.nsu.cg.kovylina.model;

import ru.nsu.cg.kovylina.buisness_logic.Cell;
import ru.nsu.cg.kovylina.buisness_logic.CellState;
import ru.nsu.cg.kovylina.buisness_logic.LifeParameters;
import ru.nsu.cg.kovylina.utils.Constants;
import ru.nsu.cg.kovylina.utils.Mode;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameFieldModel {
    private int columns;
    private int rows;
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

        initLifeParameters(
                Constants.LIFE_BEGIN, Constants.LIFE_END,
                Constants.BIRTH_BEGIN, Constants.BIRTH_END,
                Constants.FIRST_IMPACT, Constants.SECOND_IMPACT);
        initField();
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

    public void setColumns(int columns) {
        this.columns = columns;
        initField();
    }

    public void setRows(int rows) {
        this.rows = rows;
        initField();
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
