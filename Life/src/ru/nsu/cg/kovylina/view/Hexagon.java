package ru.nsu.cg.kovylina.view;

public class Hexagon {
    private int size;

    private int t;  // short leg of 30deg triangle
    private int r;  // long leg of the 30deg triangle

    private int width;
    private int height;

    private int hor; // horizontal distance between centers of two adjacent hex's
    private int vert; // vertical distance between centers of two adjacent hex's

    public Hexagon(int sideSize) {
        this.size = sideSize;
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

}
