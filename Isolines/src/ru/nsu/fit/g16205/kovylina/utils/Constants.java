package ru.nsu.fit.g16205.kovylina.utils;

import java.awt.*;

public class Constants {
    //Grid size
    public static int K = 100;
    public static int M = 100;

    //Number of Isoline's levels
    public static int N = 4;

    //Domain zone's size
    public static int A = -5;
    public static int B = 1;
    public static int C = -1;
    public static int D = 5;

    //User's view zone
    public static int WIDTH = 600;
    public static int HEIGHT_MAP = 500;
    public static int HEIGHT_LEGEND = 70;

    public static Color[] COLORS = {
            new Color(0, 0, 0),
            new Color(2, 57, 36),
            new Color(205, 136, 169),
            new Color(242, 174, 182),
            new Color(255, 219, 0)
    };

}
