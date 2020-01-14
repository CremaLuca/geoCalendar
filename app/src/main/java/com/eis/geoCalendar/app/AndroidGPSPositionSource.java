package com.eis.geoCalendar.app;

import android.os.Looper;

import com.eis.geoCalendar.gps.GPSPositionSource;
import com.eis.geoCalendar.gps.PositionSourceListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;


/**
 * This class get
 * @author Alessandra Tonin
 * @since 14/01/2020
 */

//TODO: remember this class need android.permission.ACCESS_FINE_LOCATION (or ACCESS_COARSE_LOCATION, or both)


public class AndroidGPSPositionSource implements GPSPositionSource {
    private PositionSourceListener positionSourceListener;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;


    @Override
    public void setPositionSourceListener(PositionSourceListener listener, int updateTimeInMillis) {
        positionSourceListener = listener;

    }

    @Override
    public void removePositionSourceListener() {

    }

    /**
     * Method to connect to location services and make a location request.
     */
    public void createLocationRequest() {
        //creates location request and sets its parameters
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(CurrentPositionEventsRetriever.updateTime);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Requests a location update. Before this, you should call createLocationRequest()
     */
    public void requestLocationUpdate() {
        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback(), Looper.getMainLooper());
    }

}



