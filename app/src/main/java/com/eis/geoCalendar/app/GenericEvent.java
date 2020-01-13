package com.eis.geoCalendar.app;


import androidx.annotation.Nullable;

import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.gps.GPSPosition;

/**
 * Events' implementation.
 *
 * @param <T> Type of event.
 * @author Francesco Bau'
 * @author Luca Crema
 * @version 1.0
 * @see com.eis.geoCalendar.events.Event
 * @since 24/12/2019
 */
public class GenericEvent<T> implements Event<T> {

    protected GPSPosition position;
    protected T content;

    /**
     * Main constructor, which acquires all details of the just created event.
     *
     * @param position The position of the event.
     * @param content  The content of the event.
     */
    public GenericEvent(GPSPosition position, T content) {
        this.position = position;
        this.content = content;
    }

    /**
     * @return the content of the event.
     */
    @Override
    public T getContent() {
        return content;
    }

    /**
     * @return the position of the event.
     */
    @Override
    public GPSPosition getPosition() {
        return position;
    }

    /**
     * Method to check whether two instances of {@link GenericEvent} are equal or not.
     * @param obj The other Object to compare.
     * @return {@code true} if the two objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if(!(obj instanceof GenericEvent))
            return false;
        GenericEvent other = (GenericEvent) obj;
        return this.position.equals(other.position) && this.content.equals(other.content);
    }
}
