package com.eis.geoCalendar.events;

import com.eis.communication.network.FailReason;
import com.eis.geoCalendar.gps.GPSPosition;

import java.util.ArrayList;

/**
 * @author Luca Crema
 * @since 08/01/2020
 */
public interface GetEventListener<E extends Event, EFR extends EventFailReason> {

    void onGetEvents(GPSPosition position, ArrayList<E> events);

    void onGetEventsFail(GPSPosition position, FailReason reason);

}
