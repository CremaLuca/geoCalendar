package com.eis.geoCalendar.network;

import com.eis.communication.network.FailReason;
import com.eis.geoCalendar.gps.GPSPosition;

import java.util.ArrayList;

import androidx.annotation.NonNull;

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
     * @param storeListener {@link SetEventListener#onEventStored(NetworkEvent)} will be called if the event is correctly stored,
     *                      {@link SetEventListener#onEventStoreFail(NetworkEvent, com.eis.communication.network.FailReason)} otherwise
     */
    void storeEvent(final @NonNull E event, final @NonNull SetEventListener<E> storeListener);

    /**
     * Gets an ArrayList of events of a given position.
     *
     * @param requestedPosition The position to look for events.
     * @param getListener       {@link GetEventListener#onGetEvents(GPSPosition, ArrayList)} will be called if the search has been completed,
     *                          {@link GetEventListener#onGetEventsFailed(GPSPosition, FailReason)} otherwise
     * @param radius            The radius of the research in meters. Must be reasonably small.
     */
    void getEvents(final @NonNull GPSPosition requestedPosition, final @NonNull GetEventListener<E> getListener, double radius);
}
