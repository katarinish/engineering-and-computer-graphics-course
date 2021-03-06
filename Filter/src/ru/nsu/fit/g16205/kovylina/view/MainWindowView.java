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
        addMenuItem(fileMenu, "Save Image", controller::handleSaveImage);

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
                "Применить черно-белый фильтр", controller::handleBnWFilter);
        addToolbarItem(toolBar, "smooth.png",
                "Применить сглаживающий фильтр", controller::handleBlurFilter);
        addToolbarItem(toolBar, "negative.png",
                "Применить негативный фильтр", controller::handleNegativeFilter);
        addToolbarItem(toolBar, "gamma.png",
                "Гамма коррекция", controller::handleGamma);
        addToolbarItem(toolBar, "zoom.png",
                "Увеличение в 2 раза", controller::handleZoom);
        addToolbarItem(toolBar, "rotate.png",
                "Поворот", controller::handleRotation);

        toolBar.addSeparator();

        addToolbarItem(toolBar, "sobel.png",
                "Применить оператор Собеля", controller::handleSobelFilter);
        addToolbarItem(toolBar, "roberts.png",
                "Применить оператор Робертса", controller::handleRobertsFilter);
        addToolbarItem(toolBar, "contour.png",
                "Применить фильтр выделения границ", controller::handleContourFilter);

        toolBar.addSeparator();

        addToolbarItem(toolBar, "sharp.png",
                "Применить фильтр увеличения резкости", controller::handleSharpFilter);
        addToolbarItem(toolBar, "aqua.png",
                "Применить фильтр акварелизации", controller::handleAquarelleFilter);
        addToolbarItem(toolBar, "emboss.png",
                "Применить фильтр тиснения", controller::handleEmbossFilter);

        toolBar.addSeparator();

        addToolbarItem(toolBar, "ordered.png",
                "Применить упорядоченный дизеринг", controller::handleOrderedDithering);
        addToolbarItem(toolBar, "floyd.png",
               "Применить дизернинг по Флойду", controller::handleFloydDithering);
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
