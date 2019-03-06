package ru.nsu.cg.kovylina.view;

import ru.nsu.cg.kovylina.controller.LifeGameController;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OptionsView extends JFrame{
    private LifeGameController controller;
    private JPanel rootPanel;

    private JRadioButton XOR;
    private JRadioButton Replace;
    private JSlider cellSizeSlider;
    private JTextField cellSizeTextField;
    private JRadioButton impactRadioButton;
    private JRadioButton cellStateRadioButton;
    private JTextField boundaryTextField;
    private JLabel boundaryLabel;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JTextField rowsTextField;
    private JTextField columnsTextField;
    private JPanel gameModeContainer;
    private JPanel colorModeContainer;
    private JPanel fieldSizeContainer;
    private JLabel rowsLabel;
    private JLabel columnsLabel;

    private JButton acceptButton;

    public OptionsView(LifeGameController controller) {
        this.controller = controller;
        initActionPerformances();
        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void initActionPerformances() {
        acceptButton.addActionListener(e -> {
//            String columns = columnsTextField.getText();
//
//            Configuration с = new Configuration()
//            controller.handleAcceptSettings(с);

            close();
        });

        cellSizeSlider.addChangeListener(e -> {
            cellSizeTextField.setText(String.valueOf(cellSizeSlider.getValue()));
        });

        cellSizeTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String typed = cellSizeTextField.getText();
                cellSizeSlider.setValue(0);

                if (isInteger(typed)) {
                    cellSizeTextField.setText("");
                    return;
                }

                int value = Integer.parseInt(typed);
                if (value < cellSizeSlider.getMinimum()) {
                    value = cellSizeSlider.getMinimum();
                    cellSizeTextField.setText(String.valueOf(value));
                }
                cellSizeSlider.setValue(value);
            }
        });
    }

    public void open() {
        setVisible(true);
    }

    public void close() {
        dispose();
    }

    private boolean isInteger(String s) {
        return s.matches("^\\d+$");
    }
}
