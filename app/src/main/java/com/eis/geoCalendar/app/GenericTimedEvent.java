package com.eis.geoCalendar.app;

import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.timedEvents.DateTime;
import com.eis.geoCalendar.timedEvents.TimedEvent;

/**
 * Manager of timed events' implementation.
 *
 * @param <T> Type of event.
 * @author Francesco Bau' helped by Luca Crema.
 * @version 1.0
 * @see com.eis.geoCalendar.events.EventManager
 * @since 23/12/2019
 */
public class GenericTimedEvent<T> implements TimedEvent<T> {

    private GPSPosition position;
    private T content;
    private DateTime time;

    /**
     * Main constructor, which acquires all details of the just created event.
     *
     * @param position The position of the event.
     * @param content  The content of the event.
     * @param time     The time/deadline of the event.
     */
    public GenericTimedEvent(GPSPosition position, T content, DateTime time) {
        this.position = position;
        this.content = content;
        this.time = time;
    }

    /**
     * @return the time/deadline of the event.
     */
    @Override
    public DateTime getTime() {
        return time;
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
