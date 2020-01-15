package com.eis.geoCalendar.app.network;

import androidx.annotation.NonNull;

import com.eis.geoCalendar.gps.GPSPosition;

/**
 * Class representing a {@link com.eis.geoCalendar.network.NetworkEvent} with {@link String} as
 * its content and {@link SMSNetworkEventUser} as its owner.
 *
 * @author Riccardo De Zen
 */
public class SMSStringNetworkEvent extends GenericNetworkEvent<String, SMSNetworkEventUser> {
    /**
     * Main constructor, which acquires all details of the just created event.
     *
     * @param position The position of the event.
     * @param content  The content of the event.
     * @param owner    The owner of the event.
     */
    public SMSStringNetworkEvent(@NonNull GPSPosition position, @NonNull String content,
                                 @NonNull SMSNetworkEventUser owner) {
        super(position, content, owner);
    }
}
