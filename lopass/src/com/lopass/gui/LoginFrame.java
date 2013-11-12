package com.lopass.gui;


import com.lopass.event.EventBus;
import com.lopass.event.EventController;
import com.lopass.event.EventMessage;
import com.lopass.gui.custom.FrameLP;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends FrameLP implements EventController {

    public static final String WELCOME_TITLE = "Welcome";
    public static final String BUTTON_LOGIN = "Login";
    public static final Color MAIN_PANEL_BG = new Color(201, 240, 184);

    JTextField loginField;
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
        setLocationRelativeTo(null);
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

        loginField = new JTextField();
        passField = new JPasswordField();

        passField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    controller.submit(loginField.getText(), passField.getPassword());
                }
            }
        });

        submitBtn = new JButton(BUTTON_LOGIN);

        statusLab = new JLabel("");
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.submit(loginField.getText(), passField.getPassword());
            }
        });

        mainPanel.setPreferredSize(new Dimension());
        mainPanel.add(loginField, "wrap");
        mainPanel.add(passField, "wrap");
        mainPanel.add(submitBtn, "w 70!,wrap,align right");
        mainPanel.add(statusLab, "wrap,align left");
        mainPanel.setBackground(MAIN_PANEL_BG);

        add(mainPanel, BorderLayout.CENTER);

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
