package com.eis.geoCalendar.app;

import androidx.annotation.NonNull;

import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.events.EventDatabase;
import com.eis.geoCalendar.events.EventManager;
import com.eis.geoCalendar.gps.GPSPosition;

import java.util.ArrayList;

/**
 * This class manages all scheduled events.
 *
 * @param <E> The generic timed event.
 * @author Francesco Bau'
 * @version 1.0
 * @see com.eis.geoCalendar.events.EventManager
 * @since 24/12/2019
 */
public class GenericEventManager<E extends Event> implements EventManager<E> {

    protected EventDatabase<E> database;

    /**
     * Main constructor, needed to acquire the database of the events.
     *
     * @param database The database of the events.
     */
    public GenericEventManager(EventDatabase<E> database) {
        this.database = database;
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
