package com.eis.geoCalendar.network;

import com.eis.geoCalendar.gps.GPSPosition;

/**
 * Callback for a position retrieving
 *
 * @author Alessandra Tonin
 * @since 13/01/2020
 */
public interface PositionSourceListener {
    /**
     * Callback for position correctly retrieved
     *
     * @param userPosition The retrieved position
     */
    void onPositionRetrieved(GPSPosition userPosition);
}
