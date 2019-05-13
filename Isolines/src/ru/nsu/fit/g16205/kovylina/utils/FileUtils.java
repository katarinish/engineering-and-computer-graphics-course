package ru.nsu.fit.g16205.kovylina.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;


public class FileUtils {

    public static File getOpenFile(JFrame parent) {
        File file = null;
        JFileChooser fc =  new JFileChooser();

        fc.setCurrentDirectory(new File(System.getProperty("user.dir") + "/images"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Model",
                "txt");
        fc.setFileFilter(filter);

        if (fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
        }

        return file;
    }

    public static File getSaveFile(JFrame parent) {
        File file = null;
        JFileChooser fc = new JFileChooser();

        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Picture",
                "bmp", "png", "jpeg", "jpg");
        fc.setFileFilter(filter);

        if (fc.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
        }

        return file;
    }

    public static BufferedImage readImageFromFile(File f) {
        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println("Error reading image from given file.");
            e.printStackTrace();
        }

        return bufferedImage;
    }

    public static void writeImageIntoFile(BufferedImage bi, File f) {
        try {
            String ext = FileUtils.getFileExtension(f).orElse("png");
            ImageIO.write(bi, ext, f);
        } catch (IOException e) {
            System.out.println("Error writing image to a file.");
            e.printStackTrace();
        }
    }

    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) return (BufferedImage) image;

        BufferedImage bi = new BufferedImage(
                image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D grBuffIm = bi.createGraphics();
        grBuffIm.drawImage(image, 0, 0, null);
        grBuffIm.dispose();

        return bi;
    }

    private static Optional<String> getFileExtension(File file) {
        String filepath = file.getAbsolutePath();
        return Optional.ofNullable(filepath)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filepath.lastIndexOf(".") + 1));
    }
}
