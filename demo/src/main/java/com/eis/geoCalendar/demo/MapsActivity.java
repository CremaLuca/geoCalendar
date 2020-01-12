package com.eis.geoCalendar.demo;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.eis.geoCalendar.app.GenericEventManager;
import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.events.EventManager;
import com.eis.geoCalendar.gps.GPSPosition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * @author Turcato
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnMapLongClickListener, ResultEventListener {

    private GoogleMap mMap;
    private EventManager<Event<String>> eventManager;
    private static final String CREATE_EVENT_DIALOG_TAG = "createEventDialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Creating the event manager
        eventManager = new GenericEventManager<>(null);

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
        mMap.setOnMapLongClickListener(this);
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

        //eventManager.addEvent(new GenericEvent<String>(eventPos, description));

        mMap.addMarker(new MarkerOptions().position(pos).title(description)); //automatically cuts if too long
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
    }
}
