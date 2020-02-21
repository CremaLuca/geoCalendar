package com.eis.geoCalendar.demo.Behaviour;

import android.graphics.Bitmap;
import android.util.ArrayMap;

import androidx.annotation.NonNull;

import com.eis.geoCalendar.app.network.GenericNetworkEvent;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.NetworkEvent;
import com.eis.geoCalendar.network.NetworkEventUser;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Class created to manage network related Events on a GoogleMap and divide the code that manages
 * the logic of interaction with the user from the activities, fragments, ecc.. code
 * <p>
 * All that is needed to use this class is just to create a SupportMapFragment from a map activity
 * anc call getMapAsync(networkEventMapBehaviour)
 * <p>
 * Subscription of Event related Listeners works follwing the Observer Design pattern
 * <p>
 * Note that operations accessing the map can be done once that has been built (obv) and it has
 * been initialized, set an OnMapInitializedListener to get notified of the complete initialization
 * of the map
 *
 * @author Turcato
 */
public class NetworkEventMapBehaviour extends EventMapBehaviour {
    private Map<Marker, NetworkEvent> currentEvents;
    private Map<NetworkEventUser, Float> usersColors;
    private NetworkEventUser myself;
    private List<OnEventCreatedListener> onEventCreatedListeners;
    private int resourceMarker;
    private Bitmap userIcon;
    private boolean allowMapRemovalNetworkEvents;

    private static final float MAX_COLOR = 360;

    /**
     * Default constructor that creates an instance of NetworkEventMapBehaviour without consciousness
     * of itself, not usable
     */
    private NetworkEventMapBehaviour() {
        currentEvents = new ArrayMap<>();
        usersColors = new ArrayMap<>();
    }

    /**
     * Constructor that creates an Instance of NetworkEventMapBehaviour that is aware the user that
     * created the map
     * <p>
     * The events from other users will be draw as default google map's icons (different colors for each user)
     * while the user that created the map will have events represented by the default marker (default color)
     * (the user's marker may clash with another user's marker depending by how many external users are present)
     *
     * @param myself The Network user that has created the map
     */
    public NetworkEventMapBehaviour(@NonNull NetworkEventUser myself) {
        this();
        this.myself = myself;
    }


    /**
     * Constructor that creates an Instance of NetworkEventMapBehaviour that is aware the user that
     * created the map, and loads a custom bitmap for the marker used for drawing user's events on the
     * map
     * <p>
     * The events from other users will be draw as default google map's icons (different colors for each user)
     *
     * @param myself   The Network user that has created the map
     * @param userIcon The custom bitmap that the map will display for the user's events
     */
    public NetworkEventMapBehaviour(@NonNull NetworkEventUser myself, @NonNull Bitmap userIcon) {
        this();
        this.myself = myself;
        this.userIcon = userIcon;
    }

    /**
     * These events are defined elsewhere, so no need to notify listeners
     * Adds all the events to the map, defining a different color for each user (in the available palette)
     * <p>
     * If the map already contains events the new ones will be added to the existing ones, if there are new users
     * the colors will be re-assigned
     *
     * @param events A bunch of network events to position in the map (both description and
     *               Position must be defined), must have a content parsable to String
     * @param users  The users of the given events, must be a complete list of all the users that
     *               appear in the list of events
     */
    public void addEventsToMap(@NonNull List<NetworkEventUser> users, @NonNull List<NetworkEvent> events) {
        for (NetworkEventUser user : users) {
            if (!usersColors.keySet().contains(user)) {
                //The first new user triggers this code block, will be executed only once
                List<NetworkEventUser> completeUsersList = new ArrayList<>();
                completeUsersList.addAll(usersColors.keySet());
                completeUsersList.addAll(users);
                usersColors = getUsersColors(completeUsersList);
                //All the new users are now registered and have been assigned a color
                events.addAll(currentEvents.values());

                mMap.clear();
            }
        }
        /*  At this point if there were some new users, all the events on the map must be repositioned
            due to the change of colors
            The list events contains only the new events or all of them (old and new), and all users
            (old and new) have been assigned with a new color
         */

        for (NetworkEvent event : events) {
            LatLng pos = new LatLng(event.getPosition().getLatitude(),
                    event.getPosition().getLongitude());
            Marker created;

            if (event.getOwner().equals(myself))
                created = addMyEvent(event);
            else
                created = mMap.addMarker(new MarkerOptions().position(pos)
                        .icon(BitmapDescriptorFactory.defaultMarker(usersColors.get(event.getOwner())))
                        .title(event.getContent().toString()));

            currentEvents.put(created, event);
            created.setTag(event);
        }
    }

