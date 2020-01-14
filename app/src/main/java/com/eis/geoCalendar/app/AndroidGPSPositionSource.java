package com.eis.geoCalendar.app;

import android.location.Location;

import com.eis.geoCalendar.gps.GPSPositionSource;
import com.eis.geoCalendar.gps.PositionSourceListener;

/**
 *
 */
public class AndroidGPSPositionSource implements GPSPositionSource {
    //setto come variabile privata, poi chiedo ad android di darmi la posizione ogni t secondi
    private PositionSourceListener positionSourceListener;
    private Location currentLocation;
    @Override
    public void setPositionSourceListener(PositionSourceListener listener, int updateTimeInMillis) {

    }

    @Override
    public void removePositionSourceListener() {

    }

    /**
     * Gets the last known position. Must be called before starting the periodic location updates.
     */
    public void getLastKnownLocation(){
        currentLocation = null;

    }


}
