package com.eis.geoCalendar.network;

import com.eis.communication.network.FailReason;
import com.eis.communication.network.SetResourceListener;
import com.eis.geoCalendar.gps.GPSPosition;

/**
 * Listener for a store request in the network.
 *
 * @param <E> Type of events handled in the network.
 * @author Luca Crema
 * @since 25/12/2019
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

    /**
     * Trims callback parameters to have GPSPosition as key transparent to the user.
     * @param key The position of the event.
     * @param value The event.
     */
    @Override
    public void onResourceSet(GPSPosition key, E value) {
        onEventStored(value);
    }

    /**
     * Trims callback parameters to have GPSPosition as key transparent to the user.
     *
     * @param key    The position of the event.
     * @param value  The event.
     * @param reason The reason why the set has failed.
     */
    @Override
    public void onResourceSetFail(GPSPosition key, E value, FailReason reason) {
        onEventStoreFail(value, reason);
    }
}
