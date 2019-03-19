package ru.nsu.fit.g16205.kovylina.model;

import ru.nsu.fit.g16205.kovylina.utils.FileUtils;
import ru.nsu.fit.g16205.kovylina.utils.ImageZoneModel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OriginalZoneModel extends ImageZoneModel {
    private BufferedImage fullSizeImage = null;
    private SubImageFrame subImageFrame = null;

    public OriginalZoneModel() {
        super();
    }

    public SubImageFrame getSubImageFrame() {
        return subImageFrame;
    }

    public BufferedImage getFullSizeImage() {
        return fullSizeImage;
    }

    @Override
    public void clear() {
        fullSizeImage = null;
        subImageFrame = null;

        super.clear();
    }

    private double getScalingRatio() {
        double original_width = fullSizeImage.getWidth();
        double scaled_width = image.getWidth();

        return scaled_width / original_width;
    }

    @Override
    public void setImage(BufferedImage image) {
        fullSizeImage = image;
        this.image = validateImageSize(image);

        setSubImageFrame();
    }

    private void setSubImageFrame() {
        double k = getScalingRatio();

        int frameWidth = (int)(width * k);
        int frameHeight = (int)(height * k);
        this.subImageFrame = new SubImageFrame(frameWidth, frameHeight);
    }

    private Point fromScaledToOriginal(Point p) {
        double k = getScalingRatio();
        int originX = (int) (p.x / k);
        int originY = (int) (p.y / k);

        return new Point(originX, originY);
    }

    private BufferedImage validateImageSize(BufferedImage bi) {
        if (bi.getWidth() <= width && bi.getHeight() <= height) return bi;

        Dimension imageSize = new Dimension(bi.getWidth(), bi.getHeight());
        Dimension boundary = new Dimension(width, height);

        Dimension scaledDimension = FileUtils.getScaledDimension(imageSize, boundary);

        Image scaledImage = bi.getScaledInstance(
                scaledDimension.width, scaledDimension.height, Image.SCALE_SMOOTH);

        return FileUtils.toBufferedImage(scaledImage);
    }

    public class SubImageFrame {
        private Point leftCorner = new Point(0, 0);
        private int width;
        private int height;

        public SubImageFrame(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public void setCenter(Point center) {
            int scaledWidth = image.getWidth();
            int scaledHeight = image.getHeight();
            int leftX;
            int leftY;

            leftX = ((center.x - width / 2) < 0) ? 0 : center.x - width / 2;
            leftY = ((center.y - height / 2) < 0) ? 0 : center.y - height / 2;

            leftX = ((center.x + width / 2) > scaledWidth)
                    ? scaledWidth - width : leftX;
            leftY = ((center.y + height / 2) > scaledHeight)
                    ? scaledHeight - height : leftY;

            setLeftCorner(new Point(leftX, leftY));
        }

        public void setLeftCorner(Point leftCorner) {
            this.leftCorner = leftCorner;
        }

        public Point getOriginalLeftCorner() {
            return fromScaledToOriginal(leftCorner);
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public Point getLeftCorner() {
            return leftCorner;
        }
    }
}
