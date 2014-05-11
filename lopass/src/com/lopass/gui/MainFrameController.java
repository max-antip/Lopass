package com.lopass.gui;


import com.lopass.crypto.Coder;
import com.lopass.event.EventBus;
import com.lopass.event.EventMessage;
import com.lopass.file.StorageService;
import com.lopass.main.Record;

public class MainFrameController {

    Coder coder;
    private static final StorageService service = StorageService.getInstance();
    private static EventBus eventBus = EventBus.getInstance();

    public MainFrameController() {

    }

    public void exit() {
        StorageService.getInstance().saveBase();
        System.exit(0);
    }

    public void addRecord(String title, String subTitle, String login, String pass) {
        Record lp = new Record(subTitle, login, pass);
        lp.setTitle(title);
        if (service.addRecord(lp)) {
            eventBus.fireEvent(EventMessage.ADD_PASS_LOGIN, lp);
        }
    }

    public void removeRecord(String title) {
        if (service.removeRecord(title)) {
            eventBus.fireEvent(EventMessage.REMOVE_PASS_LOGIN, title);
        }
    }

/*
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        URL fontUrl = MainFrameController.class.getResource("icons/icons.ttf");

        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontUrl.toURI())));
}
*/

}
