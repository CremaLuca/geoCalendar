package com.eis.geoCalendar.app.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eis.communication.Peer;
import com.eis.communication.network.FailReason;
import com.eis.communication.network.GetResourceListener;
import com.eis.communication.network.RemoveResourceListener;
import com.eis.communication.network.SetResourceListener;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.EventNetwork;
import com.eis.geoCalendar.network.EventNetworkManager;
import com.eis.geoCalendar.network.GetEventListener;
import com.eis.geoCalendar.network.NetworkEvent;
import com.eis.geoCalendar.network.RemoveEventListener;
import com.eis.geoCalendar.network.SetEventListener;

import java.util.ArrayList;

/**
 * Restrictions: this class only uses {@link FailReason} to operate with the network.
 *
 * @param <E> The type of network events handled.
 * @param <P> The type of addresses used by the network.
 * @author Luca Crema
 * @since 25/12/2019
 */
public class GenericEventNetwork<E extends NetworkEvent, P extends Peer> implements EventNetwork<E> {

    /**
     * Decimals to use when approximating a position when calculating its "network position"
     */
    protected static final int GPS_DECIMAL_APPROX_POSITIONS = 3;

    private EventNetworkManager<E, P> networkManager;

    public GenericEventNetwork(EventNetworkManager<E, P> networkManager) {
        this.networkManager = networkManager;
    }

    /**
     * Store an event in the network
     *
     * @param event         The event to store.
     * @param storeListener {@link SetEventListener#onEventStored(E)} will be called if the event is correctly stored,
     *                      {@link SetEventListener#onEventStoreFail(E, FailReason)} otherwise
     */
    @Override
    public void storeEvent(@NonNull final E event, @Nullable final SetEventListener<E> storeListener) {
        //This method has to get the current list of events for the position, adds the event and then sets the old list with the new event.
        //Known problem of this method: If the event list gets updated between our get and our set the update in the middle will be discarded.
        //Also specifications for inline listeners might not be the very best thing in the world, but they make the code clearer.

        networkManager.getResource(approximateGPSPosition(event.getPosition()), new GetResourceListener<GPSPosition, ArrayList<E>, FailReason>() {
            /**
             * Callback for received list of events.
             *
             * @param requestedPosition    The position where the events were searched.
             * @param alreadyPresentEvents The events located in that area.
             */
            @Override
            public void onGetResource(GPSPosition requestedPosition, ArrayList<E> alreadyPresentEvents) {
                alreadyPresentEvents.add(event);
                networkManager.setResource(approximateGPSPosition(event.getPosition()), alreadyPresentEvents, new SetResourceListener<GPSPosition, ArrayList<E>, FailReason>() {
                    //This listener is used to convert from array of events to a single event. The user doesn't need to handle the ArrayList of events, he just needs
                    // to make sure the event he added is now stored correctly or it's not.
                    @Override
                    public void onResourceSet(GPSPosition key, ArrayList<E> value) {
                        if (storeListener != null)
                            storeListener.onEventStored(event);
                    }

                    @Override
                    public void onResourceSetFail(GPSPosition key, ArrayList<E> value, FailReason reason) {
                        if (storeListener != null)
                            storeListener.onEventStoreFail(event, reason);
                    }
                });
            }

            /**
             * Callback for failed resource query.
             *
             * @param requestedPosition The position where the events were searched.
             * @param reason The reason of the failed query.
             */
            @Override
            public void onGetResourceFailed(GPSPosition requestedPosition, FailReason reason) {
                if (storeListener != null)
                    storeListener.onEventStoreFail(event, reason);
            }
        });

    }

    /**
     * Gets an ArrayList of events of a given position and radius.
     *
     * @param requestedPosition The position to look for events.
     * @param getListener       {@link GetEventListener#onGetEvents(GPSPosition, ArrayList)}  will be called if the search has been completed,
     *                          {@link GetEventListener#onGetEventsFailed(GPSPosition, FailReason)} otherwise
     * @param radius            The radius of the research in meters. Must be reasonably small: the number of queries made by this method is proportionally inverse to the
     *                          size of the area that groups events in the network.
     */
    @Override
    public void getEvents(final @NonNull GPSPosition requestedPosition, final @NonNull GetEventListener<E> getListener, final double radius) {
        //This method gets every "discrete" position in the given radius and queries the network for everyone of it,
        //then the EventsInternalListener will join the results and call the listener once every position is queried.
        ArrayList<GPSPosition> gpsPositions = getPositionsInRadius(requestedPosition, radius);
        GetEventsInternalListener eventsInternalListener = new GetEventsInternalListener(requestedPosition, gpsPositions, radius, getListener);
        for (GPSPosition position : gpsPositions) {
            networkManager.getResource(approximateGPSPosition(position), eventsInternalListener);
        }
    }

