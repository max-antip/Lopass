package com.lopass.gui;


import com.lopass.event.EventBus;
import com.lopass.event.EventController;
import com.lopass.event.EventMessage;
import com.lopass.file.StorageService;
import com.lopass.gui.icons.IconLabel;
import com.lopass.gui.icons.Icons;
import com.lopass.main.Record;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.plaf.ActionMapUIResource;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.lopass.main.Record.LoginPass;

public class MainFrame extends JFrame implements EventController {

    private static final String LOPASS_TITLE = "LoPass";

    private static final Color MAIN_BG = Color.WHITE;
    private static final Color SUB_PANEL_BG = new Color(220, 240, 221);
    private static final Color CONTENT_PANEL_BG = new Color(238, 240, 240);
    private static final Color LIGHT_BG = new Color(223, 254, 209);
    private static final int MAIN_WIDTH = 480;
    private static final int MAIN_HEIGHT = 510;

    private static final Font FONT_TITLE = new Font("Arial", Font.PLAIN, 16);
    private static final Font FONT_SUB_TITLE = new Font("Arial", Font.BOLD, 11);
    private static final Color INSERT_PANEL_COlOR = new Color(237, 151, 126);
    public static final boolean RESIZABLE = true;

    JScrollPane scrollPane;
    JPanel contentPanel;
    JButton addBut;
    JLabel statusLbl;
    private static EventBus eventBus = EventBus.getInstance();
    private Map<String, JPanel> recordMap;


    MainFrameController controller;

