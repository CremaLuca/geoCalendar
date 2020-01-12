package com.eis.geoCalendar.database;

import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.events.EventParser;

public class JSONEventParser<E extends Event> implements EventParser<E, String> {
    private Class<E> eventType;
    public JSONEventParser(Class<E> eventType){
        this.eventType = eventType;
    }

    /**
     * Method to convert an {@link E} type {@link Event} to a String.
     *
     * @param eventToParse The {@code Event} to convert.
     * @return The {@code D} type converted {@code Event}.
     * @throws IllegalArgumentException If the {@code Event} could not be converted.
     */
    @Override
    public String eventToData(E eventToParse) throws IllegalArgumentException {
        return null;
    }

    /**
     * Method to convert a String into an {@link E} type Event
     *
     * @param dataToParse
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public E dataToEvent(String dataToParse) throws IllegalArgumentException {
        return null;
    }

    @Override
    public boolean isEventParsable(E event) {
        return false;
    }

    @Override
    public boolean isDataParsable(String data) {
        return false;
    }
}
