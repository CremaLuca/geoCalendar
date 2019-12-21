package com.eis.geoCalendar.timedEvents;

import com.eis.geoCalendar.events.EventStore;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface TimedEventStore<E extends TimedEvent> extends EventStore<E> {
    ArrayList<E> getEventsBeforeTime(LocalDateTime time);
    ArrayList<E> getEventsAfterTime(LocalDateTime time);
    ArrayList<E> getEventsBetweenTime(LocalDateTime beginTime, LocalDateTime endTime);
}
