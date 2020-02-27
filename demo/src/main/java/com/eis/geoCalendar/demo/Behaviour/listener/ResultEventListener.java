package com.eis.geoCalendar.demo.Behaviour.listener;

import com.google.android.gms.maps.model.LatLng;

public interface ResultEventListener {
    void onEventReturn(LatLng pos, String description);
}
