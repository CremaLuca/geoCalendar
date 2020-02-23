package com.eis.geoCalendar.demo.Behaviour;

import com.eis.geoCalendar.events.Event;

/**
 * Interface defined for a Listener that waits for notice of the removal of an event from the map
 *
 * @param <E> Type of Event
 */
public interface OnEventRemovedListener<E extends Event> {
    void onEventRemoved(E removedEvent);
}
