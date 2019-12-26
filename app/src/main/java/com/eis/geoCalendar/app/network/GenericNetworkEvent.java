package com.eis.geoCalendar.app.network;

import androidx.annotation.NonNull;

import com.eis.geoCalendar.app.GenericEvent;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.NetworkEvent;
import com.eis.geoCalendar.network.NetworkUser;

/**
 * @param <T> The type of the content of the event.
 * @param <U> The type of user handled by the network.
 * @author Luca Crema
 * @since 25/12/2019
 */
public class GenericNetworkEvent<T, U extends NetworkUser> extends GenericEvent<T> implements NetworkEvent<T, U> {

    protected U owner;

    /**
     * Main constructor, which acquires all details of the just created event.
     *
     * @param position The position of the event.
     * @param content  The content of the event.
     * @param owner    The owner of the event.
     */
    public GenericNetworkEvent(@NonNull GPSPosition position, @NonNull T content, @NonNull U owner) {
        super(position, content);
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
