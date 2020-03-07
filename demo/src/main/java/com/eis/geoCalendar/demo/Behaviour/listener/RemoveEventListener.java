package com.eis.geoCalendar.demo.Behaviour.listener;

import com.google.android.gms.maps.model.Marker;

/**
 * Interface that defines how an object can be notified to delete an event on a map
 * (represented by a {@link com.google.android.gms.maps.model.Marker})
 *
 * @author Turcato
 */
public interface RemoveEventListener {
     void removeMark(Marker marker);
}
