package com.eis.geoCalendar.app;

import com.eis.geoCalendar.events.EventDatabase;
import com.eis.geoCalendar.timedEvents.DateTime;
import com.eis.geoCalendar.timedEvents.TimedEvent;
import com.eis.geoCalendar.timedEvents.TimedEventManager;

import java.util.ArrayList;

/**
 * This class manages all scheduled events.
 *
 * @param <E> The generic timed event.
 * @author Francesco Bau' helped by Luca Crema.
 * @version 1.0
 * @see com.eis.geoCalendar.app.GenericEventManager
 * @see com.eis.geoCalendar.timedEvents.TimedEvent
 * @see com.eis.geoCalendar.timedEvents.TimedEventManager
 * @since 23/12/2019
 */
public class GenericTimedEventManager<E extends TimedEvent> extends GenericEventManager<E> implements TimedEventManager<E> {

    /**
     * Main constructor, needed to acquire the database of the events.
     *
     * @param database The database of the events.
     */
    public GenericTimedEventManager(EventDatabase<E> database) {
        super(database);
    }

    /**
     * Searches for all events that have a time earlier than the parameter time
     *
     * @param time The time upper bound.
     * @return An {@link ArrayList} of events with time smaller than {@code time} if there is any, an empty array otherwise.
     */
    @Override
    public ArrayList<E> getEventsBeforeTime(DateTime time) {
        ArrayList<E> eventsList = new ArrayList<>();
        for (E event : database.getSavedEvents()) {
            if (event.getTime().compareTo(time) < 0)
                eventsList.add(event);
        }
        return eventsList;
    }

    /**
     * Searches for all events that have a time earlier than the parameter time
     *
     * @param time The time lower bound.
     * @return An {@link ArrayList} of events with time greater than {@code time} if there is any, an empty array otherwise.
     */
    @Override
    public ArrayList<E> getEventsAfterTime(DateTime time) {
        ArrayList<E> eventsList = new ArrayList<>();
        for (E event : database.getSavedEvents()) {
            if (event.getTime().compareTo(time) > 0)
                eventsList.add(event);
        }
        return eventsList;
    }

    /**
     * Searches for all events that have a time in between given times.
     *
     * @param beginTime The time lower bound.
     * @param endTime   The time upper bound.
     * @return An {@link ArrayList} of events with time between {@code beginTime} and {@code endTime} if there is any, an empty array otherwise.
     */
    @Override
    public ArrayList<E> getEventsBetweenTime(DateTime beginTime, DateTime endTime) {
        ArrayList<E> eventsList = new ArrayList<>();
        for (E event : database.getSavedEvents()) {
            if (event.getTime().compareTo(beginTime) > 0 && event.getTime().compareTo(endTime) < 0)
                eventsList.add(event);
        }
        return eventsList;
    }

}
