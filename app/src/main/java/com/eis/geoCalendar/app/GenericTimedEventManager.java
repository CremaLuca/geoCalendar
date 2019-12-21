package com.eis.geoCalendar.app;

import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.timedEvents.TimedEvent;
import com.eis.geoCalendar.timedEvents.TimedEventManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

import androidx.annotation.NonNull;

public class GenericTimedEventManager<E extends TimedEvent> implements TimedEventManager<E> {

    public GenericTimedEventManager() {

    }

    @Override
    public ArrayList<E> getEventsBeforeTime(LocalDateTime time) {
        return null;
    }

    @Override
    public ArrayList<E> getEventsAfterTime(LocalDateTime time) {
        return null;
    }

    @Override
    public ArrayList<E> getEventsBetweenTime(LocalDateTime beginTime, LocalDateTime endTime) {
        return null;
    }

    @Override
    public void addEvent(@NonNull E event) {

    }

    @Override
    public boolean removeEvent(@NonNull E event) {
        return false;
    }

    @Override
    public ArrayList<E> getEventsInRange(@NonNull GPSPosition p, float range) {
        return null;
    }

    @Override
    public E getClosestEvent(@NonNull GPSPosition p) {
        return null;
    }

    @Override
    public ArrayList<E> getAllEvents() {
        return null;
    }
}
