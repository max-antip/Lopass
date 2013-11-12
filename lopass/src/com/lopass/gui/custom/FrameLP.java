package com.lopass.gui.custom;

import com.lopass.gui.icons.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class FrameLP extends JFrame {
    CloseButtonLP closeBtn;
    boolean exitOnClose;

    int pressX;
    int pressY;

    public FrameLP(String title) {
        super(title);
        setUndecorated(true);
        ImageIcon ic = Icons.getIcon(Icons.CLOSE);

        closeBtn = new CloseButtonLP(this, ic.getImage());
        closeBtn.setWidth(13);
        closeBtn.setHeight(13);


        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                pressX = e.getX();
                pressY = e.getY();
                if (closeBtn.getBounds().contains(e.getX(), e.getY())) {
                    closeBtn.setVisible(false);
                    dispose();
                    if (exitOnClose){
                        System.exit(0);
                    }
                }

            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                Point point = e.getLocationOnScreen();
                setLocation((int) point.getX() - pressX, (int) point.getY() - pressY);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (closeBtn.getBounds().contains(e.getX(), e.getY())) {
                    closeBtn.appear();
                } else {
                    closeBtn.disappear();
                }
            }
        });

//        setMinimumSize(new Dimension(300, 230));


    }

    public void paint(Graphics g) {
        super.paint(g);
        closeBtn.paint(g);

    }

    public static void main(String[] args) {
        FrameLP fr = new FrameLP("Test");
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.setVisible(true);
    }

    @Override
    public void dispose() {
        super.dispose();
        closeBtn.setVisible(false);
    }

    public boolean isExitOnClose() {
        return exitOnClose;
    }

    public void setExitOnClose(boolean exitOnClose) {
        this.exitOnClose = exitOnClose;
    }
}
