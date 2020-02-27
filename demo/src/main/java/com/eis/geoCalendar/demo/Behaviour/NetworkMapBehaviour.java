package com.eis.geoCalendar.demo.Behaviour;

import androidx.annotation.NonNull;

import com.eis.geoCalendar.network.NetworkEvent;
import com.eis.geoCalendar.network.NetworkEventUser;

import java.util.List;

/**
 * Defines an interface for Class that wants to manage network events on a map
 * <p>
 * Note that this interface defines only network-related methods, a class implementing this Interface
 * must also implement {@link com.eis.geoCalendar.demo.Behaviour.MapBehaviour} and
 * {@link com.eis.geoCalendar.demo.Behaviour.MapBehaviourCallbacks}
 */
public interface NetworkMapBehaviour<E extends NetworkEvent, U extends NetworkEventUser> {

    /**
     * @param events A bunch of network events to position in the map (both description and
     *               Position must be defined), must have a content parsable to String
     * @param users  The users of the given events, must be a complete list of all the users that
     *               appear in the list of events
     */
    void addEventsToMap(@NonNull List<U> users, @NonNull List<E> events);

    /**
     * @param user   A networkUser
     * @param events A list of events from {@code user}
     */
    void addEventsToMap(@NonNull U user, @NonNull List<E> events);

    /**
     * @param allow Boolean value that indicates will of the user to be able to delete network events
     *              from the map
     */
    void allowMapRemovalNetworkEvents(boolean allow);
}
