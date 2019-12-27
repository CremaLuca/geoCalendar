package com.eis.geoCalendar.network;

import com.eis.geoCalendar.events.Event;

/**
 * Event that can be stored or retrieved from the network.
 *
 * @param <T> The type of content for the event.
 * @param <U> The type of user of the network.
 * @author Luca Crema
 * @since 25/12/2019
 */
public interface NetworkEvent<T, U extends NetworkEventUser> extends Event<T> {

    /**
     * @return The user that stored this event in the network.
     */
    U getOwner();

}
