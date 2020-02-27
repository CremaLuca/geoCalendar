package com.eis.geoCalendar.demo.Behaviour;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.eis.geoCalendar.demo.Behaviour.listener.OnEventCreatedListener;
import com.eis.geoCalendar.demo.Behaviour.listener.OnEventRemovedListener;
import com.eis.geoCalendar.demo.Behaviour.listener.OnEventTriggeredListener;
import com.eis.geoCalendar.demo.Behaviour.listener.OnMapInitializedListener;
import com.eis.geoCalendar.demo.Bottomsheet.BottomSheetBehaviour;
import com.eis.geoCalendar.demo.Dialogs.AbstractAddEventDialog;
import com.eis.geoCalendar.demo.Dialogs.AbstractRemoveEventDialog;
import com.eis.geoCalendar.demo.Localization.GoToGoogleMapsNavigator;
import com.eis.geoCalendar.events.Event;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * @author Turcato
 * <p>
 * This interface can be used to implement the variouse GoogleMap's events, without writing all interfaces' names
 * <p>
 * Defining a class that implements this interface allows to divide the application's logic from the UI activity
 * <p>
 * Subscription of Event related Listeners works following the Observer Design pattern
 * https://refactoring.guru/design-patterns/observer
 */
public interface MapBehaviour<E extends Event> {

    /**
     * @param supportFragmentManager Needed to interact with the user through dialogs
     */
    void setSupportFragmentManager(@NonNull FragmentManager supportFragmentManager);

    /**
     * @param locationRetriever An object that implements the LocationRetriever interface
     */
    void setLocationRetriever(@NonNull LocationRetriever locationRetriever);

    /**
     * @param dialog An object of a class that extends AbstractAddEventDialog
     */
    void setAddEventDialog(@NonNull AbstractAddEventDialog dialog);

    /**
     * @param dialog An object of a class that extends AbstractRemoveEventDialog
     */
    void setRemoveEventDialog(@NonNull AbstractRemoveEventDialog dialog);

    /**
     * @param bottomSheetBehaviour An object of a class that extends BottomSheetBehaviour
     */
    void setBottomSheetBehaviour(@NonNull BottomSheetBehaviour bottomSheetBehaviour);

    /**
     * @param view A view clickable object that will be used by a MapBehaviour object to receive the command
     *             for opening the device's google maps application at the current location on the maps
     */
    void setGoToNavigatorView(@NonNull View view);

    /**
     * @param googleMapsAccess An instance of an object that can open Google Maps application at a given Location
     */
    void setGoogleMapsAccess(@NonNull GoToGoogleMapsNavigator googleMapsAccess);

    /**
     * @param listener An object that wait for the creation of an event
     */
    void subscribeOnEventCreatedListener(OnEventCreatedListener<E> listener);

    /**
     * @param listener An object that wait for the creation of an event
     */
    void unsubscribeOnEventCreatedListener(OnEventCreatedListener<E> listener);

    /**
     * @param listener An object that wait for the removal of an event
     */
    void subscribeOnEventRemovedListener(OnEventRemovedListener<E> listener);

    /**
     * @param listener An object that wait for the removal of an event
     */
    void unsubscribeOnEventRemovedListener(OnEventRemovedListener<E> listener);

    /**
     * @param listener An object that wait for the trigger of an event
     */
    void subscribeOnEventTriggeredListener(OnEventTriggeredListener<E> listener);

    /**
     * @param listener An object that wait for the trigger of an event
     */
    void unsubscribeOnEventTriggeredListener(OnEventTriggeredListener<E> listener);

    /**
     * Operations on the map can be performed only once the map has been initialized, so this listener
     * will be notified when this happens
     *
     * @param onMapInitializedListener A listener that will be called when the map has been fully initialized
     */
    void setOnMapInitializedListener(OnMapInitializedListener onMapInitializedListener);

    /**
     * This method allows you to define a visible region on the map, to signal to the map that portions
     * of the map around the edges may be obscured, by setting padding on each of the four edges of the map.
     *
     * @param left   The number of pixels of padding to be added on the left of the map. (>= 0)
     * @param top    The number of pixels of padding to be added on the top of the map. (>= 0)
     * @param right  The number of pixels of padding to be added on the right of the map. (>= 0)
     * @param bottom The number of pixels of padding to be added on the bottom of the map. (>= 0)
     */
    void setMapPadding(int left, int top, int right, int bottom);

    /**
     * @param events A bunch of events to position in the map (both description and Position must be defined)
     */
    void addEventsToMap(List<E> events);

    /**
     * @param data The gps position where the map will be focused
     */
    void moveMap(LatLng data);
}
