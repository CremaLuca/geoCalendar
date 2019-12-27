package com.eis.geoCalendar.network;

import com.eis.communication.Peer;
import com.eis.communication.network.NetworkUser;

/**
 * Represents a user that has joined the network and is able to read and add events.
 *
 * @param <P> The Peer type.
 * @author Luca Crema
 * @since 25/12/2019
 */
public interface NetworkEventUser<P extends Peer> extends NetworkUser<P> {

    /**
     * @return The user's username.
     */
    String getUsername();

}
