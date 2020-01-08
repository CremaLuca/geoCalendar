package com.eis.geoCalendar.network.eventlistener;

import com.eis.geoCalendar.network.EventFailReason;
import com.eis.geoCalendar.network.NetworkEvent;

/**
 * Callback for added event.
 * Used in the {@link com.eis.geoCalendar.network.NetworkEventManager}.
 *
 * @author Luca Crema
 * @since 08/01/2020
 */
public interface AddEventListener<E extends NetworkEvent, EFR extends EventFailReason> {

    void onEventAdded(E event);

    void onEventAddFail(E event, EFR reason);
}
