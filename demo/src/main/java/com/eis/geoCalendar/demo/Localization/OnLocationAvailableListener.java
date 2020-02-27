package com.eis.geoCalendar.demo.Localization;

import com.google.android.gms.maps.model.LatLng;

/**
 * Interface that defines how an object can be notified of the availability of an updated current
 * GPS position
 *
 * @author Turcato
 */
public interface OnLocationAvailableListener {
    void onLocationAvailable(LatLng position);
}
