package com.lopass.gui.helpers;


import javax.swing.*;
import java.awt.*;

public class FrameHelper {

    public static final int screenWidth =
            (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int screenHeight =
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    //        Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();


    public static void setToCenterScreen(JFrame frame) {


        int x = (screenWidth / 2 - frame.getWidth() / 2);
        int y = (screenHeight / 2 - frame.getHeight() / 2);

        frame.setLocation(x, y);

    }

}
