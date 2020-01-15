package com.eis.geoCalendar.demo.Localization;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

/**
 * @author Turcato
 * <p>
 * TODO: this is here temporarely just for the demo
 */
public class LocationManager {
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
    };


    private final static String LocationManagerTag = "LocationManagerTag";

    private Context currentContext;
    private LocationRequest locationRequest;
    private PendingIntent locationIntent;
    private Location mLastLocation;

    private FusedLocationProviderClient mFusedLocationClient;

    public LocationManager(Context applicationContext) {
        currentContext = applicationContext;
    }

    /***
     * Method that gets the last Location available of the device, and executes the imposed command
     * calling command.execute(foundLocation)
     *
     * @param command object of a class that implements interface Command
     */
    public void getLastLocation(final Command<Location> command) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(currentContext);
        mFusedLocationClient.flushLocations();
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(PRIORITY_HIGH_ACCURACY);

        mFusedLocationClient.getLocationAvailability().addOnSuccessListener(new OnSuccessListener<LocationAvailability>() {
            @Override
            public void onSuccess(LocationAvailability locationAvailability) {
                Log.d(LocationManagerTag, "onSuccess: locationAvailability.isLocationAvailable " + locationAvailability.isLocationAvailable());

                mFusedLocationClient.requestLocationUpdates(locationRequest, locationIntent)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                Log.d(LocationManagerTag, "Update Result: " + task.getResult());
                            }
                        });

                Log.d(LocationManagerTag, "Requested updated location: ");

                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Log.d(LocationManagerTag, "Completed lastLocation");
                        Log.d(LocationManagerTag, "Task<Location> successful " + task.isSuccessful());

                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            Log.d(LocationManagerTag, "Victory!" + mLastLocation.toString());
                            command.execute(mLastLocation);
                            /*
                            mLastLocation is used directly here because once out of OnComplete
                            the Location isn't available and the variable that contains it
                            becomes null
                            */
                        } else if (!task.isSuccessful()) {
                            Log.d(LocationManagerTag, "Task<Location> not successful");
                        } else if (task.getResult() == null) {
                            Log.d(LocationManagerTag, "Task<Location> result is null");
                        }
                        Log.d(LocationManagerTag, "End of OnComplete " + mLastLocation.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(LocationManagerTag, "Task<Location>: " + e.getMessage());
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.d(LocationManagerTag, "Task<Location> getLastLocation: Canceled");
                    }
                });
            }
        })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.d(LocationManagerTag, "Task<Location>: Canceled");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(LocationManagerTag, "Task<Location>: " + e.getMessage());
                    }
                });

        //The request is high priority, this instruction removes it to be more efficient
        mFusedLocationClient.removeLocationUpdates(locationIntent);
    }

    /**
     * @return An array containing all required system Permissions
     */
    public static String[] getPermissions() {
        return PERMISSIONS;
    }
}