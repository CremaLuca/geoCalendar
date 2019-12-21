package com.eis.geoCalendar.events;

import com.eis.geoCalendar.events.Event;

import java.util.ArrayList;

public interface EventMemory<E extends Event> {
    void saveEvent(E e);
    void removeEvent(E e);
    ArrayList<E> getSavedEvents();
}
