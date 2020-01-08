package com.eis.geoCalendar.network.eventlistener;

import com.eis.communication.network.FailReason;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.EventFailReason;
import com.eis.geoCalendar.network.NetworkEvent;

import java.util.ArrayList;

/**
 * @author Luca Crema
 * @since 08/01/2020
 */
public interface GetEventListener<E extends NetworkEvent, EFR extends EventFailReason> {

    void onGetEvents(GPSPosition position, ArrayList<E> events);

    void onGetEventsFail(GPSPosition position, FailReason reason);

}
