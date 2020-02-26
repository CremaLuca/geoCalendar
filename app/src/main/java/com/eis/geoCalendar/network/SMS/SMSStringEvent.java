package com.eis.geoCalendar.network.SMS;

import androidx.annotation.NonNull;

import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.NetworkEvent;

/**
 * Simple class that implements interface NetworkEvent
 * Represents an Event on the Network that is created from a user, has a string Description and a Location
 * which is its identifier
 *
 * @author Turcato
 */
public class SMSStringEvent implements NetworkEvent<String, SMSNetworkEventUser> {
    private GPSPosition position;
    private String description;
    private SMSNetworkEventUser owner;

    /**
     * Constructor that fully initializes the object
     *
     * @param position    The identifier of this event, the geographical coordinates of the event
     * @param description A brief description of the event
     * @param owner       The user that created the event
     */
    public SMSStringEvent(@NonNull GPSPosition position, @NonNull String description, @NonNull SMSNetworkEventUser owner) {
        this.position = position;
        this.description = description;
        this.owner = owner;
    }

    /**
     * @return The content of this event, which is its description
     */
    @Override
    public String getContent() {
        return description;
    }

    /**
     * @return The {@link SMSNetworkEventUser} that created this event
     */
    @Override
    public SMSNetworkEventUser getOwner() {
        return owner;
    }

    /**
     * @return The {@link GPSPosition} with the geographical coordinates of the event
     */
    @Override
    public GPSPosition getPosition() {
        return position;
    }
}
