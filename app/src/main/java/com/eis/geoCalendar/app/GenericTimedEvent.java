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
public class GenericTimedEvent<T> extends GenericEvent<T> implements TimedEvent<T> {

    protected DateTime time;

    /**
     * Main constructor, which acquires all details of the just created event.
     *
     * @param position The position of the event.
     * @param content  The content of the event.
     * @param time     The time/deadline of the event.
     */
    public GenericTimedEvent(GPSPosition position, T content, DateTime time) {
        super(position, content);
        this.time = time;
    }

    /**
     * @return the time/deadline of the event.
     */

    @Override
    public DateTime getTime() {
        return time;
    }

}
