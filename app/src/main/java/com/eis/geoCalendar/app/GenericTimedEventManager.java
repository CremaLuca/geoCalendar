package com.eis.geoCalendar.app;

import com.eis.geoCalendar.events.EventDatabase;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.timedEvents.DateTime;
import com.eis.geoCalendar.timedEvents.TimedEvent;
import com.eis.geoCalendar.timedEvents.TimedEventManager;

import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * This class manages all scheduled events.
 *
 * @param <E> The generic timed event.
 * @author Francesco Bau' helped by Luca Crema.
 * @version 1.0
 * @see com.eis.geoCalendar.events.EventManager
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
     * @param time The time upper bound.
     * @return all events scheduled before a certain time.
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
     * @param time The time lower bound.
     * @return all events scheduled after a certain time.
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
     * @param beginTime The time lower bound.
     * @param endTime   The time upper bound.
     * @return all the events in a defined time interval.
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
