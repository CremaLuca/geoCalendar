package com.eis.geoCalendar.demo.Behaviour;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.eis.geoCalendar.demo.Bottomsheet.AbstractMapEventBottomSheetBehaviour;
import com.eis.geoCalendar.demo.Bottomsheet.OnActionViewClickListener;
import com.eis.geoCalendar.demo.Bottomsheet.OnRemoveViewClickListener;
import com.eis.geoCalendar.demo.Dialogs.AbstractAddEventDialog;
import com.eis.geoCalendar.demo.Dialogs.AbstractRemoveEventDialog;
import com.eis.geoCalendar.demo.Localization.GoToGoogleMapsNavigator;
import com.eis.geoCalendar.demo.Localization.OnLocationAvailableListener;
import com.eis.geoCalendar.events.Event;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * @author Turcato
 *
 * This interface can be used to implement the variouse GoogleMap's events, without writing all interfaces' names
 *
 * Defining a class that implements this interface allows to divide the application's logic from the UI activity
 *
 * Subscription of Event related Listeners works follwing the Observer Design pattern
 *
 */
public interface MapBehaviour<E extends Event> extends OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnInfoWindowLongClickListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener,
        ResultEventListener, RemoveEventListener, OnLocationAvailableListener,
        OnActionViewClickListener, OnRemoveViewClickListener, View.OnClickListener {

    /**
     *
     * @param supportFragmentManager    Needed to interact with the user through dialogs
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
     * @param abstractMapEventBottomSheetBehaviour An object of a class that extends AbstractMapEventBottomSheetBehaviour
     */
    void setBottomSheetBehaviour(@NonNull AbstractMapEventBottomSheetBehaviour abstractMapEventBottomSheetBehaviour);

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

}
