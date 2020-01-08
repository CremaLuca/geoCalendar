package com.eis.geoCalendar.events;

import com.eis.communication.network.FailReason;

/**
 * Callback for added event.
 * Used in the {@link AsyncEventManager}.
 *
 * @author Luca Crema
 * @since 08/01/2020
 */
public interface AddEventListener<E extends Event> {

    void onEventAdded(E event);

    void onEventAddFail(E event, FailReason reason);
}
