package com.eis.geoCalendar.app;

import android.os.Looper;

import com.eis.geoCalendar.gps.GPSPositionSource;
import com.eis.geoCalendar.gps.PositionSourceListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;


/**
 * This class updates the user's position every updateTimeInMillis
 *
 * @author Alessandra Tonin
 * @since 14/01/2020
 */

//TODO: remember this class need android.permission.ACCESS_FINE_LOCATION (or ACCESS_COARSE_LOCATION, or both)


public class AndroidGPSPositionSource implements GPSPositionSource {
    private PositionSourceListener positionSourceListener;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    /**
     * Constructor. Creates a location request to get the most accurate locations available.
     */
    AndroidGPSPositionSource() {
        locationCallback = new LocationCallback();
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void setPositionSourceListener(PositionSourceListener listener, int updateTimeInMillis) {
        positionSourceListener = listener;
        locationRequest.setInterval(updateTimeInMillis);
        requestLocationUpdate();
    }

    @Override
    public void removePositionSourceListener() {
        stopLocationUpdates();
    }

    /**
     * Requests a location update. Before this, you should create a location request (constructor)
     */
    private void requestLocationUpdate() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    /**
     * Stops requesting location updates
     */
    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

}