    public MainFrame() {
        super(LOPASS_TITLE);
        eventBus.addListener(this);
        controller = new MainFrameController();
        recordMap = new HashMap<>();
        initFrame();

        contentPanel = new JPanel(new MigLayout("", "[grow,fill]", ""));
        contentPanel.setBackground(CONTENT_PANEL_BG);
        //todo лэйаут для панели с паролями

        addBut = new JButton(Icons.getMiddleIcon(Icons.ADD));
        addBut.setToolTipText("Add new record (Alt+A)");

        InputMap keyMap = new ComponentInputMap(addBut);
        keyMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.ALT_MASK), "action");

        ActionMap actionMap = new ActionMapUIResource();
        actionMap.put("action", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openInsertPassFrame();
            }
        });

        SwingUtilities.replaceUIActionMap(addBut, actionMap);
        SwingUtilities.replaceUIInputMap(addBut, JComponent.WHEN_IN_FOCUSED_WINDOW, keyMap);
        addBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openInsertPassFrame();
            }
        });

        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        statusLbl = new JLabel("");
        statusLbl.setFont(FONT_SUB_TITLE);
        add(scrollPane, "span 2,wrap");
        add(addBut, "w 40!,h 40!, align left,span 2,split 2");
        add(statusLbl, "align right,gapleft 50");

    }

    private void initFrame() {
        getContentPane().setBackground(MAIN_BG);
        setPreferredSize(new Dimension(MAIN_WIDTH, MAIN_HEIGHT));
        setLayout(new MigLayout("", "20[grow,fill][]20", "20[grow,fill][][]20"));
        setResizable(RESIZABLE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void openInsertPassFrame() {
        InsertPassFrame frame = new InsertPassFrame();
        frame.setVisible(true);
        frame.pack();
    }


    private void addToPanel(Record record) {
        if (record == null) return;

        JPanel subContentPanel = null;

        if (!hasRecord(record)) {
            subContentPanel = new JPanel(
                    new MigLayout("insets 30", "[]5[]1[][grow,fill]1[]", "5[]5"));

            subContentPanel.setBackground(SUB_PANEL_BG);


            recordMap.put(record.getTitle(), subContentPanel);
            contentPanel.add(subContentPanel, "wrap");

            final JPanel titlePanel = createTitlePanel(subContentPanel, record.getTitle());
            JLabel titleLbl = new JLabel(record.getTitle());

            titleLbl.setFont(FONT_TITLE);
            titlePanel.add(titleLbl);

            subContentPanel.add(titlePanel, " span 5,h 40!,wrap");

        } else {
            subContentPanel = getRecordPanel(record.getTitle());
        }
        for (LoginPass lp : record.getLoginPassList()) {
            addToPanel(subContentPanel, lp);
        }

        repaint();
        revalidate();
    }

    private void addToPanel(LoginPass logPass) {
        if (logPass == null) return;

        JPanel subContentPanel = getRecordPanel(logPass.getParentTitle());

        if (subContentPanel == null) return;

        addToPanel(subContentPanel, logPass);

        repaint();
        revalidate();

    }

    private void addToPanel(JPanel subContentPanel, LoginPass lp) {
        JLabel subTitleLbl = new JLabel(lp.getSubTitle());
        subTitleLbl.setFont(FONT_SUB_TITLE);

        final IconLabel loginLbl = new IconLabel(lp.getLogin(),
                Icons.getVerySmallIcon(Icons.LOGIN));
        loginLbl.addMouseListener(new CopyToBufferAdapter(loginLbl.getText()));

        IconLabel passLbl = new IconLabel(lp.getPass(),
                Icons.getVerySmallIcon(Icons.PASS));
        passLbl.addMouseListener(new CopyToBufferAdapter(passLbl.getText()));

//        JLabel loginLbl = new JLabel(lp.getLogin());
//        JLabel passLbl = new JLabel(lp.getPass());

//        JLabel loginIcon = new JLabel();
//        loginIcon.setIcon(Icons.getVerySmallIcon(Icons.LOGIN));
//
//        JLabel passIcon = new JLabel();
//        passIcon.setIcon(Icons.getVerySmallIcon(Icons.PASS));

        subContentPanel.add(subTitleLbl, "w 97!,gapleft 10");

        subContentPanel.add(loginLbl);
        subContentPanel.add(passLbl, "wrap");

//        subContentPanel.add(loginIcon);
//        subContentPanel.add(loginLbl, "gapleft 4");
//        subContentPanel.add(passIcon);
//        subContentPanel.add(passLbl, "gapleft 4,wrap");
    }

    private JPanel getRecordPanel(String key) {
        if (key == null) throw new NullPointerException("Key must be not null");
        return recordMap.get(key);
    }

    private boolean hasRecord(Record record) {
        Set<String> keys = recordMap.keySet();

        for (String key : keys) {
            if (key.equals(record.getTitle())) {
                return true;
            }
        }
        return false;
    }

    private JPanel createTitlePanel(final JPanel subContentPanel,
                                    final String parentTile) {
        final JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(LIGHT_BG);
        titlePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                titlePanel.setBackground(MAIN_BG);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                titlePanel.setBackground(LIGHT_BG);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !newRecordIsAdding) {
                    createTextFields(parentTile, subContentPanel);
                    repaint();
                    revalidate();
                }
            }
        });
        return titlePanel;
    }

    private boolean newRecordIsAdding;

    private JPanel createTextFields(final String parentTitle, final JPanel pane) {

//        pane.setBackground(INSERT_PANEL_COlOR);
        final JTextField subtitleFld = new JTextField(13);
        final JTextField loginFld = new JTextField(13);
        final JTextField passFld = new JTextField(13);
        final JButton submitBut = new JButton(Icons.getVerySmallIcon(Icons.ADD));
        final JButton backBtn = new JButton(Icons.getVerySmallIcon(Icons.BACK));
        newRecordIsAdding = true;
        submitBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.addLoginPass(parentTitle,
                        subtitleFld.getText(),
                        loginFld.getText(),
                        passFld.getText());
                newRecordIsAdding = false;
                pane.remove(subtitleFld);
                pane.remove(loginFld);
                pane.remove(passFld);
                pane.remove(submitBut);
                pane.remove(backBtn);

                repaint();
                revalidate();
            }

        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newRecordIsAdding = false;
                pane.remove(subtitleFld);
                pane.remove(loginFld);
                pane.remove(passFld);
                pane.remove(submitBut);
                pane.remove(backBtn);

                repaint();
                revalidate();
            }
        });
        pane.add(subtitleFld, "w 60!,gapleft 10");
        pane.add(loginFld, "w 60!");
        pane.add(passFld, "w 60!,gapleft 7,split 2");
        pane.add(submitBut, "span 3,split 2, w 22!, h 22!,align left");
        pane.add(backBtn, "w 22!, h 22!,align left");


        return pane;
    }

    private void clearAll() {
        contentPanel.removeAll();
        repaint();
        revalidate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onEvent(String eveName, Object obj) {
        switch (eveName) {
            case EventMessage.ADD_NEW_RECORD:
                if (obj instanceof Record) {
                    Record rec = (Record) obj;
                    addToPanel(rec);
                }
                break;

            case EventMessage.ADD_PASS_LOGIN:
                if (obj instanceof LoginPass) {
                    LoginPass rec = (LoginPass) obj;
                    addToPanel(rec);
                }
                break;

            case EventMessage.FIRST_LOAD:
                List<Record> passList = StorageService.
                        getInstance().getAllRecords();

                for (Record record : passList) {
                    addToPanel(record);
                }
                break;
        }
    }

    private class CopyToBufferAdapter extends MouseAdapter {

        String text;

        public CopyToBufferAdapter(String text) {
            this.text = text;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            Clipboard clpbrd = Toolkit.getDefaultToolkit().
                    getSystemClipboard();

            StringSelection strSel =
                    new StringSelection(text);
            statusLbl.setText("Copied to clipboard: " + text);

            clpbrd.setContents(strSel, null);
        }
    }


}
