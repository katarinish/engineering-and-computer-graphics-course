package ru.nsu.cg.kovylina.utils;

import java.awt.*;

public class Constants {
    public static int ROWS = 20;
    public static int COLUMNS = 30;
    public static int HEX_SIDE = 25;
    public static int BOUNDARY_WIDTH = 2;

    public static Mode MODE = Mode.REPLACE;

    public static double LIFE_BEGIN = 2.0;
    public static double LIFE_END = 3.3;
    public static double BIRTH_BEGIN = 2.3;
    public static double BIRTH_END = 2.9;
    public static double FIRST_IMPACT = 1.0;
    public static double SECOND_IMPACT = 0.3;

    public static double START_ALIVE_IMPACT = 3.0;
    public static double START_DEAD_IMPACT = 0.0;

    public static int TRANSPARENT_COLOR_RGB = 0;
    public static Color BOUNDARY_COLOR = new Color(0, 0, 0);
    public static Color FONT_COLOR = new Color(1, 1, 1);
    public static Color BACKGROUND_COLOR = new Color (255, 255, 255);
}
