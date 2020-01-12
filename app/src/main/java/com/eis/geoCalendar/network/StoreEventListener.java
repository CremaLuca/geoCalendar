package com.eis.geoCalendar.network;

import com.eis.communication.network.FailReason;

import androidx.annotation.NonNull;

/**
 * Callback for a store request.
 *
 * @param <E> Type of events handled in the network.
 * @author Luca Crema
 * @since 28/12/2019
 */
public interface StoreEventListener<E extends NetworkEvent> {

    /**
     * Callback for event correctly stored in the network.
     *
     * @param event The stored event.
     */
    void onEventStored(@NonNull E event);

    /**
     * Callback for event store failed.
     *
     * @param event  The event that failed the store.
     * @param reason The reason why the store has failed.
     */
    void onEventStoreFail(@NonNull E event, @NonNull FailReason reason);
}
