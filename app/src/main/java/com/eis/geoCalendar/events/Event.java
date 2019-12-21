package com.eis.geoCalendar.events;

import com.eis.geoCalendar.gps.GPSPosition;

public interface Event<T>{
    T getContent();
    GPSPosition getPosition();
}
