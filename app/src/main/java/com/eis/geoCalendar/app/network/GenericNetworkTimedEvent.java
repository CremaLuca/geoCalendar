package com.eis.geoCalendar.app.network;

import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.NetworkUser;
import com.eis.geoCalendar.timedEvents.DateTime;
import com.eis.geoCalendar.timedEvents.network.NetworkTimedEvent;

/**
 * I tested the implementation to see which methods were given to implement and seems correct
 *
 * @param <T>
 * @param <U>
 */
public class GenericNetworkTimedEvent<T, U extends NetworkUser> implements NetworkTimedEvent<T, U> {
    @Override
    public U getUser() {
        return null;
    }

    @Override
    public DateTime getTime() {
        return null;
    }

    @Override
    public T getContent() {
        return null;
    }

    @Override
    public GPSPosition getPosition() {
        return null;
    }
}
