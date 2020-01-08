package com.eis.geoCalendar.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eis.communication.network.FailReason;
import com.eis.geoCalendar.gps.GPSPosition;

import java.util.ArrayList;

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
     * @param storeListener {@link SetEventListener#onEventStored(NetworkEvent)} is be called if the event is correctly stored,
     *                      {@link SetEventListener#onEventStoreFail(NetworkEvent, com.eis.communication.network.FailReason)} otherwise
     */
    void storeEvent(final @NonNull E event, final @Nullable SetEventListener<E> storeListener);

    /**
     * Gets an ArrayList of events of a given position.
     *
     * @param requestedPosition The position to look for events.
     * @param getListener       {@link GetEventListener#onGetEvents(GPSPosition, ArrayList)} is be called if the search has been completed,
     *                          {@link GetEventListener#onGetEventsFailed(GPSPosition, FailReason)} otherwise
     * @param radius            The radius of the research in meters. Must be reasonably small.
     */
    void getEvents(final @NonNull GPSPosition requestedPosition, final @NonNull GetEventListener<E> getListener, final double radius);

    /**
     * Removes an event from the network.
     *
     * @param event          The event to remove.
     * @param removeListener {@link RemoveEventListener#onEventRemoved(NetworkEvent)} is be called if the event is correctly removed,
     *                       {@link RemoveEventListener#onEventNotRemoved(NetworkEvent, FailReason)} otherwise.
     */
    void removeEvent(final @NonNull E event, final @Nullable RemoveEventListener<E> removeListener);
}
