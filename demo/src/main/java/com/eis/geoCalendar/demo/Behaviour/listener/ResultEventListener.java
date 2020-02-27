package com.eis.geoCalendar.demo.Behaviour.listener;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Turcato
 */
public interface ResultEventListener {
    void onEventReturn(LatLng pos, String description);
}
