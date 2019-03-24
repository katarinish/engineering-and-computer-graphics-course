package ru.nsu.fit.g16205.kovylina.controller;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;
import ru.nsu.fit.g16205.kovylina.buisness_logic.Filters.*;
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

        File fileToOpen = FileUtils.getOpenFile(mainWindowView);
        if (fileToOpen == null) return;

        handleClearZones();

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
        setFilterType(FilterType.BLUR);
    }

    public void handleBnWFilter() {
        setFilterType(FilterType.BLACK_N_WHITE);
    }

    public void handleNegativeFilter() {
        setFilterType(FilterType.NEGATIVE);
    }

    public void handleContourFilter() {
        if (modifiedZoneModel.getOriginalImage() == null) return;
        new Option(this::handleSetContourLimit, 0, 255).open();
    }

    public void handleSharpFilter() {
        setFilterType(FilterType.SHARP);
    }

    public void handleAquarelleFilter() {
        setFilterType(FilterType.AQUARELLE);
    }

    public void handleEmbossFilter() {
        setFilterType(FilterType.EMBOSS);
    }

    public void handleRobertsFilter() {
        if (modifiedZoneModel.getOriginalImage() == null) return;
        new Option(this::handleSetRobertsLimit, 0, 255).open();
    }

    public void handleSobelFilter() {
        if (modifiedZoneModel.getOriginalImage() == null) return;
        new Option(this::handleSetSobelLimit, 0, 255).open();
    }

    public void handleOrderedDithering() {
        setFilterType(FilterType.ORDERED_DITHERING);
    }

    public void handleGamma() {
        setFilterType(FilterType.GAMMA);
    }

    public void handleFloydDithering() {
        if (modifiedZoneModel.getOriginalImage() == null) return;
        new Option(this::handleSetFloydPalette, 1, 255).open();
    }

    public void handleZoom() {
        setFilterType(FilterType.ZOOM);
    }

    public void handleRotation() {
        if (modifiedZoneModel.getOriginalImage() == null) return;
        new Option(this::handleSetRotationAngle, 0, 180).open();
    }

    public void handleSetRotationAngle(int angle) {
        Rotation filter = new Rotation();
        filter.setAngle(angle);

        setFilter(filter);
    }

    public void handleSetRobertsLimit(int limit) {
        RobertsOperator filter = new RobertsOperator();
        filter.setC(limit);

        setFilter(filter);
    }

    public void handleSetSobelLimit(int limit) {
        SobelOperator filter = new SobelOperator();
        filter.setLimit(limit);

        setFilter(filter);
    }

    public void handleSetFloydPalette(int palette) {
        FloydDithering filter = new FloydDithering();
        filter.setPaletteFactor(palette);

        setFilter(filter);
    }

    public void handleSetContourLimit(int limit) {
        Contour filter = new Contour();
        filter.setC(limit);

        setFilter(filter);
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

    private void setFilterType(FilterType filter) {
        modifiedZoneModel.setFilterType(filter);
        modifiedZoneView.displayImage();
    }

    private void setFilter(Filter filter) {
        modifiedZoneModel.setFilter(filter);
        modifiedZoneView.displayImage();
    }

    public void implementMeLater() {}
}
