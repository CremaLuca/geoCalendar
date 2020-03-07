package com.eis.geoCalendar.demo.Behaviour;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.eis.geoCalendar.demo.Behaviour.listener.OnEventCreatedListener;
import com.eis.geoCalendar.demo.Behaviour.listener.OnEventRemovedListener;
import com.eis.geoCalendar.demo.Behaviour.listener.OnEventTriggeredListener;
import com.eis.geoCalendar.demo.Behaviour.listener.RemoveEventListener;
import com.eis.geoCalendar.demo.Behaviour.listener.ResultEventListener;
import com.eis.geoCalendar.demo.Dialogs.AbstractAddEventDialog;
import com.eis.geoCalendar.demo.Dialogs.AbstractRemoveEventDialog;
import com.eis.geoCalendar.demo.Localization.GoToGoogleMapsNavigator;
import com.eis.geoCalendar.demo.Localization.OnLocationAvailableListener;
import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.network.SMS.SMSNetworkEventUser;
import com.eis.smslibrary.SMSPeer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NetworkEventMapBehaviourTest implements OnEventCreatedListener, OnEventRemovedListener, OnEventTriggeredListener,
        ResultEventListener, RemoveEventListener {
    private NetworkEventMapBehaviour networkEventMapBehaviour;
    private TestLocationManager locationManager;
    private String result;
    private LatLng testPosition = new LatLng(3, 7);
    private LatLng testResultPosition;
    private String resultDescription;
    private Marker resMarker;
    private TestAddEventDialog testAddEventDialog;
    private TestRemoveEventDialog testRemoveEventDialog;
    private TestLocationManager testLocationManager;

    @Before
    public void setUp() throws Exception {
        SMSPeer myself = new SMSPeer("+390425667888");
        locationManager = new TestLocationManager();

        networkEventMapBehaviour = new NetworkEventMapBehaviour(new SMSNetworkEventUser(myself));

        testRemoveEventDialog = new TestRemoveEventDialog();
        networkEventMapBehaviour.setRemoveEventDialog(testRemoveEventDialog);

        networkEventMapBehaviour.setLocationRetriever(locationManager);

        // This activity is subscribed as an Observer

        networkEventMapBehaviour.subscribeOnEventCreatedListener(this);
        networkEventMapBehaviour.subscribeOnEventRemovedListener(this);
        networkEventMapBehaviour.subscribeOnEventTriggeredListener(this);

    }

    @Test
    public void onMapLongClick_Test() {
        result = "result";
        testAddEventDialog = new TestAddEventDialog(result);
        testAddEventDialog.setResultListener(this);
        networkEventMapBehaviour.setAddEventDialog(testAddEventDialog);

        networkEventMapBehaviour.onMapLongClick(testPosition);


    }

    @Test
    @Override
    public void onEventReturn(LatLng pos, String description) {
        testResultPosition = pos;
        resultDescription = description;
        assertEquals(testPosition, testResultPosition);
        assertEquals(result, resultDescription);
    }

    @Test
    public void callRemoveEventDialog_Test() {
        Marker marker = null; //can't create marker
        testRemoveEventDialog = new TestRemoveEventDialog();
        testRemoveEventDialog.setMarker(marker);
        testRemoveEventDialog.setRemoveEventListener(this);

        networkEventMapBehaviour.setRemoveEventDialog(testRemoveEventDialog);
        networkEventMapBehaviour.onInfoWindowLongClick(marker);
    }

    @Test
    @Override
    public void removeMark(Marker marker) {
        assertEquals(marker, resMarker);
    }

    @Override
    public void onEventCreated(Event newEvent) {

    }

    @Override
    public void onEventRemoved(Event removedEvent) {

    }

    @Override
    public void onEventTriggered(Event event) {

    }

    @After
    public void tearDown() throws Exception {
    }

    public class TestAddEventDialog extends AbstractAddEventDialog {
        LatLng pos;
        ResultEventListener listener;

        public String resultDescription;

        public TestAddEventDialog(String resultDescription) {
            this.resultDescription = resultDescription;
        }

        @Override
        public void setEventPosition(LatLng latLng) {
            pos = latLng;
        }

        @Override
        public void setResultListener(ResultEventListener resultEventListener) {
            listener = resultEventListener;
        }

        @Override
        public void show(@NonNull FragmentManager manager, @Nullable String tag) {
            listener.onEventReturn(pos, resultDescription);
        }
    }

    public class TestRemoveEventDialog extends AbstractRemoveEventDialog {
        private Marker myMarker;
        private RemoveEventListener removeEventListener;

        @Override
        public void setMarker(Marker marker) {
            myMarker = marker;
        }

        @Override
        public void setRemoveEventListener(RemoveEventListener removeEventListener) {
            this.removeEventListener = removeEventListener;
        }

        @Override
        public void show(@NonNull FragmentManager manager, @Nullable String tag) {
            removeEventListener.removeMark(myMarker);
        }
    }

    public class TestLocationManager implements LocationRetriever, OnCompleteListener<Location>, GoToGoogleMapsNavigator {
        private OnLocationAvailableListener onLocationAvailableListener;

        public final LatLng POSITION = new LatLng(1, 1);

        @Override
        public void getCurrentLocation() {
            onComplete(null);
        }

        @Override
        public void setOnLocationAvailableListener(OnLocationAvailableListener onLocationAvailableListener) {
            this.onLocationAvailableListener = onLocationAvailableListener;
        }

        @Override
        public void onComplete(@NonNull Task<Location> task) {
            onLocationAvailableListener.onLocationAvailable(POSITION);
        }

        @Override
        public void open(@NonNull LatLng position) {

        }
    }
}