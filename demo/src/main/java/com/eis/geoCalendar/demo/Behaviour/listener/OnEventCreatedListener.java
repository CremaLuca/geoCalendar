package com.eis.geoCalendar.demo.Behaviour.listener;

import com.eis.geoCalendar.events.Event;

/**
 * Interface defined for a Listener that waits for notice of the creation of an event from the map
 *
 * @param <E> Type of event
 *
 * @author Turcato
 */
public interface OnEventCreatedListener<E extends Event> {

    void onEventCreated(E newEvent);
}
