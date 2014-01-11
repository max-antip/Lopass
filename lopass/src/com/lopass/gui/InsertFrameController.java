package com.lopass.gui;

import com.lopass.event.EventBus;
import com.lopass.event.EventMessage;
import com.lopass.file.StorageService;
import com.lopass.main.Record;

public class InsertFrameController {

    private static EventBus eventBus = EventBus.getInstance();

    public void addRecord(String title, String login, String pass) {
        Record record = new Record(title, login, pass);
        if (StorageService.getInstance().addRecord(record)) {
            eventBus.fireEvent(EventMessage.ADD_NEW_RECORD, record);
        }
    }
}
