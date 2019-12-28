package com.eis.geoCalendar.gps;

import android.location.Location;

import androidx.annotation.NonNull;

/**
 * Represents a position calculated with GPS latitude and longitude coordinates.
 * Adapter for {@link android.location.Location} class.
 *
 * @author someone from group 4
 * @author Giorgia Bortoletti
 */
public class GPSPosition {

    protected Location mLocation;

    public GPSPosition(double latitude, double longitude) {
        mLocation = new Location("GPSPosition");
        mLocation.setLatitude(latitude);
        mLocation.setLongitude(longitude);
    }

    public GPSPosition(Location mLocation){
        this.mLocation = mLocation;
    }

    /**
     * @return The latitude position
     */
    public double getLatitude() {
        return mLocation.getLatitude();
    }

    /**
     * @return The longitude position
     */
    public double getLongitude() {
        return mLocation.getLongitude();
    }

    /**
     * @param otherPosition A position to calculate the distance from
     * @return The distance in meters from the given position.
     */
    public double getDistance(@NonNull final GPSPosition otherPosition) {
        return mLocation.distanceTo(otherPosition.mLocation);
    }

    /**
     * @param obj the reference object with which to compare.
     * @param precision the precision in meters within which the positions remain equal.
     * @return true if this object is equal to obj argument in the precision argument range, false otherwise.
     */
    public boolean equals(@NonNull Object obj, double precision){
        if(obj == null || !(obj instanceof GPSPosition))
            return false;
        if(obj == this)
            return true;
        if(this.getDistance((GPSPosition) obj) <= precision)
            return true;
        return false;
    }

    /**
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument, false otherwise.
     */
    @Override
    public boolean equals(@NonNull Object obj){
        return equals(obj, 0);
    }
}
