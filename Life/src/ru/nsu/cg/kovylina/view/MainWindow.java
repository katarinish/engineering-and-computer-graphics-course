package ru.nsu.cg.kovylina.view;

import ru.nsu.cg.kovylina.utils.MainFrame;
import ru.nsu.cg.kovylina.view.GameFieldView;

import java.awt.event.KeyEvent;
import javax.swing.*;

public class MainWindow extends MainFrame {

    public MainWindow(GameFieldView gameFieldView) {
        super(600, 600, "Life Game");

        try {
            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/Exit",
                    "Exit application",
                    KeyEvent.VK_X,
                    "Exit.gif",
                    "onExit");

            addSubMenu("Help",
                    KeyEvent.VK_H);
            addMenuItem("Help/About...",
                    "Shows program version and copyright information",
                    KeyEvent.VK_A,
                    "About.gif",
                    "onAbout");

            addToolBarButton("File/Exit");
            addToolBarSeparator();
            addToolBarButton("Help/About...");
            add(new JScrollPane(gameFieldView));
//            add(gameFieldView);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onAbout() {
        JOptionPane.showMessageDialog(this,
                "2019 Kovylina Yekaterina, FIT, group 16205",
                "About Init",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void onExit() {
        System.exit(0);
    }
}
