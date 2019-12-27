package com.eis.geoCalendar.app.network;

import com.eis.communication.network.FailReason;
import com.eis.communication.network.SetResourceListener;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.EventNetwork;
import com.eis.geoCalendar.network.EventNetworkManager;
import com.eis.geoCalendar.network.GetEventListener;
import com.eis.geoCalendar.network.NetworkEvent;
import com.eis.geoCalendar.network.NetworkEventUser;
import com.eis.geoCalendar.network.SetEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * @param <E> The type of network events handled.
 * @author Luca Crema
 * @since 25/12/2019
 */
public class GenericEventNetwork<E extends NetworkEvent, U extends NetworkEventUser> implements EventNetwork<E> {

    /**
     * Decimals to use when approximating a position when calculating its "network position"
     */
    protected static final int GPS_DECIMAL_APPROX_POSITIONS = 3;

    private EventNetworkManager<E, U> networkManager;

    public GenericEventNetwork(EventNetworkManager<E, U> networkManager) {
        this.networkManager = networkManager;
    }

    /**
     * Store an event in the network
     *
     * @param event         The event to store.
     * @param storeListener {@link SetEventListener#onEventStored(E)} will be called if the event is correctly stored,
     *                      {@link SetEventListener#onEventStoreFail(E, com.eis.communication.network.FailReason)} otherwise
     */
    @Override
    public void storeEvent(@NonNull final E event, @NonNull final SetEventListener<E> storeListener) {
        //This method has to get the current list of events for the position, adds the event and then sets the old list with the new event.
        //Known problem of this method: If the event list gets updated between our get and our set the update in the middle will be discarded.

        networkManager.getResource(approximateGPSPosition(event.getPosition()), new GetEventListener<E>() {
            @Override
            public void onGetResource(GPSPosition requestedPosition, ArrayList<E> alreadyPresentEvents) {
                alreadyPresentEvents.add(event);
                networkManager.setResource(approximateGPSPosition(event.getPosition()), alreadyPresentEvents, new SetResourceListener<GPSPosition, ArrayList<E>, FailReason>() {
                    //This listener is used to convert from array of events to a single event. The user doesn't need to handle the ArrayList of events, he just needs
                    // to make sure the event he added is now stored correctly or it's not.
                    @Override
                    public void onResourceSet(GPSPosition key, ArrayList<E> value) {
                        storeListener.onResourceSet(key, event);
                    }

                    @Override
                    public void onResourceSetFail(GPSPosition key, ArrayList<E> value, FailReason reason) {
                        storeListener.onResourceSetFail(key, event, reason);
                    }
                });
            }

            @Override
            public void onGetResourceFailed(GPSPosition requestedPosition, FailReason reason) {
                storeListener.onEventStoreFail(event, FailReason.GENERIC_FAIL);
            }
        });

    }

    /**
     * Gets an ArrayList of events of a given position.
     *
     * @param requestedPosition The position to look for events.
     * @param getListener       {@link GetEventListener#onGetResource(GPSPosition, ArrayList)} will be called if the search has been completed,
     *                          {@link GetEventListener#onGetResourceFailed(GPSPosition, FailReason)} otherwise
     * @param radius            The radius of the research in meters. Must be reasonably small.
     */
    @Override
    public void getEvents(@NonNull GPSPosition requestedPosition, @NonNull GetEventListener<E> getListener, double radius) {
        ArrayList<GPSPosition> gpsPositions = getPositionsInRadius(requestedPosition, radius);

        networkManager.getResource(approximateGPSPosition(requestedPosition), getListener);
    }

    /**
     * Calculates the positions to look in when looking for events in a given area.
     *
     * @param requestedPosition The start position.
     * @param radius            The radius of the research.
     * @return An array of positions around the requestedPosition. Must contain at least one position.
     */
    protected ArrayList<GPSPosition> getPositionsInRadius(GPSPosition requestedPosition, double radius) {
        //TODO : This is way more complex than it looks, I need a lot of time to think about a good algorithm for this method.
        return null;
    }

    /**
     * Approximates a GPSPosition to the third decimal for the latitude and for the longitude
     *
     * @param position The position to approximate
     * @return The approximated GPSPosition
     */
    protected GPSPosition approximateGPSPosition(GPSPosition position) {
        double latitude = approximate(position.getLatitude());
        double longitude = approximate(position.getLongitude());
        return new GPSPosition(latitude, longitude);
    }

    /**
     * Approximates the value of a float to the third decimal
     *
     * @param value The value to approximate
     * @return The approximated value
     */
    private double approximate(double value) {
        int multiplier = 10 ^ GPS_DECIMAL_APPROX_POSITIONS;
        double halfValue = 1 / (2 * (10 ^ GPS_DECIMAL_APPROX_POSITIONS - 1)); //Used to approximate 0.6 to 1 and not floor it to 0

        //return ((int) ((value + 0.005f) * 1000)) / 1000;
        return ((int) ((value + halfValue) * multiplier)) / multiplier;
    }
}
