package com.lopass.test.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddByClickTest {

    private JFrame mainFrame;
    private JPanel mainPanel;
    private JLabel textLbl, disappearLbl;
    private JTextField addField;
    private JButton addButton;

    public void makeScene() {

        mainFrame = new JFrame("Add comp test");
        mainFrame.setSize(new Dimension(400, 200));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        textLbl = new JLabel("here will be a textField");
        disappearLbl = new JLabel("Test");
        addField = new JTextField(10);

        addButton = new JButton("Add new Component");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.remove(disappearLbl);
                mainPanel.add(addField,1);
                mainFrame.repaint();
                mainFrame.revalidate();
            }
        });

        mainFrame.add(mainPanel);
        mainPanel.add(textLbl);
        mainPanel.add(disappearLbl);
        mainPanel.add(addButton);

        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        AddByClickTest test = new AddByClickTest();
        test.makeScene();
    }


}
