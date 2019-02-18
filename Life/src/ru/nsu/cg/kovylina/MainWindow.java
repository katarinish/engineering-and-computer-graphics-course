package ru.nsu.cg.kovylina;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class MainWindow extends MainFrame {

    public MainWindow() {
        super(600, 400, "Init application");

        try {
            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/Exit", "Exit application", KeyEvent.VK_X, "Exit.gif", "onExit");
            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About...", "Shows program version and copyright information", KeyEvent.VK_A, "About.gif", "onAbout");

            addToolBarButton("File/Exit");
            addToolBarSeparator();
            addToolBarButton("Help/About...");

            add(new InitView());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        MainWindow mainFrame = new MainWindow();
        mainFrame.setVisible(true);
    }
    public void onAbout() {
        JOptionPane.showMessageDialog(this,
                "Init, version 1.0\nCopyright � 2010 Vasya Pupkin, FF, group 1234",
                "About Init", JOptionPane.INFORMATION_MESSAGE);
    }

    public void onExit() {
        System.exit(0);
    }


}
