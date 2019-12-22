package com.eis.geoCalendar.events;

import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * Handles memorization of events in phone memory.
 *
 * @param <E> Event type.
 * @author Luca Crema
 * @version 1.0
 * @since 21/12/2019
 */
public interface EventDatabase<E extends Event> {
    /**
     * Saves an event in memory.
     *
     * @param event The event.
     */
    void saveEvent(@NonNull final E event);

    /**
     * Removes an event from memory.
     *
     * @param event The event.
     */
    void removeEvent(@NonNull final E event);

    /**
     * Retrieves all saved events from memory.
     *
     * @return An {@link ArrayList} of saved events.
     */
    ArrayList<E> getSavedEvents();
}
