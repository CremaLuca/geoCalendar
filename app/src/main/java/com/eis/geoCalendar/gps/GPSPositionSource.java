package com.eis.geoCalendar.gps;

/**
 * //TODO.
 *
 * @author Luca Crema
 * @since 13/01/2020
 */
public interface GPSPositionSource {
    /**
     * Sets a {@link PositionSourceListener} and starts retrieving position data.
     *
     * @param listener The listener to set
     * @param updateTimeInMillis Time interval between each update
     */
    void setPositionSourceListener(PositionSourceListener listener, int updateTimeInMillis);

    /**
     * Removes the current {@link PositionSourceListener} if it is set.
     */
    void removePositionSourceListener();

}
