package ru.nsu.cg.kovylina.view;

import ru.nsu.cg.kovylina.controller.LifeGameController;
import ru.nsu.cg.kovylina.utils.ColorMode;
import ru.nsu.cg.kovylina.utils.Configuration;
import ru.nsu.cg.kovylina.utils.Constants;
import ru.nsu.cg.kovylina.utils.Mode;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OptionsView extends JFrame{
    private LifeGameController controller;
    private JPanel rootPanel;

    private JRadioButton XOR;

    private JSlider cellSizeSlider;
    private JTextField cellSizeTextField;

    private JRadioButton impactRadioButton;

    private JTextField rowsTextField;
    private JTextField columnsTextField;

    private JButton acceptButton;

    private JCheckBox impactCheck;
    private JTextField lineWidthField;

    public OptionsView(LifeGameController controller) {
        this.controller = controller;
        initActionPerformances();
        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void initActionPerformances() {
        acceptButton.addActionListener(e -> {
            int rows = Integer.parseInt(rowsTextField.getText());
            int col = Integer.parseInt(columnsTextField.getText());

            int hex = Integer.parseInt(cellSizeTextField.getText());
            int boundaryWidth = Integer.parseInt(lineWidthField.getText());

            Mode mode = XOR.isSelected() ? Mode.XOR : Mode.REPLACE;
            ColorMode colorMode = impactRadioButton.isSelected()
                    ? ColorMode.IMPACT : ColorMode.CELL_STATE;

            boolean showImpact = impactCheck.isSelected();

            Configuration c = new Configuration(rows, col,
                    hex, boundaryWidth,
                    colorMode, showImpact, mode);

            if (!controller.handleAcceptSettings(c)) {
                JOptionPane.showMessageDialog(this,
                        "Bad settings parameters",
                        "Error",
                        JOptionPane.INFORMATION_MESSAGE);
            }

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

                if (!isInteger(typed)) {
                    cellSizeTextField.setText(String.valueOf(Constants.HEX_SIDE));
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

        rowsTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String typed = rowsTextField.getText();

                if (!isInteger(typed)) {
                    rowsTextField.setText(String.valueOf(Constants.ROWS));
                }
            }
        });

        columnsTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String typed = columnsTextField.getText();

                if (!isInteger(typed)) {
                    columnsTextField.setText(String.valueOf(Constants.COLUMNS));
                }
            }
        });

        lineWidthField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String typed = lineWidthField.getText();

                if (!isInteger(typed)) {
                    lineWidthField.setText("");
                }
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
