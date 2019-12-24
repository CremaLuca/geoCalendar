package com.eis.geoCalendar.app;

import androidx.annotation.NonNull;

import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.events.EventDatabase;
import com.eis.geoCalendar.events.EventManager;
import com.eis.geoCalendar.gps.GPSPosition;

import java.util.ArrayList;

/**
 * This class manages all generic events.
 *
 * @param <E> The generic timed event.
 * @author Francesco Bau'
 * @version 1.0
 * @see com.eis.geoCalendar.events.Event
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
     * Adds an event to the local event list.
     *
     * @param event The event to add.
     */
    @Override
    public void addEvent(@NonNull E event) {
        database.saveEvent(event);
    }

    /**
     * Removes an event from the local event list.
     *
     * @param event The event to remove.
     * @return {@code true} if the event was present and has been removed, {@code false} otherwise.
     */
    @Override
    public boolean removeEvent(@NonNull E event) {
        return database.removeEvent(event);
    }

    /**
     * Searches for events in a given circle of gps positions.
     *
     * @param p     The center of the circle of search.
     * @param range The radius of the circe of search in meters.
     * @return {@link ArrayList} of events in the given area. It's empty if there is none.
     */
    @Override
    public ArrayList<E> getEventsInRange(@NonNull GPSPosition p, float range) {
        ArrayList<E> eventsList = new ArrayList<>();
        for (E event : database.getSavedEvents()) {
            if (event.getPosition().getDistance(p) < range)
                eventsList.add(event);
        }
        return eventsList;
    }

    /**
     * Searches for the closest event to a given {@link GPSPosition}
     *
     * @param p The position used to calculate the closest event
     * @return The closest event to the given Position if there is one, {@code null} otherwise
     */
    @Override
    public E getClosestEvent(@NonNull GPSPosition p) {
        if (database.getSavedEvents().isEmpty()) return null;
        E relativeClosestEvent = database.getSavedEvents().get(0);
        for (E event : database.getSavedEvents()) {
            if (event.getPosition().getDistance(p) < relativeClosestEvent.getPosition().getDistance(p))
                relativeClosestEvent = event;
        }
        return relativeClosestEvent;
    }

    /**
     * @return All the events the user has added and that hasn't been removed yet.
     */
    @Override
    public ArrayList<E> getAllEvents() {
        return database.getSavedEvents();
    }
}
