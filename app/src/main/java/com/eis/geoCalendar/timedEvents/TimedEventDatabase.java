package com.eis.geoCalendar.timedEvents;

import com.eis.geoCalendar.events.EventDatabase;

/**
 * @param <E> Type of TimedEvent.
 * @author Luca Crema
 * @version 1.1
 * @see com.eis.geoCalendar.events.EventDatabase
 * @since 21/12/2019
 */
public interface TimedEventDatabase<E extends TimedEvent> extends EventDatabase<E> {
}
