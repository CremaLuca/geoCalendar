package com.eis.geoCalendar.app.cached;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eis.geoCalendar.events.AddEventListener;
import com.eis.geoCalendar.events.AsyncEventManager;
import com.eis.geoCalendar.events.GetEventListener;
import com.eis.geoCalendar.events.RemoveEventListener;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.NetworkEvent;

/**
 * This manager is based on the network but keeps a small amount of events in local db
 *
 * @author Luca Crema
 * @since 08/01/2020
 */
public class CachedNetworkEventManager<E extends NetworkEvent> implements AsyncEventManager<E> {


    public CachedNetworkEventManager() {

    }

    @Override
    public void getEventsInRange(@NonNull GPSPosition p, float range, GetEventListener<E> getEventListener) {

    }

    @Override
    public void addEvent(@NonNull E event, @Nullable AddEventListener<E> addEventListener) {

    }

    @Override
    public void removeEvent(@NonNull E event, @Nullable RemoveEventListener<E> removeEventListener) {

    }
}
