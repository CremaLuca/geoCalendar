package com.eis.geoCalendar.events;

/**
 * Callback for added event.
 * Used in the {@link AsyncEventManager}.
 *
 * @author Luca Crema
 * @since 08/01/2020
 */
public interface AddEventListener<E extends Event, EFR extends EventFailReason> {

    void onEventAdded(E event);

    void onEventAddFail(E event, EFR reason);
}
