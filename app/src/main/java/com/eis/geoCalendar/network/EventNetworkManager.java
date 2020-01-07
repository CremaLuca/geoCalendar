package com.eis.geoCalendar.network;

import com.eis.communication.network.FailReason;
import com.eis.communication.network.NetworkManager;
import com.eis.communication.network.NetworkUser;
import com.eis.geoCalendar.gps.GPSPosition;

import java.util.ArrayList;

/**
 * Defines which types must be used to implement a {@link NetworkManager} in order to use it in a {@link EventNetwork}.
 * The main use of this interface is to reduce the amount of variable types.
 *
 * @param <E> Type of events handled by the network.
 * @param <U> Type of users of the network.
 * @author Luca Crema
 * @since 28/12/2019
 */
public interface EventNetworkManager<E extends NetworkEvent, U extends NetworkUser> extends NetworkManager<GPSPosition, ArrayList<E>, U, FailReason> {
}
