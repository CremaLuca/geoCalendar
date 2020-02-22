package com.eis.geoCalendar.demo.Localization;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.eis.geoCalendar.demo.Behaviour.LocationRetriever;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

/**
 * @author Turcato
 * <p>
 * NOTE: this is here temporarely until something else is ready
 * NOTE: change log.d to the proper output if this goes to production
 */
public class LocationManager implements LocationRetriever, OnCompleteListener<Location>, GoToGoogleMapsNavigator {
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
    };
    private final String MAPS_START_URL = "https://www.google.com/maps/search/?api=1&query=";
    //NOTE: concat latitude,longitude


    private static final String LOCATION_MANAGER_TAG = "LOCATION_MANAGER_TAG";

    private Context currentContext;
    private LocationRequest locationRequest;
    private PendingIntent locationIntent;
    private Location mLastLocation;
    private OnLocationAvailableListener onLocationAvailableListener;

    private FusedLocationProviderClient mFusedLocationClient;


    public LocationManager(Context applicationContext) {
        currentContext = applicationContext;
    }

    public void setOnLocationAvailableListener(OnLocationAvailableListener onLocationAvailableListener) {
        this.onLocationAvailableListener = onLocationAvailableListener;
    }

    /***
     * Method that gets the last Location available of the device, and calls the defined OnCompleteListener<Location>
     */
    public void getCurrentLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(currentContext);
        mFusedLocationClient.flushLocations();
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(PRIORITY_HIGH_ACCURACY);

        mFusedLocationClient.getLocationAvailability().addOnSuccessListener(new OnSuccessListener<LocationAvailability>() {
            @Override
            public void onSuccess(LocationAvailability locationAvailability) {
                Log.d(LOCATION_MANAGER_TAG, "onSuccess: locationAvailability.isLocationAvailable " + locationAvailability.isLocationAvailable());

                mFusedLocationClient.requestLocationUpdates(locationRequest, locationIntent)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                Log.d(LOCATION_MANAGER_TAG, "Update Result: " + task.getResult());
                            }
                        });

                Log.d(LOCATION_MANAGER_TAG, "Requested updated location: ");
            }
        })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.d(LOCATION_MANAGER_TAG, "Task<Location>: Canceled");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(LOCATION_MANAGER_TAG, "Task<Location>: " + e.getMessage());
                    }
                });

        mFusedLocationClient.getLastLocation().addOnCompleteListener(this);

        //The request is high priority, this instruction removes it to be more efficient
        mFusedLocationClient.removeLocationUpdates(locationIntent);
    }

    /**
     * @return An array containing all required system Permissions
     */
    public static String[] getPermissions() {
        return PERMISSIONS;
    }

    /**
     * @param task A completed Task<Location>
     */
    @Override
    public void onComplete(@NonNull Task<Location> task) {
        Log.d(LOCATION_MANAGER_TAG, "Completed lastLocation");
        Log.d(LOCATION_MANAGER_TAG, "Task<Location> successful " + task.isSuccessful());

        if (task.isSuccessful() && task.getResult() != null) {
            mLastLocation = task.getResult();
            Log.d(LOCATION_MANAGER_TAG, "Victory!" + mLastLocation.toString());
            onLocationAvailableListener.onLocationAvailable(
                    new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
        } else if (!task.isSuccessful()) {
            Log.d(LOCATION_MANAGER_TAG, "Task<Location> not successful");
        } else if (task.getResult() == null) {
            Log.d(LOCATION_MANAGER_TAG, "Task<Location> result is null");
        }
    }

    /**
     * @param context       Context where open maps
     * @param mapsLatitude  latitude extracted by response sms
     * @param mapsLongitude longitude extracted by response sms
     * @author Turcato
     * Open the default maps application at the given Location(latitude, longitude)
     */
    public void openMapsUrl(Context context, Double mapsLatitude, Double mapsLongitude) {
        String url = MAPS_START_URL + mapsLatitude + "," + mapsLongitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    @Override
    public void open(@NonNull LatLng position) {
        openMapsUrl(currentContext, position.latitude, position.longitude);
    }
}