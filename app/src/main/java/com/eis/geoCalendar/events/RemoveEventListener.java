package com.eis.geoCalendar.events;

/**
 * @author Luca Crema
 * @since 08/01/2020
 */
public interface RemoveEventListener<E extends Event, EFR extends EventFailReason> {

    void onEventRemoved(E event);

    void onEventRemoveFail(E event, EFR reason);

}
