package com.eis.geoCalendar.network;

import com.eis.communication.network.FailReason;
import com.eis.geoCalendar.gps.GPSPosition;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Used to store and get events in/from the network.
 *
 * @param <E> Type of events handled by this network.
 * @author Luca Crema
 * @since 25/12/2019
 */
public interface EventNetwork<E extends NetworkEvent> {

    /**
     * Store an event in the network
     *
     * @param event         The event to store.
     * @param storeListener {@link NetStoreEventListener#onEventStored(NetworkEvent)} is be called if the event is correctly stored,
     *                      {@link NetStoreEventListener#onEventStoreFail(NetworkEvent, com.eis.communication.network.FailReason)} otherwise
     */
    void storeEvent(final @NonNull E event, final @Nullable NetStoreEventListener<E> storeListener);

    /**
     * Gets an ArrayList of events of a given position.
     *
     * @param requestedPosition The position to look for events.
     * @param getListener       {@link NetGetEventListener#onGetEvents(GPSPosition, ArrayList)} is be called if the search has been completed,
     *                          {@link NetGetEventListener#onGetEventsFailed(GPSPosition, FailReason)} otherwise
     * @param radius            The radius of the research in meters. Must be reasonably small.
     */
    void getEvents(final @NonNull GPSPosition requestedPosition, final double radius, final @NonNull NetGetEventListener<E> getListener);

    /**
     * Removes an event from the network.
     *
     * @param event          The event to remove.
     * @param removeListener {@link NetRemoveEventListener#onEventRemoved(NetworkEvent)} is be called if the event is correctly removed,
     *                       {@link NetRemoveEventListener#onEventNotRemoved(NetworkEvent, FailReason)} otherwise.
     */
    void removeEvent(final @NonNull E event, final @Nullable NetRemoveEventListener<E> removeListener);
}
