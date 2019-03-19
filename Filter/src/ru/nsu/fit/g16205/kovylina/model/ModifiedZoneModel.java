package ru.nsu.fit.g16205.kovylina.model;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filters.BlackAndWhite;
import ru.nsu.fit.g16205.kovylina.buisness_logic.Filters.Blur;
import ru.nsu.fit.g16205.kovylina.buisness_logic.Filters.Negative;
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
        }

        super.setImage(filteredImage);
    }

    public void setFilter(FilterType filter) {
        this.filter = filter;

        setImage(originalImage);
    }
}
