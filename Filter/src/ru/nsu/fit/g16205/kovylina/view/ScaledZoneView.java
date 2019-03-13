package ru.nsu.fit.g16205.kovylina.view;

import ru.nsu.fit.g16205.kovylina.model.ScaledZoneModel;
import ru.nsu.fit.g16205.kovylina.utils.ImageZoneView;

public class ScaledZoneView extends ImageZoneView {
    private ScaledZoneModel scaledZoneModel;

    public ScaledZoneView(ScaledZoneModel scaledZoneModel) {
        super(scaledZoneModel);
        this.scaledZoneModel = scaledZoneModel;
    }
}
