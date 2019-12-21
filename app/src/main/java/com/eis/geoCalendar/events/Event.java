package com.eis.geoCalendar.events;

import com.eis.geoCalendar.gps.GPSPosition;

/**
 * Represents some details or preferences of a geo point.
 *
 * @param <T> Type of details.
 * @author Luca Crema
 * @version 1.0
 * @since 21/12/2019
 */
public interface Event<T> {
    /**
     * @return The details of this point.
     */
    T getContent();

    /**
     * @return The position of this point.
     */
    GPSPosition getPosition();
}
