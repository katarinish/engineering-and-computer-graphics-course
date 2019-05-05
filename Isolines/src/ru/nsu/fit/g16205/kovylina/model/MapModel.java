package ru.nsu.fit.g16205.kovylina.model;

import ru.nsu.fit.g16205.kovylina.logic.Cell;
import ru.nsu.fit.g16205.kovylina.logic.Grid;
import ru.nsu.fit.g16205.kovylina.logic.CustomFunction;
import ru.nsu.fit.g16205.kovylina.logic.Segment;
import ru.nsu.fit.g16205.kovylina.utils.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MapModel {
    private static final double EPS = 0.0000001;

    protected CustomFunction function = null;
    public State state = new State();

    private BufferedImage mapImage = null;
    private BufferedImage interpolatedMapImage = null;
    private BufferedImage isolinesImage = null;
    private BufferedImage gridImage = null;

    protected int width;
    protected int height;

    // Grid size
    private int k;
    private int m;
    private Grid grid = null;

    //Number of isovalues
    protected int n;
    protected Color[] colors = null;

    public MapModel(){}

    public MapModel(int a) {
        this.width = Constants.WIDTH;
        this.height = Constants.HEIGHT_MAP;

        this.k = Constants.K;
        this.m = Constants.M;

        this.n = Constants.N;
        this.colors = Constants.COLORS;

        initParameters();
    }

    public BufferedImage getMapImage() {
        if (state.isWithInterpolation())
            return interpolatedMapImage;
        return mapImage;
    }

    public BufferedImage getIsolinesImage() {
        if (state.isWithIsoline())
            return isolinesImage;
        return null;
    }

    public BufferedImage getGridImage() {
        if (state.isWithGrid())
            return gridImage;
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double[] getKeyValues() {
        return function.getKeyIsovalues();
    }

    public void setWidth(int width) {
        if (width == this.width) return;

        this.width = width;
        updateImages();
    }

    public void setHeight(int height) {
        if (height == this.height) return;

        this.height = height;
        updateImages();
    }

    protected void initParameters() {
        function = new CustomFunction(width, height, n);
        grid = new Grid(k, m, function);

        initMapImage();
        initGridImage();
        initIsolinesImage();
    }

    private void initIsolinesImage() {
        isolinesImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) isolinesImage.getGraphics();

        for (double isovalue: function.getKeyIsovalues()) {
            ArrayList<Segment> segments = getIsoline(isovalue);
            for (Segment segment : segments) {
                Point p1 = segment.getPoint1();
                Point p2 = segment.getPoint2();

                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }

    private ArrayList<Segment> getIsoline(double isovalue) {
        ArrayList<Segment> segments = new ArrayList<>();

        Cell[][] cells = grid.getCells();

        int deltaX = width / k;
        int deltaY = height / m;

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < k; ++j) {
                Cell cell = cells[i][j];
                ArrayList<Point> intersects = cell.getCrossingPoint(isovalue, deltaX, deltaY);

                Point p1 = null;
                Point p2 = null;
                switch (intersects.size()) {
                    case 0:
                        break;
                    case 2:
                        p1 = new Point(intersects.get(0).x + deltaX * j,
                                intersects.get(0).y + deltaY * i);
                        p2 = new Point(intersects.get(1).x + deltaX * j,
                                intersects.get(1).y + deltaY * i);
                        segments.add(new Segment(p1, p2));
                        break;
                    case 4:
                        if (!cell.isSignumCenter(isovalue)) {
                            p1 = new Point(intersects.get(0).x + deltaX * j,
                                    intersects.get(0).y + deltaY * i);
                            p2 = new Point(intersects.get(1).x + deltaX * j,
                                    intersects.get(1).y + deltaY * i);
                            segments.add(new Segment(p1, p2));

                            p1 = new Point(intersects.get(2).x + deltaX * j,
                                    intersects.get(2).y + deltaY * i);
                            p2 = new Point(intersects.get(3).x + deltaX * j,
                                    intersects.get(3).y + deltaY * i);
                            segments.add(new Segment(p1, p2));
                        } else {
                            p1 = new Point(intersects.get(0).x + deltaX * j,
                                    intersects.get(0).y + deltaY * i);
                            p2 = new Point(intersects.get(3).x + deltaX * j,
                                    intersects.get(3).y + deltaY * i);
                            segments.add(new Segment(p1, p2));

                            p1 = new Point(intersects.get(2).x + deltaX * j,
                                    intersects.get(2).y + deltaY * i);
                            p2 = new Point(intersects.get(1).x + deltaX * j,
                                    intersects.get(1).y + deltaY * i);
                            segments.add(new Segment(p1, p2));
                        }
                        break;
                    default:
                        return getIsoline(isovalue + EPS);
                }

            }
        }

        return segments;
    }

    protected void initMapImage() {
        mapImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < mapImage.getHeight(); ++x) {
            for (int y = 0; y < mapImage.getWidth(); ++y) {
                double value = function.getValue(x, y);
                int color = getColor(value);
                mapImage.setRGB(y, x, color);
            }
        }
    }

    private void initGridImage() {
        gridImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) gridImage.getGraphics();

        int deltaX = width / k;
        int deltaY = height / m;
        int imageWidth = gridImage.getWidth();
        int imageHeight = gridImage.getHeight();

        for (int x = 0; x < imageWidth; x += deltaX) {
            g.drawLine(x, 0, x, imageHeight);
        }
        for (int y = 0; y < imageHeight; y += deltaY) {
            g.drawLine(0, y, imageWidth, y);
        }
    }

    private int getColor(double value) {
        double[] keyIsovalues = function.getKeyIsovalues();

        for (int i = 1; i < keyIsovalues.length; ++i) {
            if (keyIsovalues[i] > value) return colors[i - 1].getRGB();
        }

        return colors[keyIsovalues.length - 1].getRGB();
    }

    protected void updateImages() {
        function.setViewHeight(this.height);
        function.setViewWidth(this.width);

        initMapImage();
        initGridImage();
        initIsolinesImage();
    }

    public class State {
        private boolean isWithGrid = false;
        private boolean isWithIsoline = true;
        private boolean isWithInterpolation = false;
        private boolean isWithIntersection = false;

        public boolean isWithGrid() {
            return isWithGrid;
        }

        public boolean isWithIsoline() {
            return isWithIsoline;
        }

        public boolean isWithInterpolation() {
            return isWithInterpolation;
        }

        public boolean isWithIntersection() {
            return isWithIntersection;
        }

        public void setWithGrid(boolean withGrid) {
            isWithGrid = withGrid;
        }

        public void setWithIsoline(boolean withIsoline) {
            isWithIsoline = withIsoline;
        }

        public void setWithInterpolation(boolean withInterpolation) {
            isWithInterpolation = withInterpolation;
        }

        public void setWithIntersection(boolean withIntersection) {
            isWithIntersection = withIntersection;
        }
    }
}
