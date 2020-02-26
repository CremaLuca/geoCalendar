package com.eis.geoCalendar.network.SMS.ListenerAdapters;

import androidx.annotation.NonNull;

import com.eis.communication.network.listeners.GetResourceListener;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.SMS.SMSStringEvent;
import com.eis.smsnetwork.SMSFailReason;

import java.util.ArrayList;

/**
 * Adapter class for interfaces GetResourceListener<String, String, SMSFailReason> and
 * GetResourceListener<GPSPosition, ArrayList<SMSStringEvent>, SMSFailReason>
 * <p>
 * You can consider this as a fake listeners used to access the real one
 *
 * @author Turcato
 */
public class GetResourceListenerAdapter implements GetResourceListener<String, String, SMSFailReason> {
    private GetResourceListener<GPSPosition, ArrayList<SMSStringEvent>, SMSFailReason> getResourceListener;

    /**
     * Constructor that fully initializes the object
     *
     * @param getResourceListener An object implementing GetResourceListener<GPSPosition, ArrayList<SMSStringEvent>, SMSFailReason>
     */
    public GetResourceListenerAdapter(@NonNull GetResourceListener<GPSPosition, ArrayList<SMSStringEvent>, SMSFailReason> getResourceListener) {
        this.getResourceListener = getResourceListener;
    }

    /**
     * Calls the "real" listener {@link GetResourceListener#onGetResource} method
     *
     * @param key   The key of the event, must be a {@link String} obtained by {@link GPSPosition#toString()}
     * @param value Value of the resource, which is the event's description
     */
    @Override
    public void onGetResource(String key, String value) {
        GPSPosition position = new GPSPosition(key);
        //The interface GetResourceListener doesn't retrieve the SMSPeer, can't access that
        SMSStringEvent event = new SMSStringEvent(position, value, null);
        ArrayList<SMSStringEvent> eventArrayList = new ArrayList<>();
        eventArrayList.add(event);

        getResourceListener.onGetResource(position, eventArrayList);
    }

    /**
     * Calls the "real" listener {@link GetResourceListener#onGetResourceFailed} method
     *
     * @param key    The key of the event, must be a {@link String} obtained by {@link GPSPosition#toString()}
     * @param reason The reason of the operation's fail
     */
    @Override
    public void onGetResourceFailed(String key, SMSFailReason reason) {
        GPSPosition position = new GPSPosition(key);
        getResourceListener.onGetResourceFailed(position, reason);
    }
}
