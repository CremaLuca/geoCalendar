package com.eis.geoCalendar.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eis.communication.network.FailReason;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.networklistener.GetNetworkEventListener;
import com.eis.geoCalendar.network.networklistener.RemoveNetworkEventListener;
import com.eis.geoCalendar.network.networklistener.SetNetworkEventListener;

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
     * @param storeListener {@link SetNetworkEventListener#onEventStored(NetworkEvent)} is be called if the event is correctly stored,
     *                      {@link SetNetworkEventListener#onEventStoreFail(NetworkEvent, com.eis.communication.network.FailReason)} otherwise
     */
    void storeEvent(final @NonNull E event, final @Nullable SetNetworkEventListener<E> storeListener);

    /**
     * Gets an ArrayList of events of a given position.
     *
     * @param requestedPosition The position to look for events.
     * @param getListener       {@link GetNetworkEventListener#onGetEvents(GPSPosition, ArrayList)} is be called if the search has been completed,
     *                          {@link GetNetworkEventListener#onGetEventsFailed(GPSPosition, FailReason)} otherwise
     * @param radius            The radius of the research in meters. Must be reasonably small.
     */
    void getEvents(final @NonNull GPSPosition requestedPosition, final @NonNull GetNetworkEventListener<E> getListener, final double radius);

    /**
     * Removes an event from the network.
     *
     * @param event          The event to remove.
     * @param removeListener {@link RemoveNetworkEventListener#onEventRemoved(NetworkEvent)} is be called if the event is correctly removed,
     *                       {@link RemoveNetworkEventListener#onEventNotRemoved(NetworkEvent, FailReason)} otherwise.
     */
    void removeEvent(final @NonNull E event, final @Nullable RemoveNetworkEventListener<E> removeListener);
}
