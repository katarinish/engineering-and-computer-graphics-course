package ru.nsu.cg.kovylina.model;

import javax.swing.event.SwingPropertyChangeSupport;
import java.awt.*;

public class HexagonModel {
    private SwingPropertyChangeSupport propChangeFirer;

    public static int VERTEXES_NUM = 6;

    private int size;
    private int boundaryWidth;

    private int t;  // short leg of 30deg triangle
    private int r;  // long leg of the 30deg triangle

    private int width;
    private int height;

    private int hor; // horizontal distance between centers of two adjacent hex's
    private int vert; // vertical distance between centers of two adjacent hex's

    public HexagonModel(int size, int boundaryWidth) {
        this.size = size;
        this.boundaryWidth = boundaryWidth;

        calculateParameters();
    }

    private void calculateParameters() {
        this.t = this.size / 2;
        this.r = (int) (this.size * Math.cos(Math.toRadians(30)));

        this.width = 2 * this.r;
        this.height = 2 * this.size;

        this.hor = this.width;
        this.vert = this.size + this.t;
    }

    public Point[] getAllVertexes(int centerX, int centerY) {
        Point[] vertexes = new Point[VERTEXES_NUM];

        vertexes[0] = new Point(centerX, centerY - size);
        vertexes[1] = new Point(centerX + r, centerY - t);
        vertexes[2] = new Point(centerX + r, centerY + t);
        vertexes[3] = new Point(centerX, centerY + size);
        vertexes[4] = new Point(centerX - r, centerY + t);
        vertexes[5] = new Point(centerX - r, centerY - t);

        return vertexes;
    }

    public int getSize() {
        return size;
    }

    public int getBoundaryWidth() {
        return boundaryWidth;
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

    public void setSize(int size) {
        this.size = size;

        calculateParameters();
        // Уведомить View об изменении параметра
    }

    public void setBoundaryWidth(int boundaryWidth) {
        this.boundaryWidth = boundaryWidth;

        //Уведомить View об изменении ширины
    }

    public void setPropChangeFirer(SwingPropertyChangeSupport propChangeFirer) {
        this.propChangeFirer = propChangeFirer;
    }
}
