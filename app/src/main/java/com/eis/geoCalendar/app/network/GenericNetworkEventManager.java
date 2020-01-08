package com.eis.geoCalendar.app.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eis.communication.Peer;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.EventFailReason;
import com.eis.geoCalendar.network.EventNetworkManager;
import com.eis.geoCalendar.network.NetworkEvent;
import com.eis.geoCalendar.network.NetworkEventManager;
import com.eis.geoCalendar.network.eventlistener.AddEventListener;
import com.eis.geoCalendar.network.eventlistener.GetEventListener;
import com.eis.geoCalendar.network.eventlistener.RemoveEventListener;

/**
 * {@link com.eis.geoCalendar.events.EventManager} that uses network to save events.
 *
 * @author Luca Crema
 * @since 08/01/2020
 */
public class GenericNetworkEventManager<E extends NetworkEvent, P extends Peer> implements NetworkEventManager<E, EventFailReason> {


    protected GenericEventNetwork<E, P> eventNetwork;

    /**
     * @param networkManager A NetworkManager for events. The user must belong to an existing network already.
     */
    public GenericNetworkEventManager(EventNetworkManager<E, P> networkManager) {
        eventNetwork = new GenericEventNetwork<>(networkManager);
    }

    @Override
    public void getEventsInRange(@NonNull GPSPosition p, float range, GetEventListener<E, EventFailReason> getEventListener) {

    }

    @Override
    public void addEvent(@NonNull E event, @Nullable AddEventListener<E, EventFailReason> addEventListener) {

    }

    @Override
    public void removeEvent(@NonNull E event, @Nullable RemoveEventListener<E, EventFailReason> removeEventListener) {

    }
}