    /**
     * Removes an event from the network.
     *
     * @param event          The event to remove.
     * @param removeListener {@link RemoveEventListener#onEventRemoved(NetworkEvent)} is be called if the event is correctly removed,
     */
    @Override
    public void removeEvent(final @NonNull E event, final @Nullable RemoveEventListener<E> removeListener) {
        //This method has to get the current list of events for the position, removes one and then updates the key value.
        //If the array is empty it removes the key-value pair
        networkManager.getResource(approximateGPSPosition(event.getPosition()), new GetResourceListener<GPSPosition, ArrayList<E>, FailReason>() {
            @Override
            public void onGetResource(GPSPosition key, ArrayList<E> currentEventList) {
                currentEventList.remove(event);
                if (currentEventList.isEmpty())
                    networkManager.removeResource(key, new RemoveResourceListener<GPSPosition, FailReason>() {
                        @Override
                        public void onResourceRemoved(GPSPosition key) {
                            if (removeListener != null)
                                removeListener.onEventRemoved(event);
                        }

                        @Override
                        public void onResourceRemoveFail(GPSPosition key, FailReason reason) {
                            if (removeListener != null)
                                removeListener.onEventNotRemoved(event, reason);
                        }
                    });
                else
                    networkManager.setResource(key, currentEventList, new SetResourceListener<GPSPosition, ArrayList<E>, FailReason>() {
                        /**
                         * The resource value has been updated, we're ready to call the callback
                         */
                        @Override
                        public void onResourceSet(GPSPosition key, ArrayList<E> value) {
                            if (removeListener != null)
                                removeListener.onEventRemoved(event);
                        }

                        /**
                         * Fail, we couldn't update the list.
                         */
                        @Override
                        public void onResourceSetFail(GPSPosition key, ArrayList<E> value, FailReason reason) {
                            if (removeListener != null)
                                removeListener.onEventNotRemoved(event, reason);
                        }
                    });
            }

            /**
             * We can't remove a resource if we can't even get the current list of events
             */
            @Override
            public void onGetResourceFailed(GPSPosition key, FailReason reason) {
                if (removeListener != null)
                    removeListener.onEventNotRemoved(event, reason);
            }
        });
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
        ArrayList<GPSPosition> positionsInRadius = new ArrayList<>();
        positionsInRadius.add(requestedPosition);
        return positionsInRadius;
    }

    /**
     * Approximates a GPSPosition to the third decimal for the latitude and for the longitude
     *
     * @param position The position to approximate
     * @return The approximated GPSPosition
     */
    protected GPSPosition approximateGPSPosition(final GPSPosition position) {
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

        //eg. return ((int) ((value + 0.005f) * 1000)) / 1000;
        return ((int) ((value + halfValue) * multiplier)) / multiplier;
    }

    /**
     * Waits for the response for every position queried in {@link #getEvents(GPSPosition, GetEventListener, double)} and joins the results,
     * then calls the {@link GetEventListener} when every query is completed or if it has failed.
     *
     * @author Luca Crema
     * @since 28/12/2019
     */
    class GetEventsInternalListener implements GetResourceListener<GPSPosition, ArrayList<E>, FailReason> {

        private GPSPosition initialPosition;
        private ArrayList<GPSPosition> positionsQueried;
        private GetEventListener<E> listenerToCall;
        private ArrayList<E> eventsFound;
        private double requestedRadius;

        /**
         * Default constructor.
         *
         * @param initialPosition  Where the research started, it's usually the center more or less.
         * @param positionsQueried Position where the research was made.
         * @param radius           Radius of the research, used to filter events that might be outside of it because of the shape of the network area.
         * @param listenerToCall   The listener to be called once every position has been queried.
         */
        public GetEventsInternalListener(final @NonNull GPSPosition initialPosition, final @NonNull ArrayList<GPSPosition> positionsQueried, double radius, final @NonNull GetEventListener<E> listenerToCall) {
            this.initialPosition = initialPosition;
            this.positionsQueried = positionsQueried;
            this.listenerToCall = listenerToCall;
            this.eventsFound = new ArrayList<>();
            this.requestedRadius = radius;
        }

        /**
         * Callback for correct resource retrieval. Must be called once for every position.
         *
         * @param key          The resource key.
         * @param eventsInArea The event list found.
         */
        @Override
        public void onGetResource(@NonNull GPSPosition key, @NonNull ArrayList<E> eventsInArea) {
            positionsQueried.remove(key);
            eventsFound.addAll(eventsInArea);

            if (positionsQueried.isEmpty())
                onEveryPositionQueried();
        }

        /**
         * Method called when every position has been queried so we can call the listener that requested the events.
         */
        private void onEveryPositionQueried() {
            //Remove the events outside of the radius: if our area is 1 km and we look for events in 500mt we query one single area and then remove the events outside of 500mt.
            for (E event : eventsFound) {
                if (event.getPosition().getDistance(initialPosition) > requestedRadius)
                    eventsFound.remove(event);
            }
            listenerToCall.onGetEvents(initialPosition, eventsFound);
        }


        /**
         * Callback for failed resource retrieval.
         *
         * @param key    The resource key.
         * @param reason The reason of the failed retrieval.
         */
        @Override
        public void onGetResourceFailed(GPSPosition key, FailReason reason) {
            if (listenerToCall != null) {
                listenerToCall.onGetEventsFailed(initialPosition, reason);
            }
            listenerToCall = null;  // So that it won't be called anymore if any other of the requests fails.
        }
    }
}
