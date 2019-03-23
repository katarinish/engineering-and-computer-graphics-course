package ru.nsu.fit.g16205.kovylina.controller;

import ru.nsu.fit.g16205.kovylina.model.ModifiedZoneModel;
import ru.nsu.fit.g16205.kovylina.model.OriginalZoneModel;
import ru.nsu.fit.g16205.kovylina.model.ScaledZoneModel;
import ru.nsu.fit.g16205.kovylina.utils.FileUtils;
import ru.nsu.fit.g16205.kovylina.utils.FilterType;
import ru.nsu.fit.g16205.kovylina.view.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class Controller {
    private MainWindowView mainWindowView;
    private ClientContainerView clientContainerView;

    private OriginalZoneView originalZoneView;
    private ScaledZoneView scaledZoneView;
    private ModifiedZoneView modifiedZoneView;

    private OriginalZoneModel originalZoneModel;
    private ScaledZoneModel scaledZoneModel;
    private ModifiedZoneModel modifiedZoneModel;

    public Controller() {
        initModels();
        initView();
    }

    public void start() {
        mainWindowView.pack();
        mainWindowView.setLocationRelativeTo(null);
        mainWindowView.setVisible(true);
    }

    public void handleSelectImage() {
        handleClearZones();

        File fileToOpen = FileUtils.getOpenFile(mainWindowView);
        if (fileToOpen == null) return;

        BufferedImage bufferedImage = FileUtils.readImageFromFile(fileToOpen);

        originalZoneModel.setImage(bufferedImage);
        originalZoneView.displayImage();

        displayModAndScaledPics();
    }

    public void handleSaveImage() {
        BufferedImage imageToSave = modifiedZoneModel.getImage();
        if (imageToSave == null) return;

        File fileToSave = FileUtils.getSaveFile(mainWindowView);
        if (fileToSave == null) return;

        FileUtils.writeImageIntoFile(imageToSave, fileToSave);
    }

    public void handleClearZones() {
        originalZoneModel.clear();
        scaledZoneModel.clear();
        modifiedZoneModel.clear();

        originalZoneView.displayImage();
        scaledZoneView.displayImage();
        modifiedZoneView.displayImage();
    }

    public void handleOrigZoneClick(MouseEvent e) {
        Point prevPoint = originalZoneModel.getSubImageFrame().getLeftCorner();
        originalZoneModel.getSubImageFrame().setCenter(e.getPoint());
        Point currPoint = originalZoneModel.getSubImageFrame().getLeftCorner();
        if (prevPoint.equals(currPoint)) return;

        displayModAndScaledPics();
    }

    public void handleBlurFilter() {
        setFilter(FilterType.BLUR);
    }

    public void handleBnWFilter() {
        setFilter(FilterType.BLACK_N_WHITE);
    }

    public void handleNegativeFilter() {
        setFilter(FilterType.NEGATIVE);
    }

    public void handleContourFilter() {
        setFilter(FilterType.CONTOUR);
    }

    public void handleSharpFilter() {
        setFilter(FilterType.SHARP);
    }

    public void handleAquarelleFilter() {
        setFilter(FilterType.AQUARELLE);
    }

    public void handleEmbossFilter() {
        setFilter(FilterType.EMBOSS);
    }

    public void handleRobertsFilter() {
        setFilter(FilterType.ROBERTS);
    }

    public void handleSobelFilter() {
        setFilter(FilterType.SOBEL);
    }

    public void handleOrderedDithering() {
        setFilter(FilterType.ORDERED_DITHERING);
    }

    private void displayModAndScaledPics() {
        scaledZoneModel.createImage(originalZoneModel.getFullSizeImage(),
                originalZoneModel.getSubImageFrame().getOriginalLeftCorner());
        scaledZoneView.displayImage();

        modifiedZoneModel.setImage(scaledZoneModel.getImage());
        modifiedZoneView.displayImage();
    }

    private void initView() {
        originalZoneView = new OriginalZoneView(this, originalZoneModel);
        scaledZoneView = new ScaledZoneView(scaledZoneModel);
        modifiedZoneView = new ModifiedZoneView(modifiedZoneModel);

        clientContainerView = new ClientContainerView(
                originalZoneView, scaledZoneView,  modifiedZoneView);

        mainWindowView = new MainWindowView(this, clientContainerView);
    }

    private void initModels() {
        originalZoneModel = new OriginalZoneModel();
        scaledZoneModel = new ScaledZoneModel();
        modifiedZoneModel = new ModifiedZoneModel();
    }

    private void setFilter(FilterType filter) {
        modifiedZoneModel.setFilter(filter);
        modifiedZoneView.displayImage();
    }

    public void implementMeLater() {}
}
