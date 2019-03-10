package ru.nsu.cg.kovylina.view;

import ru.nsu.cg.kovylina.buisness_logic.Cell;
import ru.nsu.cg.kovylina.model.HexagonModel;
import ru.nsu.cg.kovylina.utils.ColorMode;
import ru.nsu.cg.kovylina.utils.Constants;
import ru.nsu.cg.kovylina.utils.DrawingUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.RoundingMode;
import java.text.DecimalFormat;

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

    public void drawFullCell(Cell hex, ColorMode mode, boolean isShowImpact) {
        drawBoundaries(hex);
        fillCell(hex, mode);
        if (isShowImpact) printImpact(hex);
    }

    public void fillCell(Cell hex, ColorMode mode){
        Color colorToFill = (mode == ColorMode.IMPACT)
                ? hex.getColor()
                : hex.getCellState().getColor();
        DrawingUtils.fillWithSpan(
                image,
                colorToFill,
                hex.getCenterX(), hex.getCenterY());
    }

    public void printImpact(Cell hex){
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.HALF_UP);

        String impact = df.format(hex.getImpact());

        Graphics g = image.getGraphics();
        g.setColor(Constants.FONT_COLOR);
        g.drawString(impact, hex.getCenterX() - 5 , hex.getCenterY() + 5);
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

    public void setBoundaryWidth(int w) {
        this.stroke = new BasicStroke(w,
                BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND,
                10);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}

