package com.lopass.main;


import com.lopass.file.StorageService;
import com.lopass.gui.LoginFrame;

import javax.swing.*;

public class Lopass {

    public static void main(String[] args) throws Exception {

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                if (StorageService.isInit()) {
                    StorageService.getInstance().saveBase();
                }
            }
        });
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        LoginFrame lf = new LoginFrame();
        lf.setVisible(true);
        lf.pack();
    }

}
