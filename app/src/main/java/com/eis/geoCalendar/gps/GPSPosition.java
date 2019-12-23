package com.eis.geoCalendar.gps;

import androidx.annotation.NonNull;

/**
 * Represents a position calculated with GPS latitude and longitude coordinates.
 * Adapter for {@link android.location.Location} class.
 */
public class GPSPosition {

    /**
     * @return The latitude position
     */
    public float getLatitude() {
        return 0;
    }

    /**
     * @return The longitude position
     */
    public float getLongitude() {
        return 0;
    }

    /**
     * @param otherPosition A position to calculate the distance from
     * @return The distance in meters from the given position.
     */
    public float getDistance(@NonNull final GPSPosition otherPosition) {
        return 0;
    }
}
