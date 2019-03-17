package ru.nsu.fit.g16205.kovylina.view;

import ru.nsu.fit.g16205.kovylina.controller.Controller;
import ru.nsu.fit.g16205.kovylina.model.OriginalZoneModel;
import ru.nsu.fit.g16205.kovylina.utils.Constants;
import ru.nsu.fit.g16205.kovylina.utils.ImageZoneView;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class OriginalZoneView extends ImageZoneView {
    private Controller controller;
    private OriginalZoneModel originalZoneModel;

    public OriginalZoneView(Controller controller, OriginalZoneModel originalZoneModel) {
        super(originalZoneModel);
        this.originalZoneModel = originalZoneModel;
        this.controller = controller;

        initEventListeners();
    }

    private void initEventListeners() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!model.isInImage(e.getPoint())) return;

                controller.handleOrigZoneClick(e);
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!model.isInImage(e.getPoint())) return;

                controller.handleOrigZoneClick(e);
                repaint();            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintScaleFrame(g);
    }

    private void paintScaleFrame(Graphics g) {
        OriginalZoneModel.SubImageFrame frame = originalZoneModel.getSubImageFrame();
        if (frame == null) return;

        Point lCorner = frame.getLeftCorner();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Constants.BORDER_COLOR);
        Stroke dashed = new BasicStroke(2,
                BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, new float[]{1, 1}, 0);
        g2d.setStroke(dashed);
        g2d.drawRect(lCorner.x, lCorner.y, frame.getWidth(), frame.getHeight());

        g2d.dispose();
    }
}
