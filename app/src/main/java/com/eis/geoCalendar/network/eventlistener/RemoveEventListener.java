package com.eis.geoCalendar.network.eventlistener;

import com.eis.geoCalendar.network.EventFailReason;
import com.eis.geoCalendar.network.NetworkEvent;

/**
 * @author Luca Crema
 * @since 08/01/2020
 */
public interface RemoveEventListener<E extends NetworkEvent, EFR extends EventFailReason> {

    void onEventRemoved(E event);

    void onEventRemoveFail(E event, EFR reason);

}
