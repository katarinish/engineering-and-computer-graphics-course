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
    private JRadioButton Replace;
    private JSlider cellSizeSlider;
    private JTextField cellSizeTextField;
    private JRadioButton impactRadioButton;
    private JRadioButton cellStateRadioButton;
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
    private JCheckBox impactCheck;
    private JTextField lineWidthField;
    private JPanel drawParamsContainer;

    public OptionsView(LifeGameController controller) {
        this.controller = controller;
        initActionPerformances();
        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);
    }

    public void setCustomSettings(Configuration c) {
        cellSizeTextField.setText(String.valueOf(c.getHexSide()));
        rowsTextField.setText(String.valueOf(c.getRows()));
        columnsTextField.setText(String.valueOf(c.getColumns()));
        lineWidthField.setText(String.valueOf(c.getBoundaryWidth()));
        if ((c.getColorMode() == ColorMode.IMPACT)) {
            impactRadioButton.setSelected(true);
        } else {
            cellStateRadioButton.setSelected(true);
        }
        if(c.getMode() == Mode.XOR) {
            XOR.setSelected(true);
        } else {
            Replace.setSelected(false);
        }
    }

    private void initActionPerformances() {
        acceptButton.addActionListener(e -> {
            if (!nonEmptyValidation()) {
                JOptionPane.showMessageDialog(this,
                        "Bad settings parameters",
                        "Error",
                        JOptionPane.INFORMATION_MESSAGE);

                close();
                return;
            }

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
                if (!(e.getKeyCode() == KeyEvent.VK_ENTER)) return;

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
                if (!(e.getKeyCode() == KeyEvent.VK_ENTER)) return;

                String typed = rowsTextField.getText();

                if (!isInteger(typed)) {
                    rowsTextField.setText(String.valueOf(Constants.ROWS));
                }
            }
        });

        columnsTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!(e.getKeyCode() == KeyEvent.VK_ENTER)) return;

                String typed = columnsTextField.getText();

                if (!isInteger(typed)) {
                    columnsTextField.setText(String.valueOf(Constants.COLUMNS));
                }
            }
        });

        lineWidthField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!(e.getKeyCode() == KeyEvent.VK_ENTER)) return;

                String typed = lineWidthField.getText();

                if (!isInteger(typed)) {
                    lineWidthField.setText(String.valueOf(Constants.BOUNDARY_WIDTH));
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
        if (isEmptyString(s)) return false;

        return s.matches("^\\d+$");
    }

    private boolean isEmptyString(String s) {
        return  s.equals("");
    }

    private boolean nonEmptyValidation() {
        return !isEmptyString(rowsTextField.getText())
                && !isEmptyString(columnsTextField.getText())
                && !isEmptyString(cellSizeTextField.getText())
                && !isEmptyString(lineWidthField.getText());
    }
}
