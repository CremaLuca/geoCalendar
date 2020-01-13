package com.eis.geoCalendar.gps;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Represents a position calculated with GPS latitude and longitude coordinates.
 * Adapter for {@link android.location.Location} class.
 *
 * @author someone from group 4
 * @author Giorgia Bortoletti
 */
public class GPSPosition {

    private static final String LOCATION_PROVIDER = "com.eis.geoCalendar.gps.GPSPosition";

    private double latitude;
    private double longitude;

    public GPSPosition(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GPSPosition(Location position){
        this.latitude = position.getLatitude();
        this.longitude = position.getLongitude();
    }

    /**
     * @return The latitude position
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return The longitude position
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param otherPosition A position to calculate the distance from
     * @return The distance in meters from the given position.
     */
    public float getDistance(@NonNull final GPSPosition otherPosition) {
        float[] results = new float[1];
        Location.distanceBetween(getLatitude(), getLongitude(), otherPosition.getLatitude(), otherPosition.getLongitude(), results);
        return results[0];
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(!(obj instanceof GPSPosition))
            return false;
        GPSPosition other = (GPSPosition) obj;
        return getLatitude() == other.getLatitude()
                && getLongitude() == other.getLongitude();
    }
}
