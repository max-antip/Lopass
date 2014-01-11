package com.lopass.gui;


import com.lopass.event.EventController;
import com.lopass.event.EventMessage;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertPassFrame extends JFrame implements EventController {

    public static final String ADD = "Add";
    public static final Font FONT = new Font("Arial", Font.PLAIN, 16);
    public static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 12);
    public static final Color COLOR_BACKGROUND = new Color(255, 198, 169);
    private JTextField titleField, passField, loginField;
    private JButton addBut;

    InsertFrameController controller;

    public InsertPassFrame() {
        controller = new InsertFrameController();

        settingFrame();

        titleField = new JTextField();
        titleField.setFont(FONT);

        loginField = new JTextField();
        loginField.setFont(FONT);

        passField = new JTextField();
        passField.setFont(FONT);

        addBut = new JButton(ADD);
        //todo если поля пустые то кнопка не активна !

        addBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.addRecord(titleField.getText(), loginField.getText(), passField.getText());
                dispose();
            }
        });

        JLabel titleLbl = new JLabel("Title");
        titleLbl.setFont(LABEL_FONT);
        add(titleLbl);
        add(titleField, "w 140!,wrap");

        JLabel loginLbl = new JLabel("Login");
        loginLbl.setFont(LABEL_FONT);
        add(loginLbl);
        add(loginField, "w 140!,wrap");

        JLabel passLbl = new JLabel("Password");
        passLbl.setFont(LABEL_FONT);
        add(passLbl);
        add(passField, "w 140!,wrap");

        add(addBut, "align right,w 60!,span 2,wrap");
    }

    private void settingFrame() {
        setTitle("New pass");
        setSize(300, 340);
        setLayout(new MigLayout("", "20[]20", "20[]10[]10[]10[]15[]20"));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(COLOR_BACKGROUND);
    }

    @Override
    public void onEvent(String eveName, Object obj) {
        if (EventMessage.INSERT_FORM.equals(eveName)) {
            System.out.println("Insert frame event");
        }
    }

}
