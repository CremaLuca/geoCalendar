package com.eis.geoCalendar.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eis.geoCalendar.gps.GPSPosition;

import java.util.ArrayList;

/**
 * Event manager for network events.
 *
 * @author Luca Crema
 * @since 08/01/2020
 */
public interface AsyncEventManager<E extends Event> {

    /**
     * Searches for events in a given circle of gps positions.
     *
     * @param p     The center of the circle of search.
     * @param range The radius of the circe of search in meters.
     * @return {@link ArrayList} of events in the given area. It's empty if there is none.
     */
    void getEventsInRange(@NonNull final GPSPosition p, final float range, GetEventListener<E> getEventListener);

    /**
     * Adds an event to the local event list.
     *
     * @param event The event to add.
     */
    void addEvent(@NonNull final E event, @Nullable final AddEventListener<E> addEventListener);

    /**
     * Removes an event from the local event list.
     *
     * @param event The event to remove.
     * @return {@code true} if the event was present and has been removed, {@code false} otherwise.
     */
    void removeEvent(@NonNull final E event, @Nullable final RemoveEventListener<E> removeEventListener);

}
