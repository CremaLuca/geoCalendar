package com.eis.geoCalendar.demo.Behaviour;

import com.eis.geoCalendar.demo.Localization.OnLocationAvailableListener;


public interface LocationRetriever {
    void getCurrentLocation();

    void setOnLocationAvailableListener(OnLocationAvailableListener onLocationAvailableListener);
}
