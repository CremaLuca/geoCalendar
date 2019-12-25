package com.eis.geoCalendar.network;

import com.eis.communication.Peer;

/**
 * Represents a user that has joined the network and is able to read and add events.
 *
 * @param <P> The Peer type.
 * @author Luca Crema
 * @since 25/12/2019
 */
public interface NetworkUser<P extends Peer> {

    /**
     * @return The user's peer address.
     */
    P getPeer();

    /**
     * @return The user's username.
     */
    String getUsername();

}
