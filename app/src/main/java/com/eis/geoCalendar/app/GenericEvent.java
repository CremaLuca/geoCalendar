package com.eis.geoCalendar.app;


import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.gps.GPSPosition;

/**
 * Events' implementation.
 *
 * @param <T> Type of event.
 * @author Francesco Bau'
 * @author Luca Crema
 * @version 1.0
 * @see com.eis.geoCalendar.events.EventManager
 * @since 24/12/2019
 */
public class GenericEvent<T> implements Event<T> {

    protected GPSPosition position;
    protected T content;

    /**
     * Main constructor, which acquires all details of the just created event.
     *
     * @param position The position of the event.
     * @param content  The content of the event.
     */
    public GenericEvent(GPSPosition position, T content) {
        this.position = position;
        this.content = content;
    }

    /**
     * @return the content of the event.
     */
    @Override
    public T getContent() {
        return content;
    }

    /**
     * @return the position of the event.
     */
    @Override
    public GPSPosition getPosition() {
        return position;
    }
}
