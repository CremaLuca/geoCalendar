package com.eis.geoCalendar.demo.Behaviour;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.eis.geoCalendar.app.GenericEvent;
import com.eis.geoCalendar.demo.Bottomsheet.AbstractMapEventBottomSheetBehaviour;
import com.eis.geoCalendar.demo.Dialogs.AbstractAddEventDialog;
import com.eis.geoCalendar.demo.Dialogs.AbstractRemoveEventDialog;
import com.eis.geoCalendar.demo.Localization.GoToGoogleMapsNavigator;
import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.gps.GPSPosition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;

/**
 * Class created to manage Events on a GoogleMap and divide the code that manages the logic of
 * interaction with the user from the activities, fragments, ecc.. code
 * <p>
 * All that is needed to use this class is just to create a SupportMapFragment from a map activity
 * anc call getMapAsync(eventMapBehaviour)
 * Subscription of Event related Listeners works follwing the Observer Design pattern
 *
 * @param <E> Type of event
 */
public class EventMapBehaviour<E extends Event<String>> implements MapBehaviour {
    protected GoogleMap mMap;
    private FragmentManager supportFragmentManager;
    private Context appContext;
    private LocationRetriever locationRetriever;


    // private Map<Marker, Event<String>> currentEvents;
    private AbstractAddEventDialog addEventDialog;
    private AbstractRemoveEventDialog removeEventDialog;
    private AbstractMapEventBottomSheetBehaviour bottomSheetBehaviour;
    private View goToNavigatorView;
    private GoToGoogleMapsNavigator goToGoogleMapsNavigator;

    private List<OnEventCreatedListener> onEventCreatedListeners;
    private List<OnEventRemovedListener> onEventRemovedListeners;
    private List<OnEventTriggeredListener> onEventTriggeredListeners;
    private OnMapInitializedListener onMapInitializedListener;

    private Marker currentFocusMarker;

    private static final String CREATE_EVENT_DIALOG_TAG = "CREATE_EVENT_DIALOG_TAG";
    private static final String REMOVE_EVENT_DIALOG_TAG = "REMOVE_EVENT_DIALOG_TAG";
    private static final String EVENT_DESCRIPTION_BOTTOMSHEET_DIALOG_TAG = "EVENT_DESCRIPTION_BOTTOMSHEET_DIALOG_TAG";

    /**
     * Constructor that creates a fully operative EventMapBehaviour object
     */
    public EventMapBehaviour() {
        //currentEvents = new ArrayMap<>();
    }

    /**
     * @param supportFragmentManager Needed to interact with the user through dialogs
     */
    @Override
    public void setSupportFragmentManager(@NonNull FragmentManager supportFragmentManager) {
        this.supportFragmentManager = supportFragmentManager;
    }

    /**
     * @param locationRetriever An instance of an object that implements locationRetriever to get the gps position
     */
    @Override
    public void setLocationRetriever(@NonNull LocationRetriever locationRetriever) {
        this.locationRetriever = locationRetriever;
        this.locationRetriever.setOnLocationAvailableListener(this);
    }

    /**
     * @param dialog An object of a class that extends AbstractAddEventDialog
     */
    @Override
    public void setAddEventDialog(@NonNull AbstractAddEventDialog dialog) {
        this.addEventDialog = dialog;
        this.addEventDialog.setResultListener(this);
    }

    /**
     * @param dialog An object of a class that extends AbstractRemoveEventDialog
     */
    @Override
    public void setRemoveEventDialog(@NonNull AbstractRemoveEventDialog dialog) {
        this.removeEventDialog = dialog;
        this.removeEventDialog.setRemoveEventListener(this);
    }

    /**
     * Note that for successfully open a google Maps instance a GoToGoogleMapsNavigator object must be set
     * This Method will set the view as INVISIBLE, it will turn visible when the user clicks on a Marker,
     * and turn invisible again when another part of the map will be clicked
     *
     * @param goToNavigatorView An instance of a View object that will be used by a MapBehaviour
     *                          object to receive the command for opening the device's google maps
     *                          application at the current location on the maps,
     */
    @Override
    public void setGoToNavigatorView(@NonNull View goToNavigatorView) {
        this.goToNavigatorView = goToNavigatorView;
        this.goToNavigatorView.setOnClickListener(this);
        this.goToNavigatorView.setVisibility(INVISIBLE);
    }

    /**
     * @param googleMapsAccess An instance of an object that can open Google Maps application at a given Location
     */
    @Override
    public void setGoogleMapsAccess(@NonNull GoToGoogleMapsNavigator googleMapsAccess) {
        this.goToGoogleMapsNavigator = googleMapsAccess;
    }

