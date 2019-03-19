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
        JToolBar toolBar = new JToolBar();

        toolBar.setRollover(true);
        add(toolBar, BorderLayout.PAGE_START);

        addToolbarItem(toolBar, "select.png",
                "Выбрать изображение", controller::handleSelectImage);
        addToolbarItem(toolBar, "clear.png",
                "Очистить зоны", controller::handleClearZones);

        toolBar.addSeparator();

        addToolbarItem(toolBar, "bw.png",
                "Применить черно-белый фильтр", controller::handleBnWFilterClick);
        addToolbarItem(toolBar, "smooth.png",
                "Применить сглаживающий фильтр", controller::handleBlurFilterClick);
        addToolbarItem(toolBar, "negative.png",
                "Применить негативный фильтр", controller::handleNegativeFilterClick);

        toolBar.addSeparator();

        addToolbarItem(toolBar, "contour.png",
                "Применить фильтр выделения границ", controller::handleContourFilterClick);

        toolBar.addSeparator();

        addToolbarItem(toolBar, "sharp.png",
                "Применить фильтр увеличения резкости", controller::handleSharpFilterClick);
    }

    private void addMenuItem(JMenu menu, String item, ActionHandler handler) {
        JMenuItem menuItem = new JMenuItem(item);
        menuItem.setFont(font);
        menu.add(menuItem);
        menuItem.addActionListener(e -> handler.run());
    }

    private void addToolbarItem(JToolBar toolBar,
                                String icon, String tooltip,
                                ActionHandler handler) {
        JButton button = new JButton();
        Image img = new ImageIcon(getClass().getResource("resources/" + icon))
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        button.setIcon(new ImageIcon(img));
        button.setToolTipText(tooltip);
        button.addActionListener(e -> handler.run());

        toolBar.add(button);
    }

    private JMenu createMenu(String menu) {
        JMenu menuItem = new JMenu(menu);
        menuItem.setFont(font);

        return menuItem;
    }
}
