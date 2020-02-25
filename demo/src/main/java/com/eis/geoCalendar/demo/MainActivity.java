package com.eis.geoCalendar.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.eis.geoCalendar.app.network.GenericNetworkEvent;
import com.eis.geoCalendar.demo.Behaviour.EventMapBehaviour;
import com.eis.geoCalendar.demo.Behaviour.NetworkEventMapBehaviour;
import com.eis.geoCalendar.demo.Behaviour.OnEventCreatedListener;
import com.eis.geoCalendar.demo.Behaviour.OnEventRemovedListener;
import com.eis.geoCalendar.demo.Behaviour.OnEventTriggeredListener;
import com.eis.geoCalendar.demo.Behaviour.OnMapInitializedListener;
import com.eis.geoCalendar.demo.Bottomsheet.MapEventBottomSheetBehaviour;
import com.eis.geoCalendar.demo.Dialogs.AddEventDialog;
import com.eis.geoCalendar.demo.Dialogs.RemoveEventDialog;
import com.eis.geoCalendar.demo.Localization.LocationManager;
import com.eis.geoCalendar.demo.Resources.SMSNetworkEventUser;
import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.NetworkEvent;
import com.eis.geoCalendar.network.NetworkEventUser;
import com.eis.smslibrary.SMSPeer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnEventCreatedListener, OnEventRemovedListener,
        OnEventTriggeredListener, OnMapInitializedListener {

    BottomSheetBehavior sheetBehavior;
    LinearLayout layoutBottomSheet;
    LocationManager locationManager;
    EventMapBehaviour eventMapBehaviour;
    NetworkEventMapBehaviour networkEventMapBehaviour;

    private final static int APP_PERMISSION_REQUEST_CODE = 1;
    private static int i = 0;

    List<NetworkEventUser> users;
    List<NetworkEvent> events;

    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.main_layout);

        requestPermissions();

        //  layoutBottomSheet = findViewById(R.id.bottom_sheet);
        //  sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        View goToNavigatorButton = findViewById(R.id.go_to_navigator_imageButton);


        RelativeLayout bottomSheetLayout = findViewById(R.id.relative_layout_bottom_sheet);

        MapEventBottomSheetBehaviour bottomSheetBehavior = MapEventBottomSheetBehaviour.from(bottomSheetLayout, 220);

        Button bottomSheetActionButton = findViewById(R.id.bottom_sheet_action_button);
        Button bottomSheetRemoveButton = findViewById(R.id.bottom_sheet_remove_button);
        TextView textView = findViewById(R.id.bottom_sheet_textView);
        bottomSheetBehavior.setActionView(bottomSheetActionButton);
        bottomSheetBehavior.setRemoveView(bottomSheetRemoveButton);
        bottomSheetBehavior.setTextView(textView);


        locationManager = new LocationManager(getApplicationContext());


