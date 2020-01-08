package com.eis.geoCalendar.app.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eis.communication.network.FailReason;
import com.eis.geoCalendar.events.AddEventListener;
import com.eis.geoCalendar.events.AsyncEventManager;
import com.eis.geoCalendar.events.GetEventListener;
import com.eis.geoCalendar.events.RemoveEventListener;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.EventNetwork;
import com.eis.geoCalendar.network.NetworkEvent;
import com.eis.geoCalendar.network.StoreEventListener;

import java.util.ArrayList;

/**
 * {@link com.eis.geoCalendar.events.EventManager} that only uses network to save events.
 *
 * @author Luca Crema
 * @since 08/01/2020
 */
public class GenericNetworkedEventManager<E extends NetworkEvent> implements AsyncEventManager<E> {


    protected EventNetwork<E> eventNetwork;

    /**
     * @param eventNetwork An event network already setup correctly.
     */
    public GenericNetworkedEventManager(EventNetwork<E> eventNetwork) {
        this.eventNetwork = eventNetwork;
    }

    @Override
    public void getEventsInRange(@NonNull GPSPosition p, float range, final GetEventListener<E> getEventListener) {
        eventNetwork.getEvents(p, range, new com.eis.geoCalendar.network.GetEventListener<E>() {
            @Override
            public void onGetEvents(@NonNull GPSPosition requestedPosition, @NonNull ArrayList<E> events) {
                getEventListener.onGetEvents(requestedPosition, events);
            }

            @Override
            public void onGetEventsFailed(@NonNull GPSPosition requestedPosition, @NonNull FailReason reason) {
                getEventListener.onGetEventsFail(requestedPosition, reason);
            }
        });
    }

    @Override
    public void addEvent(@NonNull E event, @Nullable final AddEventListener<E> addEventListener) {
        eventNetwork.storeEvent(event, new StoreEventListener<E>() {
            @Override
            public void onEventStored(@NonNull E event) {
                if (addEventListener != null)
                    addEventListener.onEventAdded(event);
            }

            @Override
            public void onEventStoreFail(@NonNull E event, @NonNull FailReason reason) {
                if (addEventListener != null)
                    addEventListener.onEventAddFail(event, reason);
            }
        });
    }

    @Override
    public void removeEvent(@NonNull E event, @Nullable final RemoveEventListener<E> removeEventListener) {
        eventNetwork.removeEvent(event, new com.eis.geoCalendar.network.RemoveEventListener<E>() {
            @Override
            public void onEventRemoved(@NonNull E event) {
                if (removeEventListener != null)
                    removeEventListener.onEventRemoved(event);
            }

            @Override
            public void onEventNotRemoved(@NonNull E event, @NonNull FailReason reason) {
                if (removeEventListener != null)
                    removeEventListener.onEventRemoveFail(event, reason);

            }
        });
    }
}
