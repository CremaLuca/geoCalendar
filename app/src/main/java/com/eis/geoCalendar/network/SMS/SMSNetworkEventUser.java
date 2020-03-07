package com.eis.geoCalendar.network.SMS;

import com.eis.geoCalendar.network.NetworkEventUser;
import com.eis.smslibrary.SMSPeer;

/**
 * Simple class implementing the NetworkEventUser interface, that represents a user of an SMS Network
 * @author Turcato
 */
public class SMSNetworkEventUser implements NetworkEventUser<SMSPeer> {
    private SMSPeer myPeer;

    /**
     * Public constructor that creates a fully operative SMSNetworkEventUser object
     *
     * @param myPeer A valid {@link SMSPeer}
     */
    public SMSNetworkEventUser(SMSPeer myPeer) {
        this.myPeer = myPeer;
    }

    /**
     * @return The username of this user (equals to the peer's Address)
     */
    @Override
    public String getUsername() {
        return myPeer.getAddress();
    }

    /**
     * @return The peer that is represented by this user
     */
    @Override
    public SMSPeer getPeer() {
        return myPeer;
    }
}