/*
        //Creating the "Behaviour Manager"
        eventMapBehaviour = new EventMapBehaviour<>();
        eventMapBehaviour.setAddEventDialog(new AddEventDialog());
        eventMapBehaviour.setRemoveEventDialog(new RemoveEventDialog());
        eventMapBehaviour.setLocationRetriever(locationManager);
        eventMapBehaviour.setSupportFragmentManager(getSupportFragmentManager());
        eventMapBehaviour.setBottomSheetBehaviour(bottomSheetBehavior);
        eventMapBehaviour.setGoToNavigatorView(goToNavigatorButton);
        eventMapBehaviour.setGoogleMapsAccess(locationManager);

        // This activity is subscribed as an Observer

        eventMapBehaviour.subscribeOnEventCreatedListener(this);
        eventMapBehaviour.subscribeOnEventRemovedListener(this);
        eventMapBehaviour.subscribeOnEventTriggeredListener(this);
*/

        SMSPeer myself = new SMSPeer("+390425667888");
        BitmapFactory.Options userIconOptions = new BitmapFactory.Options();

        Bitmap userIcon = BitmapFactory.decodeResource(getResources(), R.drawable.user_marker_small, userIconOptions);


        networkEventMapBehaviour = new NetworkEventMapBehaviour(new SMSNetworkEventUser(myself), userIcon);
        networkEventMapBehaviour.setAddEventDialog(new AddEventDialog());
        networkEventMapBehaviour.setRemoveEventDialog(new RemoveEventDialog());
        networkEventMapBehaviour.setLocationRetriever(locationManager);
        networkEventMapBehaviour.setSupportFragmentManager(getSupportFragmentManager());
        networkEventMapBehaviour.setBottomSheetBehaviour(bottomSheetBehavior);
        networkEventMapBehaviour.setGoToNavigatorView(goToNavigatorButton);
        networkEventMapBehaviour.setGoogleMapsAccess(locationManager);

        // This activity is subscribed as an Observer

        networkEventMapBehaviour.subscribeOnEventCreatedListener(this);
        networkEventMapBehaviour.subscribeOnEventRemovedListener(this);
        networkEventMapBehaviour.subscribeOnEventTriggeredListener(this);
        networkEventMapBehaviour.setOnMapInitializedListener(this);

        networkEventMapBehaviour.allowMapRemovalNetworkEvents(false);

        //My job here is done
        //mapFragment.getMapAsync(eventMapBehaviour);
        mapFragment.getMapAsync(networkEventMapBehaviour);

        View mapView = findViewById(R.id.mapView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout mDrawerLayout = findViewById(R.id.main_layout);
        if (mDrawerLayout != null) {
            ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_opened, R.string.drawer_closed);
            mActionBarDrawerToggle.setDrawerArrowDrawable(new DrawerArrowDrawable(getApplicationContext()));
            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            mActionBarDrawerToggle.setDrawerSlideAnimationEnabled(true);
        }

        /*
         * This code is to open the device's address boook whem clicking on menu item
         * @author Alessandra Tonin
         */
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.addressBook_item:
                        Intent i = new Intent(MainActivity.this, ContactsActivity.class);
                        startActivity(i);
                        break;
                    case R.id.settings_item:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                }
                return false;
            }
        });
    }

    private void addEvents() {
        users = new ArrayList<>();
        users.add(new SMSNetworkEventUser(new SMSPeer("+390425667111")));
        users.add(new SMSNetworkEventUser(new SMSPeer("+390425622112")));
        users.add(new SMSNetworkEventUser(new SMSPeer("+390425422112")));
        users.add(new SMSNetworkEventUser(new SMSPeer("+390422222112")));
        users.add(new SMSNetworkEventUser(new SMSPeer("+390422222130")));

        events = new ArrayList<>();
        events.add(new GenericNetworkEvent<String, NetworkEventUser>(
                new GPSPosition(39.31, -121.68), "A", users.get(0)));
        events.add(new GenericNetworkEvent<String, NetworkEventUser>(
                new GPSPosition(40, -122), "B", users.get(1)));
        events.add(new GenericNetworkEvent<String, NetworkEventUser>(
                new GPSPosition(41, -122), "C", users.get(2)));
        events.add(new GenericNetworkEvent<String, NetworkEventUser>(
                new GPSPosition(42, -124), "D", users.get(3)));
        events.add(new GenericNetworkEvent<String, NetworkEventUser>(
                new GPSPosition(41, -124), "E", users.get(4)));

        networkEventMapBehaviour.addEventsToMap(users, events);
    }

    /**
     * This activity can listen to event-related actions occurred on the map
     */

    @Override
    public void onEventCreated(Event newEvent) {
        if (i == 0)
            addEvents();
        else if (i == 1) {
            List<NetworkEvent> userEvents = new ArrayList<>();
            userEvents.add(new GenericNetworkEvent<String, NetworkEventUser>(
                    new GPSPosition(38, -121.68), "A", users.get(0)));
            userEvents.add(new GenericNetworkEvent<String, NetworkEventUser>(
                    new GPSPosition(37, -121.68), "A", users.get(0)));
            userEvents.add(new GenericNetworkEvent<String, NetworkEventUser>(
                    new GPSPosition(36, -121.68), "A", users.get(0)));

            networkEventMapBehaviour.addEventsToMap(users.get(0), userEvents);

        }
        i++;


    }

    @Override
    public void onEventRemoved(Event removedEvent) {

    }

    @Override
    public void onEventTriggered(Event event) {

    }

    /***
     * @author Turcato
     * Requests Android permissions if not granted
     */
    public void requestPermissions() {
        ActivityCompat.requestPermissions(this, LocationManager.getPermissions(), APP_PERMISSION_REQUEST_CODE);
    }

    /**
     * Called when the map is fully initialized and prepared by the Behaviour manager
     */
    @Override
    public void onMapInitialized() {
        //TODO retrieve correct height of the toolbar
        networkEventMapBehaviour.setMapPadding(0, 130, 0, 0);
    }

}
