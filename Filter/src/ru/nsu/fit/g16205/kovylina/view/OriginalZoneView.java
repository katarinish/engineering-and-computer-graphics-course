package ru.nsu.fit.g16205.kovylina.view;

import ru.nsu.fit.g16205.kovylina.model.OriginalZoneModel;
import ru.nsu.fit.g16205.kovylina.utils.ImageZoneView;

public class OriginalZoneView extends ImageZoneView {
    private OriginalZoneModel originalZoneModel;

    public OriginalZoneView(OriginalZoneModel originalZoneModel) {
        super(originalZoneModel);
        this.originalZoneModel = originalZoneModel;
    }
}
