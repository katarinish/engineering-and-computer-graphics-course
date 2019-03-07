package ru.nsu.cg.kovylina.view;
import ru.nsu.cg.kovylina.controller.LifeGameController;
import ru.nsu.cg.kovylina.utils.MenuItemActionListener;

import javax.swing.*;
import java.awt.*;

public class MainWindowView extends JFrame {
    private Font font;
    private JMenuBar menuBar;
    private JToolBar toolBar;
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
        gameMenu.addSeparator();
        addMenuItem(gameMenu, "Clear Field", controller::handleClearField);

        JMenu modeMenu = new JMenu("Mode");
        modeMenu.setFont(font);
        addMenuItem(modeMenu, "XOR", controller::handleSetXOR);
        addMenuItem(modeMenu, "Replace", controller::handleSetReplace);
        gameMenu.addSeparator();
        gameMenu.add(modeMenu);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setFont(font);
        addMenuItem(helpMenu, "About", this::handleOnAbout);
        addMenuItem(helpMenu, "Settings", controller::handleOpenOptions);

        menuBar.add(fileMenu);
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

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

        addToolbarItem(toolBar, "Exit.png", "Выход", controller::addAction);

        toolBar.addSeparator();

        addToolbarItem(toolBar, "Play.png", "Запустить игру",
                controller::handleStartGame);
        addToolbarItem(toolBar, "Next.png", "Показать следующее поколение",
                controller::handleNextGen);
        addToolbarItem(toolBar, "Stop.png", "Остановить игру",
                controller::handlePauseGame);

        toolBar.addSeparator();

        addToolbarItem(toolBar, "XOR.png", "Установить закраску XOR",
                controller::handleSetXOR);
        addToolbarItem(toolBar, "Replace.png", "Установить закраску Replace",
                controller::handleSetReplace);

        toolBar.addSeparator();

        addToolbarItem(toolBar, "Refresh.png", "Очистить поле",
                controller::handleClearField);
        addToolbarItem(toolBar, "Settings.png", "Открыть настройки",
                controller::handleOpenOptions);

        toolBar.addSeparator();

        addToolbarItem(toolBar, "About.png", "Об авторе",
                this::handleOnAbout);
    }

    private void addToolbarItem(JToolBar toolBar,
                                String icon, String tooltip,
                                MenuItemActionListener listener) {
        JButton button = new JButton();
        Image img = new ImageIcon(getClass().getResource("resources/" + icon))
                .getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);

        button.setIcon(new ImageIcon(img));
        button.setToolTipText(tooltip);
        button.addActionListener(e -> listener.execute());

        toolBar.add(button);
    }

    public void handleOnAbout() {
        JOptionPane.showMessageDialog(this,
                "2019 Kovylina Yekaterina, FIT, group 16205",
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
