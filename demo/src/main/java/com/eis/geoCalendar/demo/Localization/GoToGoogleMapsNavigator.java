package com.eis.geoCalendar.demo.Localization;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

/**
 * Interface for objects that can open the google maps application at a given Location
 *
 * @author Turcato
 */
public interface GoToGoogleMapsNavigator {
    /**
     * @param position A valid Gps Location
     */
    void open(@NonNull LatLng position);
}