    /**
     * Adds all the events to the map, defining a color for the user (in the available palette)
     * <p>
     * If the map already contains events the new ones will be added to the existing ones,
     * if the user is new the colors will be re-assigned
     *
     * @param user   A networkUser
     * @param events A list of events from {@code user}
     */
    public void addEventsToMap(@NonNull NetworkEventUser user, @NonNull List<NetworkEvent> events) {
        List<NetworkEventUser> userList = new ArrayList<>();
        userList.add(user);
        addEventsToMap(userList, events);
    }


    /**
     * Defines the colors for the map's markers
     * See {@link com.google.android.gms.maps.model.BitmapDescriptorFactory} for details
     * Colors are created in the domain (0, 360)
     *
     * @param users The users of the network
     * @return A Map that associates each user to a float (0, 360) defining a color
     */
    private Map<NetworkEventUser, Float> getUsersColors(List<NetworkEventUser> users) {
        ArrayMap<NetworkEventUser, Float> usersColors = new ArrayMap<>();
        float[] colors = new float[users.size()];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = (i + 1) * ((MAX_COLOR - 1) / colors.length);
        }
        int j = 0;
        for (NetworkEventUser user : users) {
            usersColors.put(user, colors[j++]);
        }
        return usersColors;
    }

    /**
     * Called by a fragment of the application that returns the parameters to Create a new event and update the map
     *
     * @param pos         The position of the event to create
     * @param description The description of the event to create
     */
    @Override
    public void onEventReturn(LatLng pos, String description) {
        GPSPosition eventPos = new GPSPosition(pos.latitude, pos.longitude);
        NetworkEvent event = new GenericNetworkEvent<>(eventPos, description, myself);
        if (onEventCreatedListeners != null && !onEventCreatedListeners.isEmpty())
            for (OnEventCreatedListener listener : onEventCreatedListeners) {
                listener.onEventCreated(event);
            }

        Marker created = addMyEvent(event);
        created.setTag(event);
        moveMap(pos);
        currentEvents.put(created, event);
    }

    /**
     * Adds the given event to the map, with the custom icon if set, if the the default icon will be used
     *
     * @param myEvent An event created from the current user
     * @return The marker that represents the event on the map
     */
    private Marker addMyEvent(NetworkEvent myEvent) {
        LatLng pos = new LatLng(myEvent.getPosition().getLatitude(),
                myEvent.getPosition().getLongitude());
        if (userIcon != null)
            return mMap.addMarker(new MarkerOptions().position(pos).icon
                    (BitmapDescriptorFactory.fromBitmap(userIcon)).title(myEvent.getContent().toString()));
        else
            return mMap.addMarker(new MarkerOptions().position(pos).title(myEvent.getContent().toString()));
        //automatically cuts title if too long
    }

    @Override
    public void subscribeOnEventCreatedListener(@NonNull OnEventCreatedListener listener) {
        if (onEventCreatedListeners == null)
            onEventCreatedListeners = new ArrayList<>();
        onEventCreatedListeners.add(listener);
    }

    @Override
    public void unsubscribeOnEventCreatedListener(OnEventCreatedListener listener) {
        if (onEventCreatedListeners != null)
            onEventCreatedListeners.remove(listener);
    }

    /**
     * If set {@code True} it will be allowed for the user to remove from the map events coming
     * from the network, listeners will be notified.
     * If set to {@code False} it won't be allowed and if the user tries to remove a
     * network event from the UI using whatever view nothing will happen, and listeners won't be notified
     * <p>
     * Whatever value this flag will be set, the user will always be able to delete its own events
     *
     * @param allow Boolean value that indicates will of the user to be able to delete network events
     *              from the map
     */
    public void allowMapRemovalNetworkEvents(boolean allow) {
        this.allowMapRemovalNetworkEvents = allow;
    }

    /**
     * @param marker The marker that has been selected for confirming deletion
     */
    @Override
    protected void callRemoveEventDialog(Marker marker) {
        if (marker.getTag() instanceof NetworkEvent &&
                (((NetworkEvent) marker.getTag()).getOwner().equals(myself) || allowMapRemovalNetworkEvents))
            super.callRemoveEventDialog(marker);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTag() instanceof NetworkEvent)
            if (!((NetworkEvent) marker.getTag()).getOwner().equals(myself))
                if (allowMapRemovalNetworkEvents)
                    bottomSheetBehaviour.setRemoveViewVisible(true);
                else
                    bottomSheetBehaviour.setRemoveViewVisible(false);
            else
                bottomSheetBehaviour.setRemoveViewVisible(true);
        return super.onMarkerClick(marker);
    }
}
