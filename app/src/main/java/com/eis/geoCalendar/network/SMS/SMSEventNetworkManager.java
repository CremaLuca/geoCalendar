package com.eis.geoCalendar.network.SMS;

import android.content.Context;

import androidx.annotation.NonNull;

import com.eis.communication.network.NetworkManager;
import com.eis.communication.network.listeners.GetResourceListener;
import com.eis.communication.network.listeners.InviteListener;
import com.eis.communication.network.listeners.RemoveResourceListener;
import com.eis.communication.network.listeners.SetResourceListener;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.EventNetworkManager;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.SMSFailReason;
import com.eis.smsnetwork.SMSNetworkManager;

import java.util.ArrayList;

/**
 * Simple network manager for {@link SMSStringEvent}s that uses {@link SMSStringEventNetworkManagerAdapter}
 * as network. Uses Singleton Design pattern since {@link SMSStringEventNetworkManagerAdapter} is a singleton
 *
 * @author Turcato
 */
public class SMSEventNetworkManager implements EventNetworkManager<SMSStringEvent, SMSPeer, SMSFailReason> {
    private static SMSEventNetworkManager instance;

    private NetworkManager<GPSPosition, ArrayList<SMSStringEvent>, SMSPeer, SMSFailReason> smsNetworkManager;

    /**
     * Private constructor since this class uses Singleton DP
     *
     * @param appContext The application's context
     * @param myUser     The user of the current Device
     */
    private SMSEventNetworkManager(@NonNull Context appContext, @NonNull SMSNetworkEventUser myUser) {
        SMSNetworkManager manager = new SMSNetworkManager();
        manager.setup(appContext.getApplicationContext());
        smsNetworkManager = SMSStringEventNetworkManagerAdapter.getInstance(appContext, myUser);
    }

    /**
     * @param appContext The application's context
     * @param myUser     The user of the current Device
     * @return The only instance the device is allowed to access
     */
    public static SMSEventNetworkManager getInstance(Context appContext, SMSNetworkEventUser myUser) {
        if (instance == null)
            return instance = new SMSEventNetworkManager(appContext, myUser);
        else
            return instance;
    }

    /**
     * @param key                    The key of the Resource (event) to remove
     * @param removeResourceListener A listener that waits for the removal of the resource
     */
    @Override
    public void removeResource(GPSPosition key, RemoveResourceListener<GPSPosition, SMSFailReason> removeResourceListener) {
        smsNetworkManager.removeResource(key, removeResourceListener);
    }

    /**
     * @param key                 The key of the Resource (event) to add
     * @param value               A list of resources (events), identified by the given key, to add
     * @param setResourceListener A listener that waits for the resource to be added
     */
    @Override
    public void setResource(GPSPosition key, ArrayList<SMSStringEvent> value, SetResourceListener<GPSPosition, ArrayList<SMSStringEvent>, SMSFailReason> setResourceListener) {
        smsNetworkManager.setResource(key, value, setResourceListener);
    }

    /**
     * @param key                 The key of the Resource (event) to retrieve
     * @param getResourceListener A listener that waits for the resource to be retrieved
     */
    @Override
    public void getResource(GPSPosition key, GetResourceListener<GPSPosition, ArrayList<SMSStringEvent>, SMSFailReason> getResourceListener) {
        smsNetworkManager.getResource(key, getResourceListener);
    }

    /**
     * @param peer           The {@link com.eis.smslibrary.SMSPeer} to invite
     * @param inviteListener A listener that waits for the response of the peer
     */
    @Override
    public void invite(SMSPeer peer, InviteListener<SMSPeer, SMSFailReason> inviteListener) {
        smsNetworkManager.invite(peer, inviteListener);
    }
}