    /**
     * @param onMapInitializedListener A listener that will be called when the map has been fully initialized
     */
    public void setOnMapInitializedListener(OnMapInitializedListener onMapInitializedListener) {
        this.onMapInitializedListener = onMapInitializedListener;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * <p>
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setMapToolbarEnabled(true);

        //set onClick listener
        mMap.setOnMapLongClickListener(this);           //To add Events
        mMap.setOnInfoWindowLongClickListener(this);    //To delete Events
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

        //Retrieve current position

        locationRetriever.getCurrentLocation();

        if (onMapInitializedListener != null)
            onMapInitializedListener.onMapInitialized();
    }

    /**
     * @param position The current available Gps position
     */
    @Override
    public void onLocationAvailable(LatLng position) {
        moveMap(position);
    }

    /**
     * Moves the map's focus to the given position
     *
     * @throws NullPointerException if the method is called before map's initialization
     * @param data  The gps position where the map will be focused
     */
    public void moveMap(LatLng data) {
        mMap.moveCamera(CameraUpdateFactory.zoomTo(mMap.getMaxZoomLevel()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(data));
    }

    /**
     * Sets padding on the map.
     * This method allows you to define a visible region on the map, to signal to the map that portions
     * of the map around the edges may be obscured, by setting padding on each of the four edges of the map.
     * Map functions will be adapted to the padding. For example, the zoom controls, compass, copyright notices
     * and Google logo will be moved to fit inside the defined region, camera movements will be relative
     * to the center of the visible region, etc.
     *
     * @throws NullPointerException if the method is called before map's initialization
     * @param left      The number of pixels of padding to be added on the left of the map. (>= 0)
     * @param top       The number of pixels of padding to be added on the top of the map. (>= 0)
     * @param right     The number of pixels of padding to be added on the right of the map. (>= 0)
     * @param bottom    The number of pixels of padding to be added on the bottom of the map. (>= 0)
     */
    public void setMapPadding(int left, int top, int right, int bottom) {
        mMap.setPadding(left, top, right, bottom);
    }

    /**
     * These events are defined elsewhere, so no need to notify listeners
     *
     * @param events A bunch of events to position in the map (both description and Position must be defined)
     */
    public void addEventsToMap(List<E> events) {
        for (Event<String> event : events) {
            Marker created = mMap.addMarker(new MarkerOptions().position(new LatLng(event.getPosition().getLatitude(),
                    event.getPosition().getLongitude()))
                    .title(event.getContent()));
            //currentEvents.put(created, event);
            created.setTag(event);
        }
    }


    /**
     * Called when user clicks (taps) on the map for a prolonged time
     * Collects the position associated and opens Dialog for creating event
     *
     * @param latLng The position on the map where the user clicked
     */
    @Override
    public void onMapLongClick(LatLng latLng) {
        addEventDialog.setEventPosition(latLng);
        addEventDialog.show(supportFragmentManager, CREATE_EVENT_DIALOG_TAG);
    }

    /**
     * Moves the camera to the marker's location and, if set,
     * expands the bottomSheet to display Marker's event's description
     * If a goToNavigatorView was set, it will turn visible
     * Note: returning false for this event will fire the default event for Google Map
     *
     * @param marker The marker that caused this callback
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        currentFocusMarker = marker;
        moveMap(marker.getPosition());

        if (goToNavigatorView != null)
            goToNavigatorView.setVisibility(View.VISIBLE);

        if (bottomSheetBehaviour != null) {
            bottomSheetBehaviour.setDisplayedText(marker.getTitle());
            if (bottomSheetBehaviour.isShown())
                bottomSheetBehaviour.hide();
            else
                bottomSheetBehaviour.show();
            return true;
        }
        return false;
    }

    /**
     * If set, hides the Bottom Sheet
     * If set, hides the goToNavigatorView
     * Makes forget the previously set currentFocusMarker
     *
     * @param latLng The position on the map where the user clicked
     */
    @Override
    public void onMapClick(LatLng latLng) {
        if (bottomSheetBehaviour != null)
            bottomSheetBehaviour.hide();

        if (goToNavigatorView != null)
            goToNavigatorView.setVisibility(INVISIBLE);

        currentFocusMarker = null;
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
        Event event = new GenericEvent<>(eventPos, description);
        if (onEventCreatedListeners != null && !onEventCreatedListeners.isEmpty())
            for (OnEventCreatedListener listener : onEventCreatedListeners)
                listener.onEventCreated(event);

        Marker created = mMap.addMarker(new MarkerOptions().position(pos).title(description)); //automatically cuts title if too long
        created.setTag(event);
        moveMap(pos);
        //currentEvents.put(created, event);
    }

    /**
     * Called when user long presses an InfoWindow of Marker
     *
     * @param marker The marker whose InfoWindow was pressed
     */
    @Override
    public void onInfoWindowLongClick(Marker marker) {
        currentFocusMarker = marker;
        callRemoveEventDialog(marker);
    }

    /**
     * Opens a dialog that asks the user if the event has to be deleted
     * If the user accepts, the dialog will call RemoveMark method of this instance
     *
     * @param marker The marker that has been selected for confirming deletion
     */
    private void callRemoveEventDialog(Marker marker) {
        if (removeEventDialog != null) {
            removeEventDialog.setMarker(marker);
            removeEventDialog.show(supportFragmentManager, REMOVE_EVENT_DIALOG_TAG);
        }
    }

    /**
     * Called by the RemoveEventDialog or the bottom sheet if the user confirms to delete the event
     * Removes the Event mark from the map
     * Makes forget the previously set currentFocusMarker
     * Calls all the onEventRemovedListeners
     *
     * @param marker The marker to be removed
     */
    @Override
    public void removeMark(Marker marker) {
        currentFocusMarker = null;

        if (onEventRemovedListeners != null && !onEventRemovedListeners.isEmpty())
            for (OnEventRemovedListener listener : onEventRemovedListeners)
                listener.onEventRemoved((Event) marker.getTag());

        if (bottomSheetBehaviour != null)
            bottomSheetBehaviour.hide();
        if (goToNavigatorView != null)
            goToNavigatorView.setVisibility(INVISIBLE);
        marker.remove();
    }

    /**
     * @param abstractMapEventBottomSheetBehaviour An object of a class that extends AbstractMapEventBottomSheetBehaviour
     */
    @Override
    public void setBottomSheetBehaviour(@NonNull AbstractMapEventBottomSheetBehaviour abstractMapEventBottomSheetBehaviour) {
        this.bottomSheetBehaviour = abstractMapEventBottomSheetBehaviour;
        bottomSheetBehaviour.setOnActionViewClickListener(this);
        bottomSheetBehaviour.setOnRemoveViewClickListener(this);
    }

    /**
     * This is called from the action View of the BottomSheet
     *
     * @param v The View obj that caused this callback
     */
    @Override
    public void OnActionViewClick(View v) {
        //Note: This is where an action on the event can be performed
        // currentFocusMarker is the current focused marker (if not null)
        if (currentFocusMarker != null)
            if (onEventTriggeredListeners != null && !onEventTriggeredListeners.isEmpty())
                for (OnEventTriggeredListener listener : onEventTriggeredListeners)
                    listener.onEventTriggered((Event) currentFocusMarker.getTag());

        if (bottomSheetBehaviour != null)
            bottomSheetBehaviour.hide();
    }

    /**
     * This is called from the remove View of the BottomSheet, calls a Dialog to ask confirmation
     *
     * @param v The View obj that caused this callback
     */
    @Override
    public void OnRemoveViewClick(View v) {
        callRemoveEventDialog(currentFocusMarker);
    }

    /**
     * If a {@code GoToGoogleMapsNavigator} was set, this method will use it to open the google maps
     * application at the current map's central position
     *
     * @param v The View object that the user clicked to open the google maps application at the current location on the map
     */
    @Override
    public void onClick(View v) {
        if (goToGoogleMapsNavigator != null)
            goToGoogleMapsNavigator.open(mMap.getCameraPosition().target);
    }


    /**
     * IMPLEMENTATION OF THE OBSERVER DESIGN PATTERN FOR EVENT RELATED ACTIONS
     */

    @Override
    public void subscribeOnEventCreatedListener(@NonNull OnEventCreatedListener listener) {
        if (onEventCreatedListeners == null)
            onEventCreatedListeners = new ArrayList<>();
        onEventCreatedListeners.add(listener);
    }

    @Override
    public void subscribeOnEventRemovedListener(OnEventRemovedListener listener) {
        if (onEventRemovedListeners == null)
            onEventRemovedListeners = new ArrayList<>();
        onEventRemovedListeners.add(listener);
    }

    @Override
    public void subscribeOnEventTriggeredListener(OnEventTriggeredListener listener) {
        if (onEventTriggeredListeners == null)
            onEventTriggeredListeners = new ArrayList<>();
        onEventTriggeredListeners.add(listener);
    }

    @Override
    public void unsubscribeOnEventCreatedListener(OnEventCreatedListener listener) {
        if (onEventCreatedListeners != null)
            onEventCreatedListeners.remove(listener);
    }

    @Override
    public void unsubscribeOnEventRemovedListener(OnEventRemovedListener listener) {
        if (onEventRemovedListeners != null)
            onEventRemovedListeners.remove(listener);
    }

    @Override
    public void unsubscribeOnEventTriggeredListener(OnEventTriggeredListener listener) {
        if (onEventTriggeredListeners != null)
            onEventTriggeredListeners.remove(listener);
    }
}