package com.eis.geoCalendar.events;

import com.eis.geoCalendar.gps.GPSPosition;

import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * Event manager, manages events storing and their removal, also filters events.
 *
 * @param <E> Type of event handled
 * @author Luca Crema
 * @version 1.0
 * @since 21/12/2019
 */
public interface EventManager<E extends Event> {

    /**
     * Adds an event to the local event list.
     *
     * @param event The event to add.
     */
    void addEvent(@NonNull final E event);

    /**
     * Removes an event from the local event list.
     *
     * @param event The event to remove.
     * @return {@code true} if the event was present and has been removed, {@code false} otherwise.
     */
    boolean removeEvent(@NonNull final E event);

    /**
     * Searches for events in a given circle of gps positions.
     *
     * @param p     The center of the circle of search.
     * @param range The radius of the circe of search in meters.
     * @return {@link ArrayList} of events in the given area. It's empty if there is none.
     */
    ArrayList<E> getEventsInRange(@NonNull final GPSPosition p, final float range);

    /**
     * Searches for the closest event to a given {@link GPSPosition}
     *
     * @param p The position used to calculate the closest event
     * @return The closest event to the given Position if there is one, {@code null} otherwise
     */
    E getClosestEvent(@NonNull final GPSPosition p);

    /**
     * @return All the events the user has added and that hasn't been removed.
     */
    ArrayList<E> getAllEvents();
}
