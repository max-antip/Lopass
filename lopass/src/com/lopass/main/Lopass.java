package com.lopass.main;


import com.lopass.file.StorageService;
import com.lopass.gui.LoginFrame;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.InputStream;

public class Lopass {

    public static void main(String[] args) throws Exception {

        InputStream in = Lopass.class.getResourceAsStream("Arimo-Regular.ttf");

        Font font = FontUIResource.createFont(Font.TRUETYPE_FONT, in);
        font = font.deriveFont(13F);
        UIManager.put("Button.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("Textfield.font", font);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                if (StorageService.isInit()) {
                    StorageService.getInstance().saveBase();
                }
            }
        });
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        new LoginFrame();
    }

}
