package com.eis.geoCalendar.demo;

import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.eis.geoCalendar.app.GenericEventManager;
import com.eis.geoCalendar.demo.Behaviour.EventMapBehaviour;
import com.eis.geoCalendar.demo.Localization.LocationManager;
import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.events.EventManager;
import com.google.android.gms.maps.SupportMapFragment;



/**
 * @author Turcato
 */
public class MapsActivity extends FragmentActivity {

    private EventManager<Event<String>> eventManager;


    private static final String CREATE_EVENT_DIALOG_TAG = "createEventDialog";
    private static final String REMOVE_EVENT_DIALOG_TAG = "removeEventDialog";
    private static final int APP_PERMISSION_REQUEST_CODE = 0;
    private LocationManager locationManager;
    private EventMapBehaviour eventMapBehaviour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Creating the event manager
        eventManager = new GenericEventManager<>(null);
        locationManager = new LocationManager(getApplicationContext());
        eventMapBehaviour = new EventMapBehaviour<>(getApplicationContext());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //My job here is done
        mapFragment.getMapAsync(eventMapBehaviour);

        requestPermissions();
    }

    /***
     * @author Turcato
     * Requests Android permissions if not granted
     */
    public void requestPermissions() {
        ActivityCompat.requestPermissions(this, LocationManager.getPermissions(), APP_PERMISSION_REQUEST_CODE);
    }

}
