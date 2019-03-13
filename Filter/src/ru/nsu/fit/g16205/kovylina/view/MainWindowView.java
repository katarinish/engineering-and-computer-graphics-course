package ru.nsu.fit.g16205.kovylina.view;

import ru.nsu.fit.g16205.kovylina.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MainWindowView extends JFrame {
    private Font font;

    private Controller controller;

    public MainWindowView(Controller controller) {
        this.controller = controller;

        initMenu();
        initToolBar();

        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public MainWindowView(Controller controller, JPanel panel) {
        this(controller);
        add(new JScrollPane(panel));
    }

    //TODO: implement
    private void initMenu() {
    }

    //TODO: implement
    private void initToolBar() {
    }
}
