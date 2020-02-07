package com.eis.geoCalendar.demo;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.eis.geoCalendar.app.GenericEventManager;
import com.eis.geoCalendar.demo.Behaviour.EventMapBehaviour;
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

        try {


            // get the bottom sheet view
            ConstraintLayout bottomSheetLayout = findViewById(R.id.bottom_sheet);
            // init the bottom sheet behavior
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);

            if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        } catch (Exception e) {

        }
        /*
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:

                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:


                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:

                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:

                        break;
                    case BottomSheetBehavior.STATE_SETTLING:

                        break;
                }
                Log.d(MAPS_ACTIVITY_TAG, "onStateChanged: " + newState);
            }



            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        */


    }

    /***
     * @author Turcato
     * Requests Android permissions if not granted
     */
    public void requestPermissions() {
        ActivityCompat.requestPermissions(this, LocationManager.getPermissions(), APP_PERMISSION_REQUEST_CODE);
    }

}
