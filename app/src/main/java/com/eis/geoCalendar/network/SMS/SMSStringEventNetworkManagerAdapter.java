package com.eis.geoCalendar.network.SMS;

import android.content.Context;

import androidx.annotation.NonNull;

import com.eis.communication.network.NetworkManager;
import com.eis.communication.network.listeners.GetResourceListener;
import com.eis.communication.network.listeners.InviteListener;
import com.eis.communication.network.listeners.RemoveResourceListener;
import com.eis.communication.network.listeners.SetResourceListener;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.SMS.ListenerAdapters.GetResourceListenerAdapter;
import com.eis.geoCalendar.network.SMS.ListenerAdapters.RemoveResourceListenerAdapter;
import com.eis.geoCalendar.network.SMS.ListenerAdapters.SetResourceListenerAdapter;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.SMSFailReason;
import com.eis.smsnetwork.SMSNetworkManager;

import java.util.ArrayList;

/**
 * Simple network manager Adapter for {@link SMSStringEvent}s that uses {@link com.eis.smsnetwork.SMSNetworkManager}
 * as real network manager. Uses Singleton Design pattern since {@link com.eis.smsnetwork.SMSNetworkManager} is a singleton
 * <p>
 * This class was created because of conflicts with the defined interfaces and the available network dictionary's interfaces
 *
 * @author Turcato
 */
public class SMSStringEventNetworkManagerAdapter implements NetworkManager<GPSPosition, ArrayList<SMSStringEvent>, SMSPeer, SMSFailReason> {
    private static SMSStringEventNetworkManagerAdapter instance;

    private SMSNetworkManager smsNetworkManager;
    private SMSNetworkEventUser myself;

    /**
     * Private constructor since this class uses Singleton DP
     *
     * @param appContext The application's context
     * @param myself     The user of the current Device
     */
    private SMSStringEventNetworkManagerAdapter(@NonNull Context appContext, @NonNull SMSNetworkEventUser myself) {
        smsNetworkManager = new SMSNetworkManager();
        smsNetworkManager.setup(appContext.getApplicationContext());
    }

    /**
     * @param appContext The application's context
     * @param myself     The user of the current Device
     * @return The only instance the device is allowed to access
     */
    public static SMSStringEventNetworkManagerAdapter getInstance(@NonNull Context appContext, @NonNull SMSNetworkEventUser myself) {
        if (instance == null)
            return instance = new SMSStringEventNetworkManagerAdapter(appContext, myself);
        else
            return instance;
    }

    /**
     * @param key                 The key of the Resource (event) to add
     * @param value               A list of resources (events), identified by the given key, to add
     * @param setResourceListener A listener that waits for the resource to be added
     */
    @Override
    public void setResource(GPSPosition key, ArrayList<SMSStringEvent> value, SetResourceListener<GPSPosition, ArrayList<SMSStringEvent>, SMSFailReason> setResourceListener) {
        SetResourceListener<String, String, SMSFailReason> adapterSetResourceListener = new SetResourceListenerAdapter(setResourceListener, myself);

        for (SMSStringEvent event : value) {
            smsNetworkManager.setResource(key.toString(), event.getContent(), adapterSetResourceListener);
        }

    }

    /**
     * @param key                 The key of the Resource (event) to retrieve
     * @param getResourceListener A listener that waits for the resource to be retrieved
     */
    @Override
    public void getResource(GPSPosition key, GetResourceListener<GPSPosition, ArrayList<SMSStringEvent>, SMSFailReason> getResourceListener) {
        GetResourceListener<String, String, SMSFailReason> listener = new GetResourceListenerAdapter(getResourceListener);
        smsNetworkManager.getResource(key.toString(), listener);
    }

    /**
     * @param key                    The key of the Resource (event) to remove
     * @param removeResourceListener A listener that waits for the removal of the resource
     */
    @Override
    public void removeResource(GPSPosition key, RemoveResourceListener<GPSPosition, SMSFailReason> removeResourceListener) {
        RemoveResourceListener<String, SMSFailReason> listener = new RemoveResourceListenerAdapter(removeResourceListener);
        smsNetworkManager.removeResource(key.toString(), listener);
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
