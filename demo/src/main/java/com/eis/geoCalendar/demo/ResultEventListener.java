package com.eis.geoCalendar.demo;

import com.google.android.gms.maps.model.LatLng;

public interface ResultEventListener {
    void onEventReturn(LatLng pos, String description);
}
