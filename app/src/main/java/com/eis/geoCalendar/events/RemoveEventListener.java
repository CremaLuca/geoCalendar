package com.eis.geoCalendar.events;

import com.eis.communication.network.FailReason;

/**
 * @author Luca Crema
 * @since 08/01/2020
 */
public interface RemoveEventListener<E extends Event> {

    void onEventRemoved(E event);

    void onEventRemoveFail(E event, FailReason reason);

}
