package com.eis.geoCalendar.network;

import androidx.annotation.NonNull;

import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.gps.GPSPosition;

import java.util.ArrayList;

/**
 * Used to store and get events in/from the network.
 *
 * @param <E> Events handled by this network.
 * @author Luca Crema
 * @since 25/12/2019
 */
public interface EventNetwork<E extends Event> {

    /**
     * Store an event in the network
     *
     * @param event         The event to store.
     * @param storeListener {@link StoreEventListener#onEventStored(Event)} will be called if the event is correctly stored,
     *                      {@link StoreEventListener#onEventStoreFail(Event)} otherwise
     */
    void storeEvent(final @NonNull E event, final @NonNull StoreEventListener<E> storeListener);

    /**
     * Gets an ArrayList of events of a given position.
     *
     * @param requestedPosition The position to look for events.
     * @param getListener       {@link GetEventListener#onGetEvents(GPSPosition, ArrayList)} will be called if the search has been completed,
     *                          {@link GetEventListener#onGetEventFailed(GPSPosition)} otherwise
     * @param radius            The radius of the research in meters. Must be reasonably small.
     */
    void getEvents(final @NonNull GPSPosition requestedPosition, final @NonNull GetEventListener<E> getListener, float radius);
}
