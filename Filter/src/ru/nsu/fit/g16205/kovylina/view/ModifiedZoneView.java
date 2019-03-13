package ru.nsu.fit.g16205.kovylina.view;

import ru.nsu.fit.g16205.kovylina.model.ModifiedZoneModel;
import ru.nsu.fit.g16205.kovylina.utils.ImageZoneView;

public class ModifiedZoneView extends ImageZoneView {
    private ModifiedZoneModel modifiedZoneModel;

    public ModifiedZoneView(ModifiedZoneModel modifiedZoneModel) {
        super(modifiedZoneModel);
        this.modifiedZoneModel = modifiedZoneModel;
    }
}
