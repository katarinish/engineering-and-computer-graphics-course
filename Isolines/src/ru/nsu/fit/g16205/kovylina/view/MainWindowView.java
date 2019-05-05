package ru.nsu.fit.g16205.kovylina.view;

import ru.nsu.fit.g16205.kovylina.controller.Controller;
import ru.nsu.fit.g16205.kovylina.utils.ActionHandler;

import javax.swing.*;
import java.awt.*;

public class MainWindowView extends JFrame {
    private Font font;

    private Controller controller;

    public MainWindowView(Controller controller) {
        super("Isolines");

        this.font = new Font("Verdana", Font.PLAIN, 11);
        this.controller = controller;

        initMenu();
        initToolBar();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public MainWindowView(Controller controller, JPanel panel) {
        this(controller);
        add(panel);

        this.setMinimumSize(panel.getPreferredSize());
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = createMenu("File");
        addMenuItem(fileMenu, "Open", controller::implementMeLater);

        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);
    }

    private void initToolBar() {
        JToolBar toolBar = new JToolBar();

        toolBar.setRollover(true);
        add(toolBar, BorderLayout.PAGE_START);

        addToolbarItem(toolBar, "new.png",
                "Открыть новый файл", controller::implementMeLater);
        toolBar.addSeparator();
        addToolbarItem(toolBar, "circle.png",
                "Отображать границу входа изолинии", controller::handleIntersection);
        addToolbarItem(toolBar, "grid.png",
                "Отобразить сетку", controller::handleToggleGrid);
        addToolbarItem(toolBar, "isolines.png",
                "Отобразить изолинии", controller::handleToggleIsilones);
        addToolbarItem(toolBar, "interpolation.png",
                "Включить интерполяцию цвета", controller::handleInterpolation);
        toolBar.addSeparator();
        addToolbarItem(toolBar, "custom.png",
                "Прорисовка изолинии точечного значения", controller::handleEnableCustomIsolines);
        addToolbarItem(toolBar, "dynamic.png",
                "Непрерывное отображение изолинии", controller::handleEnableDynamicIsolines);
        toolBar.addSeparator();

        addToolbarItem(toolBar, "settings.png",
                "Очистить карту", controller::hadleOpenSettings);
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
