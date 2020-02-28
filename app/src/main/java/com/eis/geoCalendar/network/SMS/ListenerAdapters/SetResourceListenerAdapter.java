package com.eis.geoCalendar.network.SMS.ListenerAdapters;

import androidx.annotation.NonNull;

import com.eis.communication.network.listeners.SetResourceListener;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.SMS.SMSNetworkEventUser;
import com.eis.geoCalendar.network.SMS.SMSStringEvent;
import com.eis.smsnetwork.SMSFailReason;

import java.util.ArrayList;

/**
 * Adapter class for interfaces SetResourceListener<String, String, SMSFailReason> and
 * SetResourceListener<GPSPosition, ArrayList<SMSStringEvent>, SMSFailReason>
 * <p>
 * You can consider this as a fake listeners used to access the real one
 *
 * @author Turcato
 */
public class SetResourceListenerAdapter implements SetResourceListener<String, String, SMSFailReason> {
    private SetResourceListener<GPSPosition, ArrayList<SMSStringEvent>, SMSFailReason> setResourceListener;
    private SMSNetworkEventUser myself;

    /**
     * Constructor that fully initializes the object
     *
     * @param setResourceListener An object implementing SetResourceListener<GPSPosition, ArrayList<SMSStringEvent>, SMSFailReason>
     */
    public SetResourceListenerAdapter(@NonNull SetResourceListener<GPSPosition, ArrayList<SMSStringEvent>, SMSFailReason> setResourceListener,
                                      @NonNull SMSNetworkEventUser myself) {
        this.setResourceListener = setResourceListener;
        this.myself = myself;
    }

    /**
     * Calls the "real" listener {@link SetResourceListener#onResourceSet} method
     *
     * @param key   The key of the event, must be a {@link String} obtained by {@link GPSPosition#toString()}
     * @param value Value of the resource, which is the event's description
     */
    @Override
    public void onResourceSet(String key, String value) {
        GPSPosition position = new GPSPosition(key);
        SMSStringEvent event = new SMSStringEvent(position, value, myself);
        ArrayList<SMSStringEvent> eventArrayList = new ArrayList<>();
        eventArrayList.add(event);

        setResourceListener.onResourceSet(position, eventArrayList);
    }

    /**
     * Calls the "real" listener {@link SetResourceListener#onResourceSetFail} method
     *
     * @param key    The key of the event, must be a {@link String} obtained by {@link GPSPosition#toString()}
     * @param reason The reason of the operation's fail
     */
    @Override
    public void onResourceSetFail(String key, String value, SMSFailReason reason) {
        GPSPosition position = new GPSPosition(key);
        SMSStringEvent event = new SMSStringEvent(position, value, myself);
        ArrayList<SMSStringEvent> eventArrayList = new ArrayList<>();
        eventArrayList.add(event);

        setResourceListener.onResourceSetFail(position, eventArrayList, reason);
    }
}
