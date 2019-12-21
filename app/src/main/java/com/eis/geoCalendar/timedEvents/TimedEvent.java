package com.eis.geoCalendar.timedEvents;

import com.eis.geoCalendar.events.Event;
import java.time.LocalDateTime;

public interface TimedEvent<T> extends Event<T> {
    LocalDateTime getTime();
}
