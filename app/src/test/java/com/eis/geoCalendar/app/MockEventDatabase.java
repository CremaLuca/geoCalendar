package com.eis.geoCalendar.app;

import androidx.annotation.NonNull;

import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.events.EventDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
/**
 * Implementation of EventDatabase to use in tests
 */
class MockEventDatabase<E extends Event> implements EventDatabase<E> {

    private ArrayList<E> events;

    MockEventDatabase() {
        events = new ArrayList<>();
    }

    @Override
    public boolean saveEvent(@NonNull E event) {
        this.events.add(event);
        return true;
    }

    @Override
    public Map<E, Boolean> saveEvents(@NonNull Collection<E> events) {
        this.events.addAll(events);
        return null;
    }

    @Override
    public boolean removeEvent(@NonNull E event) {
        return this.events.remove(event);
    }

    @Override
    public Map<E, Boolean> removeEvents(@NonNull Collection<E> events) {
        Map<E, Boolean> returnMap = new HashMap<>();
        Object[] eventsArray = events.toArray();
        for (int i = 0; i < events.size(); i++) {
            returnMap.put((E) eventsArray[i], events.remove(eventsArray[i]));
        }
        return returnMap;
    }

    @Override
    public ArrayList<E> getSavedEvents() {
        return events;
    }

    /**
     * Retrieves presence of event.
     *
     * @param event The event to find.
     * @return {@code true} if the event is parsable and present, {@code false} otherwise.
     */
    @Override
    public boolean contains(@NonNull E event) {
        return events.contains(event);
    }
}