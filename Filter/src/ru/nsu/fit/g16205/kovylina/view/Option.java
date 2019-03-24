package ru.nsu.fit.g16205.kovylina.view;

import ru.nsu.fit.g16205.kovylina.utils.OptionHandler;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Option extends JFrame {
    private OptionHandler handler;
    private JTextField textField;
    private JSlider slider;
    private JButton okButton;
    private JPanel rootPanel;

    public Option(OptionHandler handler, int min, int max) {
        this.handler = handler;

        slider.setMinimum(min);
        slider.setMaximum(max);

        initActionPerformances();

        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void initActionPerformances() {
        slider.addChangeListener(e -> {
            textField.setText(String.valueOf(slider.getValue()));
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                String typed = textField.getText();
                int value = Integer.parseInt(typed);
                slider.setValue(value);
            }
        });

        okButton.addActionListener(e -> {
            int val = slider.getValue();
            close();

            handler.run(val);
        });
    }

    public void open() {
        setVisible(true);
    }

    public void close() {
        dispose();
    }
}
