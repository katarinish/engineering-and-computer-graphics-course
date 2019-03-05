package ru.nsu.cg.kovylina.view;
import ru.nsu.cg.kovylina.controller.LifeGameController;
import ru.nsu.cg.kovylina.utils.MenuItemActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindowView extends JFrame {
    Font font;
    JMenuBar menuBar;
    JToolBar toolBar;
    private LifeGameController controller;

    public MainWindowView(LifeGameController controller) {
        super("Life Game");
        this.font = new Font("Verdana", Font.PLAIN, 11);
        this.controller = controller;
        initMenu();
        initToolBar();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public MainWindowView(GameFieldView gameFieldView, LifeGameController controller) {
        this(controller);
        add(new JScrollPane(gameFieldView));
    }

    private void initMenu() {
        this.menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(font);
        addMenuItem(fileMenu, "New Document", controller::addAction);
        addMenuItem(fileMenu, "Read from...", controller::addAction);
        addMenuItem(fileMenu, "Write to...", controller::addAction);
        fileMenu.addSeparator();
        addMenuItem(fileMenu, "Exit", controller::addAction);

        JMenu gameMenu = new JMenu("Game");
        gameMenu.setFont(font);
        addMenuItem(gameMenu, "Start", controller::handleStartGame);
        addMenuItem(gameMenu, "Next Generation", controller::handleNextGen);
        addMenuItem(gameMenu, "Pause", controller::handlePauseGame);


        menuBar.add(fileMenu);
        menuBar.add(gameMenu);

        this.setJMenuBar(menuBar);
    }

    private void addMenuItem(JMenu menu, String item, MenuItemActionListener listener) {
        JMenuItem menuItem = new JMenuItem(item);
        menuItem.setFont(font);
        menu.add(menuItem);
        menuItem.addActionListener(e -> listener.execute());
    }

    private void initToolBar() {
        toolBar = new JToolBar("Main toolbar");
        toolBar.setRollover(true);
        add(toolBar, BorderLayout.PAGE_START);
    }
}
