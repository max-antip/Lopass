package com.lopass.test.gui;


import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class IconLabelTest extends JLabel {

    Timer enterTimer, exitTimer;
    Rectangle shape;
    Image img;
    int x = 5;
    int y = 5;
    float opaque = 0.4f;
    int iteration = 3;

    boolean toUp = true;

    IconLabelTest(String s) throws IOException {
        super(s);
        setPreferredSize(new Dimension(30, 30));
        shape = new Rectangle(x, y, 10, 10);
        ImageIcon icon = new ImageIcon(IconLabelTest.class.getResource("delete.png"));
        img = icon.getImage();

        enterTimer = new Timer(25, new EnterToLabelAction());
        exitTimer = new Timer(25, new ExitFromLabelAction());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitTimer.stop();
                enterTimer.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                enterTimer.stop();
                exitTimer.start();
            }
        });

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        AlphaComposite ac = java.awt.AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opaque);
        g2d.setComposite(ac);
        g2d.drawImage(img, x, y, 20, 20, this);
    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Gui test");
        frame.setLocationRelativeTo(null);
        frame.setSize(new Dimension(300, 300));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new MigLayout("", "10[]10", "10[]10"));

        frame.add(mainPanel, BorderLayout.CENTER);

        mainPanel.add(new IconLabelTest("Test"));

        frame.setVisible(true);

    }

    boolean big;

    private class EnterToLabelAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (opaque < 0.9F) {
                opaque = opaque + 0.1F;
                repaint();
            } else {
                enterTimer.stop();
            }
        }
    }

    private class ExitFromLabelAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (opaque > 0.4F) {
                opaque = opaque - 0.1F;
                repaint();
            } else {
                exitTimer.stop();
            }
        }
    }


    /*    @Override
  public void actionPerformed(ActionEvent e) {
    if (toUp) {
        shape.setLocation(x, ++y);
        iteration++;

        if (iteration == 3) {
            toUp = false;
        }
    } else {
        shape.setLocation(x, --y);
        iteration--;

        if (iteration == 0) {
            toUp = true;
        }
    }
    repaint();
}*/
}
