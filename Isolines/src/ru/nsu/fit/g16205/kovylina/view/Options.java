package ru.nsu.fit.g16205.kovylina.view;

import ru.nsu.fit.g16205.kovylina.controller.Controller;
import ru.nsu.fit.g16205.kovylina.model.MapModel;
import ru.nsu.fit.g16205.kovylina.utils.Configuration;

import javax.swing.*;

public class Options extends JFrame {
    private MapModel model;
    private Controller controller;

    private JPanel rootPanel;
    private JTextField kTF;
    private JTextField mTF;
    private JLabel mLabel;
    private JTextField aTF;
    private JTextField bTF;
    private JTextField dTF;
    private JTextField cTF;
    private JButton acceptButton;
    private JButton cancelButton;
    
    public Options(MapModel model, Controller controller) {
        this.model = model;
        this.controller = controller;

        initActionPerformances();

        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void initActionPerformances() {
        Configuration oldConfig = model.getConfiguration();

        acceptButton.addActionListener(e -> {

            int k = Integer.parseInt(kTF.getText());
            int m = Integer.parseInt(mTF.getText());

            int a = Integer.parseInt(aTF.getText());
            int b = Integer.parseInt(bTF.getText());
            int c = Integer.parseInt(cTF.getText());
            int d = Integer.parseInt(dTF.getText());

            Configuration configuration = new Configuration(oldConfig.getN(),k, m, a, b, c, d,
                    oldConfig.getColors());
            controller.setNewSettings(configuration);
            close();
        });

        kTF.setText(String.valueOf(oldConfig.getK()));
        mTF.setText(String.valueOf(oldConfig.getM()));
        aTF.setText(String.valueOf(oldConfig.getA()));
        bTF.setText(String.valueOf(oldConfig.getB()));
        cTF.setText(String.valueOf(oldConfig.getC()));
        dTF.setText(String.valueOf(oldConfig.getD()));

        cancelButton.addActionListener(e -> close());
    }

    public void open() {
        setVisible(true);
    }

    public void close() {
        dispose();
    }
}
