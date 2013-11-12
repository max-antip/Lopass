package com.lopass.gui;

import com.lopass.event.EventBus;
import com.lopass.event.EventMessage;
import com.lopass.file.StorageService;
import com.lopass.main.Record;

public class InsertFrameController {

    private static EventBus eventBus = EventBus.getInstance();

    public void addRecord(String title, String subTitleField, String login, String pass) {
        Record.LoginPass item = new Record.LoginPass(subTitleField, login, pass);
        Record record = new Record();
        record.setTitle(title);
        record.addLoginPass(item);
        if(StorageService.getInstance().addRecord(record)){
            eventBus.fireEvent(EventMessage.ADD_NEW_RECORD, record);
        }
    }
}
