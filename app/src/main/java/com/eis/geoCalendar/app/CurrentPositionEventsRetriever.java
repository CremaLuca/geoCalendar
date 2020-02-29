package com.eis.geoCalendar.app;

import android.content.Context;

import androidx.annotation.NonNull;

import com.eis.geoCalendar.events.AsyncEventManager;
import com.eis.geoCalendar.events.GetEventListener;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.gps.GPSPositionSource;
import com.eis.geoCalendar.gps.PositionSourceListener;
import com.eis.geoCalendar.network.NetworkEvent;
import com.google.android.gms.location.LocationServices;

/**
 * This class handles the retrieving of the events near to the user's position.
 * Before calling the listener with updates, method StartEventRetrieval() must be called.
 *
 * @author Tonin Alessandra
 * @author Luca Crema
 * @since 14/01/2020
 */
public class CurrentPositionEventsRetriever<E extends NetworkEvent> {
    private GPSPositionSource positionSource;
    private AsyncEventManager<E> networkEventManager;
    private GetEventListener getEventListener;
    private float precision;
    private int updateTime = 5000; //update time in millis

    /**
     * Constructor.
     *
     * @param eventManager The event manager
     * @param listener     Callback for event retrieval
     * @param precision    The precision used to retrieve events
     * @param context      The current application context
     */
    public CurrentPositionEventsRetriever(@NonNull AsyncEventManager<E> eventManager, @NonNull GetEventListener<E> listener, @NonNull float precision, @NonNull Context context) {
        this.positionSource = new AndroidGPSPositionSource(context);
        this.precision = precision;
        this.networkEventManager = eventManager;
        this.getEventListener = listener;
    }

    /**
     * Overrides current position source.
     *
     * @param positionSource The new position source
     */
    public void setPositionSource(@NonNull GPSPositionSource positionSource) {
        this.positionSource = positionSource;
    }

    /**
     * Overrides current update time.
     * This change will be effective at the next startEventRetrieval() call.
     *
     * @param updateTime The new update time
     */
    public void setUpdateTime(@NonNull int updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Starts retrieving events.
     */
    public void startEventRetrieval() {
        positionSource.setPositionSourceListener(new PositionSourceListener() {
            @Override
            public void onPositionRetrieved(GPSPosition userPosition) {
                networkEventManager.getEventsInRange(userPosition, precision, getEventListener);
            }
        }, updateTime);
    }


    /**
     * Removes the positionSourceListener.
     */
    public void stopEventRetrieval() {
        positionSource.removePositionSourceListener();
    }


}
