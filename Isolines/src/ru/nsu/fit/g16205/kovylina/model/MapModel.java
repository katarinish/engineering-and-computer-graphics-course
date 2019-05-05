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
    private BufferedImage intersectionImage = null;
    private BufferedImage dynamicIsolineImage = null;
    private BufferedImage customIsolineImage = null;

    protected int width;
    protected int height;

    private int deltaX;
    private int deltaY;

    // Grid size
    private int k;
    private int m;
    private Grid grid = null;

    //Number of isovalues
    protected int n;
    protected Color[] colors = null;
    private ArrayList<Double> customKeyValues = new ArrayList<>();
    private Double[] dynamicIsovalue = new Double[1];

    public MapModel(){}

    public MapModel(int a) {
        this.k = Constants.K;
        this.m = Constants.M;

        this.width = Constants.WIDTH;
        this.height = Constants.HEIGHT_MAP;

        this.deltaX = width / k;
        this.deltaY = height / m;

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

    public BufferedImage getIntersectionImage() {
        if (state.isWithIntersection())
            return intersectionImage;
        return null;
    }

    public BufferedImage getDynamicIsolineImage() {
        if (state.isWithDynamicIsoline())
            return dynamicIsolineImage;
        return null;
    }

    public BufferedImage getCustomIsolineImage() {
        if (state.isWithCustomIsoline())
            return customIsolineImage;
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Double[] getKeyValues() {
        return function.getKeyIsovalues();
    }

    public void setWidth(int width) {
        if (width == this.width) return;

        this.width = width;
        this.deltaX = width / k;
        updateImages();
    }

    public void setHeight(int height) {
        if (height == this.height) return;

        this.height = height;
        this.deltaY = height / m;
        updateImages();
    }

    public void buildIsoline(int x, int y) {
        double value = function.getValue(y, x);
        customKeyValues.add(value);
        initCustomIsolineImage();
    }

    public void buildDynamicIsoline(int x, int y) {
        double value = function.getValue(y, x);
        dynamicIsovalue[0] = value;
        initDynamicIsolineImage();
    }

    protected void initParameters() {
        function = new CustomFunction(width, height, n);

        initMapImage();
        initInterpolatedImage();
        initGridImage();
        initIsolinesImage();
        initIntersectionImage();
    }

    protected void initInterpolatedImage() {
        interpolatedMapImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < interpolatedMapImage.getHeight(); ++x) {
            for (int y = 0; y < interpolatedMapImage.getWidth(); ++y) {
                double value = function.getValue(x, y);
                int color = getInterpolatedColor(value);
                interpolatedMapImage.setRGB(y, x, color);
            }
        }
    }

    private void initIntersectionImage() {
        intersectionImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        drawGeometry(intersectionImage, Types.INTERSECTION);
    }

    private void initIsolinesImage() {
        isolinesImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        drawGeometry(isolinesImage, Types.ISOLINE);
    }

    private void initCustomIsolineImage() {
        customIsolineImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        drawGeometry(customIsolineImage, Types.CUSTOM_ISOLINES);
    }

    private void initDynamicIsolineImage() {
        dynamicIsolineImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        drawGeometry(dynamicIsolineImage, Types.DYNAMIC_ISOLINE);
    }

    private ArrayList<Segment> getIsoline(double isovalue) {
        ArrayList<Segment> segments = new ArrayList<>();

        Cell[][] cells = grid.getCells();

        for (int i = 0; i < cells.length; ++i) {
            for (int j = 0; j < cells[0].length; ++j) {
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
        g.setColor(Color.GRAY);

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
        Double[] keyIsovalues = function.getKeyIsovalues();

        for (int i = 1; i < keyIsovalues.length; ++i) {
            if (keyIsovalues[i] > value) return colors[i - 1].getRGB();
        }

        return colors[keyIsovalues.length - 1].getRGB();
    }

    private int getInterpolatedColor(double value) {
        Double[] keyIsovalues = function.getKeyIsovalues();

        int currentIndex = keyIsovalues.length - 1;
        for (int i = 1; i < keyIsovalues.length; ++i) {
            if (keyIsovalues[i] > value) {
                currentIndex = i - 1;
                break;
            }
        }

        if (currentIndex == (keyIsovalues.length - 1)) {
            return colors[currentIndex].getRGB();
        }

        int prevIndex = currentIndex;
        int nextIndex = currentIndex + 1;

        double prevKeyValue = keyIsovalues[prevIndex];
        double nextKeyValue = keyIsovalues[nextIndex];

        return interpolateColor(value,
                colors[prevIndex], colors[nextIndex],
                prevKeyValue, nextKeyValue);
    }

    private int interpolateColor(double value, Color prevColor, Color nextColor,
                                 double prevValue, double nextValue) {
        double r = prevColor.getRed() * (nextValue - value) / (nextValue - prevValue)
                + nextColor.getRed() * (value - prevValue) / (nextValue - prevValue);
        double g = prevColor.getGreen() * (nextValue - value) / (nextValue - prevValue)
                + nextColor.getGreen() * (value - prevValue) / (nextValue - prevValue);
        double b = prevColor.getBlue() * (nextValue - value) / (nextValue - prevValue)
                + nextColor.getBlue() * (value - prevValue) / (nextValue - prevValue);


        int r1 = validateColor((int)r);
        int g1 = validateColor((int)g);
        int b1 = validateColor((int)b);

        return new Color(r1, g1, b1).getRGB();

    }

    private int validateColor(int r) {
        if (r > 255) return 255;
        if (r < 0) return 0;
        return r;
    }

    private void drawGeometry(BufferedImage image, Types type) {
        grid = new Grid(function, deltaX, deltaY);
        Graphics2D g2d = (Graphics2D) image.getGraphics();

        Double[] isoArray = function.getKeyIsovalues();
        if (type == Types.CUSTOM_ISOLINES) {
            isoArray = customKeyValues.toArray(new Double[isoArray.length]);
        }
        if (type == Types.DYNAMIC_ISOLINE) {
            isoArray = dynamicIsovalue;
        }

        for (Double isovalue: isoArray) {
            if (isovalue == null) break;

            ArrayList<Segment> segments = getIsoline(isovalue);
            for (Segment segment : segments) {
                Point p1 = segment.getPoint1();
                Point p2 = segment.getPoint2();

                if (type == Types.INTERSECTION) {
                    int radius = deltaX / 10;
                    g2d.setColor(Color.RED);
                    g2d.drawOval(p1.x - radius / 2, p1.y - radius / 2,
                            radius, radius);
                    g2d.drawOval(p2.x - radius / 2, p2.y - radius / 2,
                            radius, radius);
                } else {
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }
    }

    protected void updateImages() {
        function.setViewHeight(this.height);
        function.setViewWidth(this.width);

        if (state.isWithInterpolation) {
            initInterpolatedImage();
        } else {
            initMapImage();
        }

        initCustomIsolineImage();
        initDynamicIsolineImage();
        initGridImage();
        initIsolinesImage();
        initIntersectionImage();
    }

    public class State {
        private boolean isWithGrid = true;
        private boolean isWithIsoline = true;
        private boolean isWithInterpolation = false;
        private boolean isWithIntersection = false;
        private boolean isWithDynamicIsoline = false;
        private boolean isWithCustomIsoline = false;

        public boolean isWithDynamicIsoline() {
            return isWithDynamicIsoline;
        }

        public boolean isWithCustomIsoline() {
            return isWithCustomIsoline;
        }

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
            updateImages();
        }

        public void setWithIntersection(boolean withIntersection) {
            isWithIntersection = withIntersection;
        }

        public void setWithDynamicIsoline(boolean withDynamicIsoline) {
            isWithDynamicIsoline = withDynamicIsoline;
        }

        public void setWithCustomIsoline(boolean withCustomIsoline) {
            isWithCustomIsoline = withCustomIsoline;
        }
    }

    private enum Types {
        INTERSECTION,
        ISOLINE,
        CUSTOM_ISOLINES,
        DYNAMIC_ISOLINE,
    }
}
