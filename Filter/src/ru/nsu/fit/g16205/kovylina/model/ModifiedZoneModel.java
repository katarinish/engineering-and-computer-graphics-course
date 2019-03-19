package ru.nsu.fit.g16205.kovylina.model;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Blur;
import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;
import ru.nsu.fit.g16205.kovylina.utils.ImageZoneModel;

import java.awt.image.BufferedImage;

public class ModifiedZoneModel extends ImageZoneModel {

    @Override
    public void setImage(BufferedImage image) {
        Filter filter = new Blur();
        super.setImage(filter.applyFilter(image));
    }
}
