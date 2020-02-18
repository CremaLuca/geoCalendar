package com.eis.geoCalendar.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.eis.geoCalendar.demo.Behaviour.EventMapBehaviour;
import com.eis.geoCalendar.demo.Bottomsheet.MapEventBottomSheetBehaviour;
import com.eis.geoCalendar.demo.Dialogs.AddEventDialog;
import com.eis.geoCalendar.demo.Dialogs.RemoveEventDialog;
import com.eis.geoCalendar.demo.Localization.LocationManager;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MainActivity extends AppCompatActivity {

    BottomSheetBehavior sheetBehavior;
    LinearLayout layoutBottomSheet;
    LocationManager locationManager;
    EventMapBehaviour eventMapBehaviour;
    private final static int APP_PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions();

        //  layoutBottomSheet = findViewById(R.id.bottom_sheet);
        //  sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        View goToNaivgatorButton = findViewById(R.id.go_to_navigator_imageButton);


        RelativeLayout bottomSheetLayout = findViewById(R.id.relative_layout_bottom_sheet);
        //FIXME bottomSheetLayout.getHeight() gets 0
        Log.d("Sheet height", "Activity Sheet height " + bottomSheetLayout.getHeight());

        MapEventBottomSheetBehaviour bottomSheetBehavior = MapEventBottomSheetBehaviour.from(bottomSheetLayout, 220);

        Button bottomSheetActionButton = findViewById(R.id.bottom_sheet_action_button);
        Button bottomSheetRemoveButton = findViewById(R.id.bottom_sheet_remove_button);
        TextView textView = findViewById(R.id.bottom_sheet_textView);
        bottomSheetBehavior.setActionView(bottomSheetActionButton);
        bottomSheetBehavior.setRemoveView(bottomSheetRemoveButton);
        bottomSheetBehavior.setTextView(textView);


        locationManager = new LocationManager(getApplicationContext());

        //Creating the "Behaviour Manager"
        eventMapBehaviour = new EventMapBehaviour<>(getApplicationContext());
        eventMapBehaviour.setAddEventDialog(new AddEventDialog());
        eventMapBehaviour.setRemoveEventDialog(new RemoveEventDialog());
        eventMapBehaviour.setLocationRetriever(locationManager);
        eventMapBehaviour.setSupportFragmentManager(getSupportFragmentManager());
        eventMapBehaviour.setBottomSheetBehaviour(bottomSheetBehavior);
        eventMapBehaviour.setGoToNavigatorView(goToNaivgatorButton);
        eventMapBehaviour.setGoogleMapsAccess(locationManager);


        //My job here is done
        mapFragment.getMapAsync(eventMapBehaviour);


        View mapView = findViewById(R.id.mapView);


    }

    /***
     * @author Turcato
     * Requests Android permissions if not granted
     */
    public void requestPermissions() {
        ActivityCompat.requestPermissions(this, LocationManager.getPermissions(), APP_PERMISSION_REQUEST_CODE);
    }

}
