package ru.nsu.fit.g16205.kovylina.view;

import ru.nsu.fit.g16205.kovylina.model.ScaledZoneModel;
import ru.nsu.fit.g16205.kovylina.utils.ImageZone;

public class ScaledZoneView extends ImageZone {
    private ScaledZoneModel scaledZoneModel;

    public ScaledZoneView(ScaledZoneModel scaledZoneModel) {
        super();
        this.scaledZoneModel = scaledZoneModel;
    }
}
