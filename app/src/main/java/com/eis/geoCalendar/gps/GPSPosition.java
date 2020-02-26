package com.eis.geoCalendar.gps;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Represents a position calculated with GPS latitude and longitude coordinates.
 * Adapter for {@link android.location.Location} class.
 *
 * @author someone from group 4
 * @author Giorgia Bortoletti
 * @author Niccolò Turcato (some tweaking, including stealing code from De Zen)
 * @author Riccardo De Zen (his code for GPSPosition -> String, String -> GPSPosition was brutally stolen)
 */
public class GPSPosition {
    private Location mLocation = new Location(GPS_POSITION);

    private static final String GPS_POSITION = "GPSPosition";
    private static final String POSITION_SPLIT_SEQUENCE = "ç";

    /**
     * Default constructor
     */
    public GPSPosition() {
    }

    /**
     * Constructor that requires {@link Location} as param
     *
     * @param mLocation The {@link Location} obj
     */
    public GPSPosition(@NonNull Location mLocation) {
        this.mLocation = mLocation;
    }

    /**
     * Constructor that requires latitude and longitude.
     *
     * @param latitude  The latitude for this {@link GPSPosition}. Must be a suitable latitude
     *                  for {@link Location}.
     * @param longitude The longitude for this {@link GPSPosition}. Must be a suitable longitude
     *                  for {@link Location}.
     */
    public GPSPosition(double latitude, double longitude) {
        mLocation = new Location(GPS_POSITION); //provider name is unnecessary
        mLocation.setLatitude(latitude);
        mLocation.setLongitude(longitude);
    }

    /**
     * Constructor that requires {@link LatLng} as param
     *
     * @param location The {@link LatLng} obj
     */
    public GPSPosition(@NonNull LatLng location) {
        mLocation = new Location(GPS_POSITION); //provider name is unnecessary
        mLocation.setLatitude(location.latitude);
        mLocation.setLongitude(location.longitude);
    }

    /**
     * Constructor taking a {@link String} as a parameter. The {@code String} should be formatted
     * as {@link GPSPosition#toString()} does.
     *
     * @param locationString The Non-null properly formatted {@link String}.
     * @throws ArrayIndexOutOfBoundsException If {@code locationString} is not divided in at
     *                                        least two parts by
     *                                        {@link GPSPosition#POSITION_SPLIT_SEQUENCE}.
     * @throws NumberFormatException          If {@code locationString} is not divided into parts
     *                                        that
     *                                        are parsable as Double values.
     */
    public GPSPosition(@NonNull String locationString) throws
            ArrayIndexOutOfBoundsException,
            NumberFormatException {
        this(
                Double.parseDouble(locationString.split(POSITION_SPLIT_SEQUENCE)[0]),
                Double.parseDouble(locationString.split(POSITION_SPLIT_SEQUENCE)[1])
        );
    }

    /**
     * Method used to update or set the location coordinates for this {@link GPSPosition} object
     *
     * @param latitude  The updated GPS latitude
     * @param longitude The updated GPS longitude
     * @return the updated {@link GPSPosition} object
     */
    public GPSPosition updateLocation(double latitude, double longitude) {
        this.mLocation.setLatitude(latitude);
        this.mLocation.setLongitude(longitude);
        return this;
    }

    /**
     * @return The latitude of this {@link GPSPosition} object
     */
    public double getLatitude() {
        return mLocation.getLatitude();
    }

    /**
     * @return The longitude of this {@link GPSPosition} object
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
     * @param obj       The reference object with which to compare.
     * @param precision The precision in meters within which the positions remain equal.
     * @return true if this object is equal to obj argument in the precision argument range,
     * false otherwise.
     */
    public boolean equals(@Nullable Object obj, double precision) {
        if (obj == this)
            return true;
        if (!(obj instanceof GPSPosition))
            return false;
        return this.getDistance((GPSPosition) obj) <= precision;
    }

    /**
     * @param obj The reference object with which to compare.
     * @return true if this object is the same as the obj argument, false otherwise.
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        return equals(obj, 0);
    }

    /**
     * Returns a {@link String} representation of this {@link GPSPosition}. This {@code String} is
     * considered valid when provided to {@link GPSPosition#GPSPosition(String)}.
     * More precisely, the String will be in the format:
     * [latitude]{@link GPSPosition#POSITION_SPLIT_SEQUENCE}[longitude]
     *
     * @return A {@link String} representing this {@link GPSPosition}.
     */
    @NonNull
    @Override
    public String toString() {
        return getLatitude() + POSITION_SPLIT_SEQUENCE + getLongitude();
    }
}
