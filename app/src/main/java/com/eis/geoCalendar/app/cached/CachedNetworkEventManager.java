package com.eis.geoCalendar.app.cached;

import com.eis.geoCalendar.app.network.GenericNetworkedEventManager;
import com.eis.geoCalendar.events.AddEventListener;
import com.eis.geoCalendar.events.EventDatabase;
import com.eis.geoCalendar.events.RemoveEventListener;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.EventNetwork;
import com.eis.geoCalendar.network.NetworkEvent;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * This manager is based on the network but keeps a small amount of events in local db.
 * The events saved in memory are user's only events.
 *
 * @author Luca Crema
 * @since 08/01/2020
 */
public class CachedNetworkEventManager<E extends NetworkEvent> extends GenericNetworkedEventManager<E> {

    private EventDatabase<E> eventDatabase;

    /**
     * @param eventDatabase An instance of a database.
     * @param eventNetwork  An instance of a correctly setup network.
     */
    public CachedNetworkEventManager(EventNetwork<E> eventNetwork, EventDatabase<E> eventDatabase) {
        super(eventNetwork);
        this.eventDatabase = eventDatabase;
    }

    /**
     * Synchronous call to database to obtain personal events.
     *
     * @param p     The center of the circle of search.
     * @param range The radius of the circe of search in meters.
     * @return List of personal events.
     */
    public ArrayList<E> getMyEventsInRange(@NonNull GPSPosition p, float range) {
        ArrayList<E> eventsInDB = eventDatabase.getSavedEvents();
        ArrayList<E> foundEvents = new ArrayList<>();

        for (E event : eventsInDB) {
            if (event.getPosition().getDistance(p) <= range)
                foundEvents.add(event);
        }
        return foundEvents;
    }

    @Override
    public void addEvent(@NonNull E event, @Nullable AddEventListener<E> addEventListener) {
        super.addEvent(event, addEventListener);
        eventDatabase.saveEvent(event);
    }

    @Override
    public void removeEvent(@NonNull E event, @Nullable RemoveEventListener<E> removeEventListener) {
        super.removeEvent(event, removeEventListener);
        eventDatabase.removeEvent(event);
    }
}
