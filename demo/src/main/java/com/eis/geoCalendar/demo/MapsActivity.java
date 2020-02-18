package com.eis.geoCalendar.demo;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.eis.geoCalendar.app.GenericEventManager;
import com.eis.geoCalendar.demo.Behaviour.EventMapBehaviour;
import com.eis.geoCalendar.demo.Bottomsheet.EventBottomSheetDialogFragment;
import com.eis.geoCalendar.demo.Dialogs.AddEventDialog;
import com.eis.geoCalendar.demo.Dialogs.RemoveEventDialog;
import com.eis.geoCalendar.demo.Localization.LocationManager;
import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.events.EventManager;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;


/**
 * @author Turcato
 * <p>
 * GeoCalendar has a main activity formed by a GoogleMap
 */
public class MapsActivity extends FragmentActivity {

    private EventManager<Event<String>> eventManager;

    private static final int APP_PERMISSION_REQUEST_CODE = 0;
    private LocationManager locationManager;
    private EventMapBehaviour eventMapBehaviour;
    private BottomSheetBehavior bottomSheetBehavior;

    LinearLayout layoutBottomSheet;


    private static final String MAPS_ACTIVITY_TAG = "MAPS_ACTIVITY_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        requestPermissions();

        //Creating the event manager
        eventManager = new GenericEventManager<>(null);
        locationManager = new LocationManager(getApplicationContext());

        //Creating the "Behaviour Manager"
        eventMapBehaviour = new EventMapBehaviour<>(getApplicationContext());
        eventMapBehaviour.setAddEventDialog(new AddEventDialog());
        eventMapBehaviour.setRemoveEventDialog(new RemoveEventDialog());
        eventMapBehaviour.setLocationRetriever(locationManager);
        eventMapBehaviour.setSupportFragmentManager(getSupportFragmentManager());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //My job here is done
        mapFragment.getMapAsync(eventMapBehaviour);

        EventBottomSheetDialogFragment eventBottomSheetDialogFragment = new EventBottomSheetDialogFragment();



    }

    /***
     * @author Turcato
     * Requests Android permissions if not granted
     */
    public void requestPermissions() {
        ActivityCompat.requestPermissions(this, LocationManager.getPermissions(), APP_PERMISSION_REQUEST_CODE);
    }

}
