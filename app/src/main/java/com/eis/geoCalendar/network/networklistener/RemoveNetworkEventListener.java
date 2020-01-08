package com.eis.geoCalendar.network.networklistener;

import androidx.annotation.NonNull;

import com.eis.communication.network.FailReason;
import com.eis.geoCalendar.network.NetworkEvent;

/**
 * Callback for a remove event request.
 *
 * @author Luca Crema
 * @since 08/01/2020
 */
public interface RemoveNetworkEventListener<E extends NetworkEvent> {

    /**
     * Callback for event correctly removed from the network.
     *
     * @param event The stored event.
     */
    void onEventRemoved(@NonNull E event);

    /**
     * Callback for event remove failed.
     *
     * @param event  The event that failed the be removed.
     * @param reason The reason why the remove has failed.
     */
    void onEventNotRemoved(@NonNull E event, @NonNull FailReason reason);

}
