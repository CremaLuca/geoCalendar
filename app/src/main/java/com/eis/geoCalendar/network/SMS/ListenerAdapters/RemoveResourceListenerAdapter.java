package com.eis.geoCalendar.network.SMS.ListenerAdapters;

import com.eis.communication.network.listeners.RemoveResourceListener;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.smsnetwork.SMSFailReason;

/**
 * Adapter class for interfaces RemoveResourceListener<String, SMSFailReason>> and
 * RemoveResourceListener<GPSPosition, SMSFailReason> removeResourceListener
 * <p>
 * You can consider this as a fake listeners used to access the real one
 *
 * @author Turcato
 */
public class RemoveResourceListenerAdapter implements RemoveResourceListener<String, SMSFailReason> {
    private RemoveResourceListener<GPSPosition, SMSFailReason> removeResourceListener;

    /**
     * Constructor that fully initializes the object
     *
     * @param removeResourceListener An object implementing RemoveResourceListener<GPSPosition, SMSFailReason>
     */
    public RemoveResourceListenerAdapter(RemoveResourceListener<GPSPosition, SMSFailReason> removeResourceListener) {
        this.removeResourceListener = removeResourceListener;
    }

    /**
     * Calls the "real" listener {@link RemoveResourceListener#onResourceRemoved} method
     *
     * @param key The Resource's key (the resource is an event in this case)
     */
    @Override
    public void onResourceRemoved(String key) {
        GPSPosition position = new GPSPosition(key);
        removeResourceListener.onResourceRemoved(position);
    }

    /**
     * Calls the "real" listener {@link RemoveResourceListener#onResourceRemoveFail} method
     *
     * @param key    The Resource's key (the resource is an event in this case)
     * @param reason The reason of the operation's fail
     */
    @Override
    public void onResourceRemoveFail(String key, SMSFailReason reason) {
        GPSPosition position = new GPSPosition(key);
        removeResourceListener.onResourceRemoveFail(position, reason);
    }
}
