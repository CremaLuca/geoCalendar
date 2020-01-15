package com.eis.geoCalendar.app.network;

import com.eis.geoCalendar.network.NetworkEventUser;
import com.eis.smslibrary.SMSPeer;

/**
 * An implementation of {@link NetworkEventUser} through {@link SMSPeer}.
 *
 * @author Riccardo De Zen
 */
public class SMSNetworkEventUser implements NetworkEventUser<SMSPeer> {

    private SMSPeer peer;
    private String username;

    /**
     * The default Constructor, taking an {@link SMSPeer} and a username.
     *
     * @param peer     The {@link SMSPeer} associated to this User.
     * @param username The {@link String} username for this User.
     */
    public SMSNetworkEventUser(SMSPeer peer, String username) {
        this.peer = peer;
        this.username = username;
    }

    /**
     * @return The user's peer address ({@link SMSNetworkEventUser#peer}).
     */
    @Override
    public SMSPeer getPeer() {
        return peer;
    }

    /**
     * @return The user's username ({@link SMSNetworkEventUser#username}).
     */
    @Override
    public String getUsername() {
        return username;
    }
}
