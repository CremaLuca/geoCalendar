package com.eis.geoCalendar.network;

import com.eis.communication.network.FailReason;
import com.eis.communication.network.SetResourceListener;
import com.eis.geoCalendar.gps.GPSPosition;

/**
 * Listener for a store request in the network.
 *
 * @param <E> Type of events handled in the network.
 * @author Luca Crema
 * @since 28/12/2019
 */
public abstract class SetEventListener<E extends NetworkEvent> implements SetResourceListener<GPSPosition, E, FailReason> {

    /**
     * Callback for event correctly stored in the network.
     *
     * @param event The stored event.
     */
    public abstract void onEventStored(E event);

    /**
     * Callback for event store failed.
     *
     * @param event The event that failed the store.
     * @param reason The reason why the store has failed.
     */
    public abstract void onEventStoreFail(E event, FailReason reason);
}
