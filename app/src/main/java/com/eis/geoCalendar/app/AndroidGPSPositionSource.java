package com.eis.geoCalendar.app;

import android.content.Context;
import android.os.Looper;

import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.gps.GPSPositionSource;
import com.eis.geoCalendar.gps.PositionSourceListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import androidx.annotation.NonNull;


/**
 * Source for current phone's GPS position.
 * android.permission.ACCESS_FINE_LOCATION (or ACCESS_COARSE_LOCATION, or both) permission is needed in order to work properly.
 *
 * @author Alessandra Tonin
 * @author Luca Crema
 * @since 14/01/2020
 */
public class AndroidGPSPositionSource extends LocationCallback implements GPSPositionSource {

    private PositionSourceListener positionSourceListener;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    /**
     * Constructor. Creates a location request to get the most accurate locations available.
     */
    public AndroidGPSPositionSource(Context appContext) {
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(appContext);
        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult result) {
                positionSourceListener.onPositionRetrieved(new GPSPosition(result.getLastLocation()));
            }
        };
        this.locationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Sets a {@link PositionSourceListener} and starts requesting location updates.
     * Parameter's {@link PositionSourceListener#onPositionRetrieved(GPSPosition)} will be called every {@code updateTimeInMillis} seconds.
     *
     * @param listener           The listener to subscribe to be called upon update.
     * @param updateTimeInMillis Time interval between each update
     */
    @Override
    public void setPositionSourceListener(@NonNull final PositionSourceListener listener, final int updateTimeInMillis) {
        this.positionSourceListener = listener;
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
     * Subscribes the PositionSourceListener to be called upon location update.
     * N.B.: this method will fail if a PositionSourceListener wasn't set, since LocationCallback
     * will be calling positionSourceListener.onPositionRetrieved on a null positionSourceListener
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





