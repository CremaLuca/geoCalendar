package com.eis.geoCalendar.network;

import com.eis.communication.network.FailReason;
import com.eis.communication.network.GetResourceListener;
import com.eis.geoCalendar.gps.GPSPosition;

import java.util.ArrayList;

/**
 * Listener for get requests from the network.
 *
 * @param <E> The type of Event handled in the network.
 * @author Luca Crema
 * @since 25/12/2019
 */
public abstract class GetEventListener<E extends NetworkEvent> implements GetResourceListener<GPSPosition, ArrayList<E>, FailReason> {

    /**
     * Callback for successful event research.
     *
     * @param requestedPosition The position where the research was made.
     * @param events            An {@link ArrayList} of events, it's empty if there are none.
     */
    @Override
    public abstract void onGetResource(GPSPosition requestedPosition, ArrayList<E> events);

    /**
     * Callback for failed event research
     *
     * @param requestedPosition The position where the research was made
     * @param reason The reason for the failed resource retrieval.
     */
    @Override
    public abstract void onGetResourceFailed(GPSPosition requestedPosition, FailReason reason);
}
