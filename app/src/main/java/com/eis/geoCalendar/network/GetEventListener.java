package com.eis.geoCalendar.network;

import androidx.annotation.NonNull;

import com.eis.geoCalendar.gps.GPSPosition;

import java.util.ArrayList;

/**
 * Listener for get requests from the network.
 *
 * @param <E> The type of Event handled in the network.
 * @author Luca Crema
 * @since 25/12/2019
 */
public interface GetEventListener<E extends NetworkEvent> {

    /**
     * Callback for successful event research.
     *
     * @param requestedPosition The position where the research was made.
     * @param events            An {@link ArrayList} of events, it's empty if there are none.
     */
    void onGetEvents(@NonNull GPSPosition requestedPosition, @NonNull ArrayList<E> events);

    /**
     * Callback for failed event research
     *
     * @param requestedPosition The position where the research was made
     */
    void onGetEventFailed(@NonNull GPSPosition requestedPosition /*, Reason failReason ?*/);

}
