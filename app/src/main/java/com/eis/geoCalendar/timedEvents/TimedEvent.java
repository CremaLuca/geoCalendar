package com.eis.geoCalendar.timedEvents;

import com.eis.geoCalendar.events.Event;

import java.time.LocalDateTime;

/**
 * Represents an event with a timeout or a precise time.
 *
 * @param <T> Type of details for the event.
 * @author Luca Crema
 * @version 1.1
 * @see com.eis.geoCalendar.events.Event
 * @since 21/12/2019
 */
public interface TimedEvent<T> extends Event<T> {

    /**
     * @return The time of the event.
     */
    LocalDateTime getTime();
}
