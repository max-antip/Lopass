package com.lopass.gui;


import com.lopass.event.EventBus;
import com.lopass.event.EventController;
import com.lopass.event.EventMessage;
import com.lopass.gui.custom.FrameLP;
import com.lopass.gui.helpers.FrameHelper;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class LoginFrame extends FrameLP implements EventController {

    public static final String WELCOME_TITLE = "Welcome";
    public static final String BUTTON_LOGIN = "Login";
    public static final Color MAIN_PANEL_BG = new Color(201, 240, 184);
    public static final String DATA_PATH = System.getProperty("user.dir") + "/data";
    JComboBox<String> loginField;
    JPasswordField passField;
    JButton submitBtn;
    JLabel statusLab;

    LoginFrameController controller;
    EventBus eventBus;

    public LoginFrame() {
        super(WELCOME_TITLE);

        controller = new LoginFrameController();
        eventBus = EventBus.getInstance();
        eventBus.addListener(this);
        setLayout(new BorderLayout());

        setPreferredSize(new Dimension(210, 160));
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                passField.requestFocus();
            }
        });

        setExitOnClose(true);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("", "20[grow,fill]20", "20[][][]20"));


        loginField = new JComboBox<>();
        loginField.setEditable(true);
        initLoginBox();

        passField = new JPasswordField();

        passField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    classicLogin();
                }
            }
        });

        submitBtn = new JButton(BUTTON_LOGIN);

        statusLab = new JLabel("");
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classicLogin();
            }
        });


        mainPanel.setPreferredSize(new Dimension());
        mainPanel.add(loginField, "wrap");
        mainPanel.add(passField, "wrap");
        mainPanel.add(submitBtn, "w 70!,wrap,align right");
        mainPanel.add(statusLab, "wrap,align left");
        mainPanel.setBackground(MAIN_PANEL_BG);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
        pack();

        FrameHelper.setToCenterScreen(this);
    }

    private void classicLogin() {
        if (loginField.getSelectedItem() != null && !loginField.getSelectedItem().equals("")) {
            controller.submit((String) loginField.getSelectedItem(), passField.getPassword());
        }
    }

    private void initLoginBox() {
        File dataDir = new File(DATA_PATH);
        File[] files = dataDir.listFiles();

        if (files != null) {
            for (File f : files) {
                String fileName = f.getName();
                if (fileName.contains(".")) {
                    int stopIdx = fileName.indexOf(".");
                    fileName = fileName.substring(0, stopIdx);
                }
                loginField.addItem(fileName);
            }
        }
    }

    @Override
    public void onEvent(String eventName, Object obj) {
        switch (eventName) {
            case EventMessage.AUTHORIZE_WRONG:
                if (obj instanceof String) {
                    String status = (String) obj;
                    statusLab.setText(status);
                    statusLab.setForeground(Color.RED);
                }
                break;
            case EventMessage.FIRST_LOAD:
                dispose();
                break;
        }
    }

}
