package com.lopass.gui.icons;


import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IconLabel extends JPanel {

    public static final Border LINE_BORDER = BorderFactory.
            createLineBorder(new Color(155, 230, 145));

    public static final Color PRESSED_BG_COLOR = new Color(171, 230, 165);

    public static final Border BORDER_lINE_RED = BorderFactory.
            createLineBorder(new Color(230, 159, 139));
    public static final Border EMPTY_BORDER = BorderFactory.createLineBorder(new Color(0, 0, 0, 0F));
    public static final int TEXT_FlD_SIZE = 10;

    private JLabel textLbl, iconLbl;
    private JTextField editTextField;

    public IconLabel(String text, Icon icon) {
        super(new MigLayout("", "[]5[]", "[]"));
        setBorder(EMPTY_BORDER);
        initMouseListeners();
        if (text == null || icon == null) throw new NullPointerException("Icon or text is null");

        textLbl = new JLabel(text);
        iconLbl = new JLabel(icon);

        editTextField = new JTextField(TEXT_FlD_SIZE);
        editTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String text = editTextField.getText();
                    textLbl.setText(text);

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            remove(editTextField);
                            add(textLbl, 1);

                            repaint();
                            revalidate();

                        }
                    });

                }
            }
        });

        add(iconLbl);
        add(textLbl);

    }

    public String getText() {
        return textLbl.getText();
    }

    private void initMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(LINE_BORDER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(EMPTY_BORDER);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(PRESSED_BG_COLOR);

                if (e.getClickCount() == 2) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            remove(textLbl);
                            add(editTextField, 1);
                            repaint();
                            revalidate();
                        }
                    });
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(null);
            }
        });
    }

}
