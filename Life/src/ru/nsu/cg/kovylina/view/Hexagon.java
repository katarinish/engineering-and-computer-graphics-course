package ru.nsu.cg.kovylina.view;

import ru.nsu.cg.kovylina.utils.DrawingUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Hexagon {
    private static int VERTEXES_NUM = 6;

    private int centerX;
    private int centerY;

    private int size;

    private int t;  // short leg of 30deg triangle
    private int r;  // long leg of the 30deg triangle

    private int width;
    private int height;

    private int hor; // horizontal distance between centers of two adjacent hex's
    private int vert; // vertical distance between centers of two adjacent hex's

    private Point[] vertexes;


    public Hexagon(int sideSize, int x0, int y0) {
        this.size = sideSize;
        this.centerX = x0;
        this.centerY = y0;

        calculateParameters();
        calculateAllVertexes();
    }

    public Hexagon (int sideSize) {
        this(sideSize, 0, 0);
    }

    private void calculateParameters() {
        this.t = this.size / 2;
        this.r = (int) (this.size * Math.cos(Math.toRadians(30)));

        this.width = 2 * this.r;
        this.height = 2 * this.size;

        this.hor = this.width;
        this.vert = this.size + this.t;
    }

    private void calculateAllVertexes() {
        vertexes = new Point[Hexagon.VERTEXES_NUM];

        vertexes[0] = new Point(centerX, centerY - size);
        vertexes[1] = new Point(centerX + r, centerY - t);
        vertexes[2] = new Point(centerX + r, centerY + t);
        vertexes[3] = new Point(centerX, centerY + size);
        vertexes[4] = new Point(centerX - r, centerY + t);
        vertexes[5] = new Point(centerX - r, centerY - t);
    }

    public void draw(BufferedImage image) {
        for (int i = 0; i < Hexagon.VERTEXES_NUM; ++i) {
            int nextIndex = (i + 1) % 6;

            int x1 = vertexes[i].x;
            int y1 = vertexes[i].y;

            int x2 = vertexes[nextIndex].x;
            int y2 = vertexes[nextIndex].y;

            DrawingUtils.drawBresenhamLine(image, x1, y1, x2, y2);
        }
    }

    public Point[] getVertexes() {
        return vertexes;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHor() {
        return hor;
    }

    public int getVert() {
        return vert;
    }

    public int getR() {
        return r;
    }

    public void setNewCenterPoint(int x0, int y0) {
        centerX = x0;
        centerY = y0;

        calculateAllVertexes();
    }
}
