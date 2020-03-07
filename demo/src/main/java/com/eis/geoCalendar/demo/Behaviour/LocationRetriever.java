package com.eis.geoCalendar.demo.Behaviour;

import com.eis.geoCalendar.demo.Localization.OnLocationAvailableListener;

/**
 * Interface that defines the Behaviour of a Class that is able to retrieve the current GPS coordinates
 *
 * @author Turcato
 */
public interface LocationRetriever {
    void getCurrentLocation();

    void setOnLocationAvailableListener(OnLocationAvailableListener onLocationAvailableListener);
}
