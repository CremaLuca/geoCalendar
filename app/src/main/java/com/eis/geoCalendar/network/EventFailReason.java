package com.eis.geoCalendar.network;

import androidx.annotation.NonNull;

/**
 * TypeSafe enumeration of possible reasons why a request in {@link NetworkEventManager} has failed.
 *
 * @author Luca Crema
 * @since 08/01/2020
 */
public class EventFailReason {

    public static final EventFailReason NO_NETWORK = new EventFailReason("No network");

    private String name;

    protected EventFailReason(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
