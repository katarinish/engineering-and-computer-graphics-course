package ru.nsu.fit.g16205.kovylina.utils;

import java.awt.*;

public class Configuration {
    int n;
    int k;
    int m;

    int a;
    int b;
    int c;
    int d;

    protected Color[] colors = Constants.COLORS;

    public Configuration(int n, int k, int m, int a, int b, int c, int d) {
        this.k = k;
        this.m = m;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Configuration(int n, int k, int m, int a, int b, int c, int d, Color[] colors) {
        this.n = n;
        this.k = k;
        this.m = m;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.colors = colors;
    }

    public Color[] getColors() {
        return colors;
    }

    public int getN() {
        return n;
    }

    public int getK() {
        return k;
    }

    public int getM() {
        return m;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public int getD() {
        return d;
    }
}
