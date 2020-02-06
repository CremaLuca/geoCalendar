package com.eis.geoCalendar.demo.Behaviour;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.eis.geoCalendar.app.GenericEvent;
import com.eis.geoCalendar.demo.CreateLocatedEventDialogFragment;
import com.eis.geoCalendar.demo.RemoveLocatedEventDialog;
import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.events.EventManager;
import com.eis.geoCalendar.gps.GPSPosition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Class created to divide the code that manages the logic of interaction with the user from the
 * activities, fragments, ecc.. code
 * <p>
 * All that is needed to use this class is just to create a SupportMapFragment from a map activity
 * anc call getMapAsync(eventMapBehaviour)
 *
 * @param <E> Type of event
 */
public class EventMapBehaviour<E extends Event<String>> implements MapBehaviour {
    private GoogleMap mMap;
    private FragmentManager supportFragmentManager;
    private Context appContext;
    private LocationRetriever locationRetriever;
    private EventManager<Event<String>> eventManager;
    private ArrayList<Event<String>> currentEvents;

    private static final String CREATE_EVENT_DIALOG_TAG = "CREATE_EVENT_DIALOG_TAG";
    private static final String REMOVE_EVENT_DIALOG_TAG = "REMOVE_EVENT_DIALOG_TAG";

    /**
     * Constructor that creates a fully operative EventMapBehaviour object
     *
     * @param context      Application's Context
     * @param eventManager An instance of an object that implements EventManager
     */
    public EventMapBehaviour(@NonNull Context context, @NonNull EventManager<Event<String>> eventManager) {
        this.eventManager = eventManager;
        this.appContext = context;
    }

    /**
     * Constructor that creates an EventMapBehaviour object without event managing (only UI and map)
     *
     * @param context Application's Context
     */
    public EventMapBehaviour(@NonNull Context context) {
        this.appContext = context;
    }

    /**
     * @param eventManager An instance of an object that implements EventManager
     */
    public void setEventManager(@NonNull EventManager<Event<String>> eventManager) {
        this.eventManager = eventManager;
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
        //Collect all events from EventManager

        //set onClick listener
        mMap.setOnMapLongClickListener(this);           //To add Events
        mMap.setOnInfoWindowLongClickListener(this);    //To delete Events

        //Retrieve current position
        locationRetriever.setOnLocationAvailableListener(this);
        locationRetriever.getCurrentLocation();

        if (eventManager != null) {
            currentEvents = eventManager.getAllEvents();
            addEventsToMap(currentEvents);
        }
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
     * @param data The gps position where the map will be focused
     */
    private void moveMap(LatLng data) {
        mMap.moveCamera(CameraUpdateFactory.zoomTo(mMap.getMaxZoomLevel()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(data));
    }

    /**
     * @param events A bunch of events to position in the map (both description and Position must be defined)
     */
    private void addEventsToMap(ArrayList<Event<String>> events) {
        for (Event<String> event : events) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(event.getPosition().getLatitude(),
                    event.getPosition().getLongitude()))
                    .title(event.getContent()));
        }
    }

    /**
     * Called when user clicks (taps) on the map
     * Collects the position associated and opens Dialog for creating event
     *
     * @param latLng The position on the map where the user clicked
     */
    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(appContext, "Clicked at " + latLng.toString(), Toast.LENGTH_SHORT).show();

        CreateLocatedEventDialogFragment createEventDialog = new CreateLocatedEventDialogFragment();
        createEventDialog.show(supportFragmentManager, CREATE_EVENT_DIALOG_TAG);
        createEventDialog.setResultActivity(this);
        createEventDialog.setEventPosition(latLng);
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
        Event<String> event = new GenericEvent<>(eventPos, description);
        if (eventManager != null)
            eventManager.addEvent(event);

        Marker created = mMap.addMarker(new MarkerOptions().position(pos).title(description)); //automatically cuts title if too long
        created.setTag(event);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
    }

    /**
     * Called when user long presses an InfoWindow of Marker
     * Opens a dialog that asks the user if the event has to be deleted
     *
     * @param marker The marker whose InfoWindow was pressed
     */
    @Override
    public void onInfoWindowLongClick(Marker marker) {
        RemoveLocatedEventDialog removeLocatedEventDialog = new RemoveLocatedEventDialog();
        removeLocatedEventDialog.setMarker(marker);
        removeLocatedEventDialog.setRemoveEventListener(this);
        removeLocatedEventDialog.show(supportFragmentManager, REMOVE_EVENT_DIALOG_TAG);
    }

    /**
     * Removes the Located Event mark from the map
     *
     * @param marker The marker to be removed
     */
    @Override
    public void removeMark(Marker marker) {
        marker.remove();
        GPSPosition position = new GPSPosition(marker.getPosition().latitude, marker.getPosition().longitude);
        Event<String> event = new GenericEvent<>(position, "");
        if (eventManager != null)
            eventManager.removeEvent(event);
    }

}
