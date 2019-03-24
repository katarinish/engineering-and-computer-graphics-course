package ru.nsu.fit.g16205.kovylina.model;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filters.*;
import ru.nsu.fit.g16205.kovylina.utils.FilterType;
import ru.nsu.fit.g16205.kovylina.utils.ImageZoneModel;

import java.awt.image.BufferedImage;

public class ModifiedZoneModel extends ImageZoneModel {
    private BufferedImage originalImage = null;
    private FilterType filter = null;

    @Override
    public void clear() {
        originalImage = null;
        filter = null;

        super.clear();
    }

    @Override
    public void setImage(BufferedImage image) {
        originalImage = image;
        BufferedImage filteredImage = null;

        if (this.filter == null
            || image == null) return;

        switch (this.filter) {
            case BLUR:
                filteredImage = new Blur().applyFilter(image);
                break;
            case BLACK_N_WHITE:
                filteredImage = new BlackAndWhite().applyFilter(image);
                break;
            case NEGATIVE:
                filteredImage = new Negative().applyFilter(image);
                break;
            case CONTOUR:
                filteredImage = new Contour().applyFilter(image);
                break;
            case SHARP:
                filteredImage = new Sharp().applyFilter(image);
                break;
            case AQUARELLE:
                filteredImage = new Aquarelle().applyFilter(image);
                break;
            case EMBOSS:
                filteredImage = new Emboss().applyFilter(image);
                break;
            case ROBERTS:
                filteredImage = new RobertsOperator().applyFilter(image);
                break;
            case SOBEL:
                filteredImage = new SobelOperator().applyFilter(image);
                break;
            case ORDERED_DITHERING:
                filteredImage = new OrderedDithering().applyFilter(image);
                break;
            case FLOYD_DITHERING:
                filteredImage = new FloydDithering().applyFilter(image);
                break;
        }

        super.setImage(filteredImage);
    }

    public void setFilter(FilterType filter) {
        this.filter = filter;

        setImage(originalImage);
    }
}
