package com.eis.geoCalendar.network;

import com.eis.geoCalendar.events.Event;

/**
 * Listener for a store request in the network.
 *
 * @param <E> Type of events handled in the network.
 * @author Luca Crema
 * @since 25/12/2019
 */
public interface StoreEventListener<E extends Event> {

    /**
     * Callback for event correctly stored in the network.
     *
     * @param event The stored event.
     */
    void onEventStored(E event);

    /**
     * Callback for event store failed.
     *
     * @param event The event that failed the store.
     */
    void onEventStoreFail(E event /*, Reason reason ? */);

}
