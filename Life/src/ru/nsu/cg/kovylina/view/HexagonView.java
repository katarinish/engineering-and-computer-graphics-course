package ru.nsu.cg.kovylina.view;

import ru.nsu.cg.kovylina.buisness_logic.Cell;
import ru.nsu.cg.kovylina.model.HexagonModel;
import ru.nsu.cg.kovylina.utils.DrawingUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HexagonView {
    private Stroke stroke;
    private BufferedImage image = null;


    public HexagonView(int w) {
        this.stroke = new BasicStroke(w,
                BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND,
                10);
    }

    public HexagonView(int w, BufferedImage image) {
        this(w);
        this.image = image;
    }

    public void drawFullCell(Cell hex) {
        drawBoundaries(hex);
        fillCell(hex);
    }

    public void fillCell(Cell hex){
        DrawingUtils.fillWithSpan(
                image,
                hex.getColor(),
//                hex.getCellState().getColor(),
                hex.getCenterX(), hex.getCenterY());
    }

    public void printImpact(Cell hex){

    }

    public void drawBoundaries(Cell hex) {
        Point[] vertexes = hex.getVertexes();
        for (int i = 0; i < HexagonModel.VERTEXES_NUM; ++i) {
            int nextIndex = (i + 1) % HexagonModel.VERTEXES_NUM;

            int x1 = vertexes[i].x;
            int y1 = vertexes[i].y;

            int x2 = vertexes[nextIndex].x;
            int y2 = vertexes[nextIndex].y;

            DrawingUtils.drawBresenhamLine(image, stroke, x1, y1, x2, y2);
        }
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;

        // Надо ли перерисовываться?
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}

