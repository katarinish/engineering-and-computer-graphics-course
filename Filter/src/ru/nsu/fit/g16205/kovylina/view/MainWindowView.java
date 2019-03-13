package ru.nsu.fit.g16205.kovylina.view;

import ru.nsu.fit.g16205.kovylina.controller.Controller;
import ru.nsu.fit.g16205.kovylina.utils.ActionHandler;

import javax.swing.*;
import java.awt.*;

public class MainWindowView extends JFrame {
    private Font font;

    private Controller controller;

    public MainWindowView(Controller controller) {
        super("Filter");

        this.font = new Font("Verdana", Font.PLAIN, 11);
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

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = createMenu("File");
        addMenuItem(fileMenu, "New document", controller::implementMeLater);
        addMenuItem(fileMenu, "Select", controller::handleSelectImage);

        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);
    }

    private void initToolBar() {
    }

    private void addMenuItem(JMenu menu, String item, ActionHandler handler) {
        JMenuItem menuItem = new JMenuItem(item);
        menuItem.setFont(font);
        menu.add(menuItem);
        menuItem.addActionListener(e -> handler.run());
    }

    private JMenu createMenu(String menu) {
        JMenu menuItem = new JMenu(menu);
        menuItem.setFont(font);

        return menuItem;
    }
}
