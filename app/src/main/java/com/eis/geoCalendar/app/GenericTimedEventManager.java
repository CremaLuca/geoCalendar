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
public class GenericTimedEventManager<E extends TimedEvent> implements TimedEventManager<E> {

    private EventDatabase<E> database;

    /**
     * Main constructor, needed to acquire the database of the events.
     *
     * @param database The database of the events.
     */
    public GenericTimedEventManager(EventDatabase<E> database) {
        this.database = database;
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

    /**
     * Adds an event.
     * @param event The event to add.
     */
    @Override
    public void addEvent(@NonNull E event) {
        database.saveEvent(event);
    }

    /**
     * Removes an event.
     * @param event The event to remove.
     * @return true if event is removed, false otherwise.
     */
    @Override
    public boolean removeEvent(@NonNull E event) {
        return database.removeEvent(event);
    }

    /**
     * @param p     The center of the circle of search.
     * @param range The radius of the circe of search in meters.
     * @return all events in a certain range.
     */
    @Override
    public ArrayList<E> getEventsInRange(@NonNull GPSPosition p, float range) {
        ArrayList<E> eventsList = new ArrayList<>();
        for(E event:database.getSavedEvents()){
            if(event.getPosition().getDistance(p)<range)
                eventsList.add(event);
        }
        return eventsList;
    }

    /**
     * @param p The position used to calculate the closest event.
     * @return the closest event.
     */
    @Override
    public E getClosestEvent(@NonNull GPSPosition p) {
        E relativeClosestEvent = database.getSavedEvents().get(0);
        for(E event:database.getSavedEvents()){
            if(event.getPosition().getDistance(p)<relativeClosestEvent.getPosition().getDistance(p))
                relativeClosestEvent = event;
        }
        return relativeClosestEvent;
    }

    /**
     * @return all the events.
     */
    @Override
    public ArrayList<E> getAllEvents() {
        return database.getSavedEvents();
    }
}
