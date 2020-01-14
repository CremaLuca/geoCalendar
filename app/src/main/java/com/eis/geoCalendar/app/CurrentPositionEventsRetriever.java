package com.eis.geoCalendar.app;

import com.eis.geoCalendar.events.AsyncEventManager;
import com.eis.geoCalendar.events.GetEventListener;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.gps.GPSPositionSource;
import com.eis.geoCalendar.gps.PositionSourceListener;
import com.eis.geoCalendar.network.NetworkEvent;

/**
 * This class handles the retrieving of the events near to the user's position
 *
 * @author Tonin Alessandra
 * @author Luca Crema
 * @since 14/01/2020
 */
public class CurrentPositionEventsRetriever<E extends NetworkEvent> {
    private GPSPositionSource positionSource;
    private AsyncEventManager networkEventManager;
    private GetEventListener getEventListener;
    private float precision;
    static int updateTime = 5000; //update time in millis

    /**
     * Constructor
     *
     * @param eventManager The event manager
     * @param listener     Callback for event retrieval
     * @param precision    The precision used to retrieve events
     */
    CurrentPositionEventsRetriever(AsyncEventManager<E> eventManager, GetEventListener<E> listener, float precision) {
        positionSource = new AndroidGPSPositionSource();
        this.precision = precision;
        this.networkEventManager = eventManager;
        getEventListener = listener;
    }

    /**
     * Overrides current position source
     *
     * @param positionSource The new position source
     */
    public void setPositionSource(GPSPositionSource positionSource) {
        this.positionSource = positionSource;
    }

    /**
     * Overrides current update time
     *
     * @param updateTime The new update time
     */
    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Starts retrieving events
     */
    void startEventRetrieval() {
        positionSource.setPositionSourceListener(new PositionSourceListener() {
            @Override
            public void onPositionRetrieved(GPSPosition userPosition) {
                networkEventManager.getEventsInRange(userPosition, precision, getEventListener);
            }
        }, updateTime);
    }


    /**
     * Stops retrieving events
     */
    void stopEventRetrieval() {
        positionSource.removePositionSourceListener();
    }


}
