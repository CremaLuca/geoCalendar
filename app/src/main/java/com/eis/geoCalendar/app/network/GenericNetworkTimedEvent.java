package com.eis.geoCalendar.app.network;

import com.eis.geoCalendar.app.GenericTimedEvent;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.NetworkEventUser;
import com.eis.geoCalendar.timedEvents.DateTime;
import com.eis.geoCalendar.timedEvents.network.NetworkTimedEvent;

import androidx.annotation.NonNull;

/**
 * I tested the implementation to see which methods were given to implement and seems correct
 *
 * @param <T> The type of the content of the timedEvent.
 * @param <U> The type of user handled by the network.
 * @author Luca Crema
 * @since 25/12/2019
 */
public class GenericNetworkTimedEvent<T, U extends NetworkEventUser> extends GenericTimedEvent<T> implements NetworkTimedEvent<T, U> {

    protected U owner;

    /**
     * Main constructor, which acquires all details of the just created event.
     *
     * @param position The position of the event.
     * @param content  The content of the event.
     * @param owner    The owner of the event.
     */
    public GenericNetworkTimedEvent(@NonNull GPSPosition position, @NonNull T content, DateTime time, @NonNull U owner) {
        super(position, content, time);
        this.owner = owner;
    }

    /**
     * @return The user that stored this event in the network.
     */
    @Override
    public U getOwner() {
        return owner;
    }
}
