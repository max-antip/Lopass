package com.lopass.gui.icons;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Icons {

    public static final String LOGIN = "login.png";
    public static final String PASS = "pass.png";
    public static final String ADD = "add.png";
    public static final String BACK = "back.png";
    public static final String CLOSE = "close.png";
    public static final int SIZE_VERY_SMALL = 10;
    public static final int SIZE_SMALL_MIDDLE = 15;
    public static final int SIZE_MIDDLE = 25;


    public static ImageIcon getVerySmallIcon(String iconName) {
        return getIcon(iconName, SIZE_VERY_SMALL);
    }

    public static ImageIcon getSMiddleIcon(String iconName) {
        return getIcon(iconName, SIZE_SMALL_MIDDLE);
    }

    public static ImageIcon getMiddleIcon(String iconName) {
        return getIcon(iconName, SIZE_MIDDLE);
    }

    public static ImageIcon getIcon(String iconName, int size) {
        Image smallImg = null;
        try {
            URL url = Icons.class.getResource(iconName);
            BufferedImage image = ImageIO.read(new File(url.toURI()));

            smallImg = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {

        }
        return new ImageIcon(smallImg);
    }

    public static ImageIcon getIcon(String iconName) {
        Image smallImg = null;
        try {
            URL url = Icons.class.getResource(iconName);
            BufferedImage image = ImageIO.read(new File(url.toURI()));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {

        }
        return new ImageIcon(smallImg);
    }

}
