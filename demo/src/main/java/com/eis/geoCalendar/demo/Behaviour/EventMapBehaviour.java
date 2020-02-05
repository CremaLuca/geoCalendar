package com.eis.geoCalendar.demo.Behaviour;

import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.eis.geoCalendar.app.GenericEvent;
import com.eis.geoCalendar.demo.CreateLocatedEventDialogFragment;
import com.eis.geoCalendar.demo.Localization.Command;
import com.eis.geoCalendar.demo.Behaviour.MapBehaviour;
import com.eis.geoCalendar.demo.MoveMapCommand;
import com.eis.geoCalendar.demo.RemoveLocatedEventDialog;
import com.eis.geoCalendar.gps.GPSPosition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class EventMapBehaviour implements MapBehaviour {
    private GoogleMap mMap;
    private FragmentManager supportFragmentManager;
    private Context appContext;
    private LocationRetriever locationRetriever;

    public EventMapBehaviour(@NonNull Context context) {
        appContext = context;
    }

    @Override
    public void getSupportFragmentManager(@NonNull FragmentManager supportFragmentManager) {
        this.supportFragmentManager = supportFragmentManager;
    }

    @Override
    public void getLocationRetriever(@NonNull LocationRetriever locationRetriever) {
        this.locationRetriever = locationRetriever;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
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
        Command<Location> moveMapCommand = new MoveMapCommand(mMap);
        locationRetriever.getCurretnLocation();
    }


    /**
     * Called when user clicks (taps) on the map
     * Collects the position associated and opens Dialog for creating event
     *
     * @param latLng The position on the map where the user clicked
     */
    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(getApplicationContext(), "Clicked at " + latLng.toString(), Toast.LENGTH_SHORT).show();

        CreateLocatedEventDialogFragment createEventDialog = new CreateLocatedEventDialogFragment();
        createEventDialog.show(getSupportFragmentManager(), CREATE_EVENT_DIALOG_TAG);
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
        GenericEvent<String> event = new GenericEvent<>(eventPos, description);
        //eventManager.addEvent(event);
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
        removeLocatedEventDialog.show(getSupportFragmentManager(), REMOVE_EVENT_DIALOG_TAG);
    }

    /**
     * Removes the Located Event mark from the map
     *
     * @param marker The marker to be removed
     */
    @Override
    public void removeMark(Marker marker) {
        marker.remove();
    }

}
