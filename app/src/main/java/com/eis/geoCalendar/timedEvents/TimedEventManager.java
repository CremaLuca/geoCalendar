package com.eis.geoCalendar.timedEvents;

import com.eis.geoCalendar.events.EventManager;

import java.util.ArrayList;

/**
 * Manager of timed events.
 *
 * @param <E> Type of TimedEvent.
 * @author Luca Crema
 * @version 1.1
 * @see com.eis.geoCalendar.events.EventManager
 * @since 21/12/2019
 */
public interface TimedEventManager<E extends TimedEvent> extends EventManager<E> {

    /**
     * Searches for all events that have a time earlier than the parameter time
     *
     * @param time The time upper bound.
     * @return An {@link ArrayList} of events with time smaller than {@code time}.
     */
    ArrayList<E> getEventsBeforeTime(DateTime time);

    /**
     * Searches for all events that have a time earlier than the parameter time
     *
     * @param time The time lower bound.
     * @return An {@link ArrayList} of events with time greater than {@code time}.
     */
    ArrayList<E> getEventsAfterTime(DateTime time);

    /**
     * Searches for all events that have a time in between given times.
     *
     * @param beginTime The time lower bound.
     * @param endTime   The time upper bound.
     * @return An {@link ArrayList} of events with time between {@code beginTime} and {@code endTime}.
     */
    ArrayList<E> getEventsBetweenTime(DateTime beginTime, DateTime endTime);
}
