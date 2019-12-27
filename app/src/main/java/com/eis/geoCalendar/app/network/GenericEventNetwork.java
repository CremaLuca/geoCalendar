package com.eis.geoCalendar.app.network;

import androidx.annotation.NonNull;

import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.EventNetwork;
import com.eis.geoCalendar.network.GetEventListener;
import com.eis.geoCalendar.network.NetworkEvent;
import com.eis.geoCalendar.network.StoreEventListener;

import java.util.ArrayList;

/**
 * @param <E> The type of network events handled.
 * @author Luca Crema
 * @since 25/12/2019
 */
public class GenericEventNetwork<E extends NetworkEvent> implements EventNetwork<E> {

    /**
     * Decimals to use when approximating a position when calculating its "network position"
     */
    protected static final int GPS_DECIMAL_APPROX_POSITIONS = 3;
    private static final int NUMBER_OF_DECIMALS = 3;

    //NetworkHandler<GPSPosition, E> networkHandler;

    public GenericEventNetwork(/* NetworkDictionary/Handler<GPSPosition, E> networkHandler*/) {
        //TODO : Class to implement once we'll have a common network library
        //this.networkHandler = networkHandler;
    }


    /**
     * Store an event in the network
     *
     * @param event         The event to store.
     * @param storeListener {@link StoreEventListener#onEventStored(E)} will be called if the event is correctly stored,
     *                      {@link StoreEventListener#onEventStoreFail(E)} otherwise
     */
    @Override
    public void storeEvent(@NonNull E event, @NonNull StoreEventListener<E> storeListener) {
        //networkHandler.store(,event, storeListener);
    }

    /**
     * Gets an ArrayList of events of a given position.
     *
     * @param requestedPosition The position to look for events.
     * @param getListener       {@link GetEventListener#onGetEvents(GPSPosition, ArrayList)} will be called if the search has been completed,
     *                          {@link GetEventListener#onGetEventFailed(GPSPosition)} otherwise
     * @param radius            The radius of the research in meters. Must be reasonably small.
     */
    @Override
    public void getEvents(@NonNull GPSPosition requestedPosition, @NonNull GetEventListener<E> getListener, double radius) {
        //networkHandler.getFromKey(requestedPosition
    }

    /**
     * Calculates the positions to look in when looking for events in a given area.
     *
     * @param requestedPosition The start position.
     * @param radius            The radius of the research.
     * @return An array of positions around the requestedPosition. Must contain at least one position.
     */
    protected ArrayList<GPSPosition> getPositionsInRadius(GPSPosition requestedPosition, double radius) {
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
        int multiplier = 10 ^ NUMBER_OF_DECIMALS;
        double halfValue = 1 / (2 * (10 ^ NUMBER_OF_DECIMALS - 1)); //Used to approximate 0.6 to 1 and not floor it to 0

        //return ((int) ((value + 0.005f) * 1000)) / 1000;
        return ((int) ((value + halfValue) * multiplier)) / multiplier;
    }
}
