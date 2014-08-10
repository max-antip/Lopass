package com.lopass.test.gui;

import javax.swing.*;
import java.awt.*;

public class PopupTest {

    public PopupTest() {
        JFrame frame = new JFrame("Popup Test");
        JPanel panel = new JPanel();
        panel.setBackground(Color.cyan);

        JPopupMenu menu = new JPopupMenu();
        menu.add(new JMenuItem("Test"));

        panel.setComponentPopupMenu(menu);

        frame.setSize(new Dimension(600, 800));
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
        new PopupTest();
    }
}
