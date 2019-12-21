package com.eis.geoCalendar.app;

import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.timedEvents.TimedEvent;

import java.time.LocalDateTime;

public class GenericTimedEvent<T> implements TimedEvent<T> {

    public GenericTimedEvent(GPSPosition position, T content, LocalDateTime time) {

    }

    @Override
    public LocalDateTime getTime() {
        return null;
    }

    @Override
    public T getContent() {
        return null;
    }

    @Override
    public GPSPosition getPosition() {
        return null;
    }
}
