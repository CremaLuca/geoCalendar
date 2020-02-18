package com.eis.geoCalendar.demo.Behaviour;

import com.eis.geoCalendar.events.Event;

/**
 * Interface defined for a listener that waits for an event to be triggered (meaning the user has accessed to it)
 * by the UI map
 *
 * @param <E> Type of event
 */
public interface OnEventTriggeredListener<E extends Event> {

    void onEventTriggered(E event);
}
