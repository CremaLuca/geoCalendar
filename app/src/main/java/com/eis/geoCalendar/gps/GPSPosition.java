package com.eis.geoCalendar.gps;

public interface GPSPosition {
    float getLatitude();
    float getLongitude();
    float getDistance(GPSPosition otherPosition);
}
