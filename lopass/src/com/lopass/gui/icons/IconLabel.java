package com.lopass.gui.icons;


import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IconLabel extends JPanel {

    public static final Border LINE_BORDER = BorderFactory.
            createLineBorder(new Color(155, 230, 145));

    public static final Color PRESSED_BG_COLOR = new Color(171, 230, 165);

    public static final Border BORDER_lINE_RED = BorderFactory.
            createLineBorder(new Color(230, 159, 139));
    public static final Border EMPTY_BORDER = BorderFactory.createLineBorder(new Color(0, 0, 0, 0F));

    private JLabel textLbl, iconLbl;

    public IconLabel(String text, Icon icon) {
        super(new MigLayout("", "[]5[]", "[]"));
        setBorder(EMPTY_BORDER);
        initMouseListeners();
        if (text == null || icon == null) throw new NullPointerException("Icon or text is null");

        textLbl = new JLabel(text);
        iconLbl = new JLabel(icon);


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
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(null);
            }
        });
    }

}
