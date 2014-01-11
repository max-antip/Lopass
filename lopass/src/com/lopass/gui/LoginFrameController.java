package com.lopass.gui;


import com.lopass.event.EventBus;
import com.lopass.event.EventMessage;
import com.lopass.file.StorageService;

public class LoginFrameController {

    private static EventBus eventBus = EventBus.getInstance();
    private StorageService service;

    public void submit(String login, char[] pass) {
        StorageService.init(login.trim(), pass);
        StorageService storageService = StorageService.getInstance();

        boolean loaded = storageService.loadDB();
        if (!loaded) {
            eventBus.fireEvent(EventMessage.AUTHORIZE_WRONG, "Wrong pass");
            return;
        }
        openMainFrame();

        eventBus.fireEvent(EventMessage.FIRST_LOAD, "Base loaded");
    }

    private void openMainFrame() {
        MainFrame mf = new MainFrame();
        mf.setVisible(true);
        mf.pack();
    }

}
