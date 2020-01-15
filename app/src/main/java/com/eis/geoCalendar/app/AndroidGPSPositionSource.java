package com.eis.geoCalendar.app;

import android.content.Context;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.gps.GPSPositionSource;
import com.eis.geoCalendar.gps.PositionSourceListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


/**
 * This class updates the user's position every updateTimeInMillis.
 * This class needs android.permission.ACCESS_FINE_LOCATION (or ACCESS_COARSE_LOCATION, or both).
 *
 * @author Alessandra Tonin
 * @since 14/01/2020
 */
public class AndroidGPSPositionSource extends LocationCallback implements GPSPositionSource {
    //private int updateTimeInMillis;
    private PositionSourceListener positionSourceListener;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private Context currentContext;

    /**
     * Constructor. Creates a location request to get the most accurate locations available.
     */
    public AndroidGPSPositionSource(Context appContext) {
        this.currentContext = appContext;
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(currentContext);
        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult result) {
                //check this
                positionSourceListener.onPositionRetrieved(new GPSPosition(result.getLocations().get(0)));
            }
        };
        this.locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Sets a {@link PositionSourceListener} and requests location updates.
     *
     * @param listener           The listener to set
     * @param updateTimeInMillis Time interval between each update
     */
    @Override
    public void setPositionSourceListener(@NonNull PositionSourceListener listener, @NonNull int updateTimeInMillis) {
        this.positionSourceListener = listener;
        //this.updateTimeInMillis = updateTimeInMillis;
        locationRequest.setInterval(updateTimeInMillis);
        requestLocationUpdate();
    }

    /**
     * Removes the current {@link PositionSourceListener} and stops location updates.
     */
    @Override
    public void removePositionSourceListener() {
        stopLocationUpdates();
    }

    /**
     * Requests a location update. Before this, you should create a location request (constructor).
     */
    private void requestLocationUpdate() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    /**
     * Stops requesting location updates.
     */
    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

}



