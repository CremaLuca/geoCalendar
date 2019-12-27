package com.eis.geoCalendar.timedEvents.network;

import com.eis.geoCalendar.network.NetworkEvent;
import com.eis.geoCalendar.network.NetworkEventUser;
import com.eis.geoCalendar.timedEvents.TimedEvent;

/**
 * Represents a timed event of the network.
 *
 * @param <T> Type of content.
 * @param <U> Type of network user.
 */
public interface NetworkTimedEvent<T, U extends NetworkEventUser> extends NetworkEvent<T, U>, TimedEvent<T> {
}
