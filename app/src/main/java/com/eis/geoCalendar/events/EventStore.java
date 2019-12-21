package com.eis.geoCalendar.events;

import com.eis.geoCalendar.gps.GPSPosition;

import java.util.ArrayList;

public interface EventStore<E extends Event>{
    void addEvent(E event);
    boolean removeEvent(E event);
    ArrayList<E> getEventsInRange(GPSPosition p, float range);
    E getClosestEvent(GPSPosition p);
    ArrayList<E> getAllEvents();
}
