package com.eis.geoCalendar.demo.Resources;

import com.eis.geoCalendar.network.NetworkEventUser;
import com.eis.smslibrary.SMSPeer;

public class SMSNetworkEventUser implements NetworkEventUser<SMSPeer> {
    private SMSPeer myPeer;

    public SMSNetworkEventUser(SMSPeer myPeer) {
        this.myPeer = myPeer;
    }

    @Override
    public String getUsername() {
        return myPeer.getAddress();
    }

    @Override
    public SMSPeer getPeer() {
        return myPeer;
    }
}
