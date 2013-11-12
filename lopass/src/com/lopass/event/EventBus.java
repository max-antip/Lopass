package com.lopass.event;


import com.lopass.gui.InsertFrameController;
import com.lopass.gui.MainFrameController;

import java.util.ArrayList;
import java.util.List;

public class EventBus {
  //todo make EventBus tests

    private static final EventBus instance = new EventBus();

    private List<EventController> eventListeners;

    private EventBus() {
        eventListeners = new ArrayList<>();
    }

    public static EventBus getInstance() {
        return instance;
    }

    public void addListener(EventController listener) {
        eventListeners.add(listener);

    }

    public boolean removeListener(EventController listener) {
        return eventListeners.remove(listener);
    }

    public void fireEvent(String eventName, Object obj) {
        if (!eventListeners.isEmpty()) {
            for (EventController ec : eventListeners) {
                ec.onEvent(eventName, obj);
            }
        }
    }

}
