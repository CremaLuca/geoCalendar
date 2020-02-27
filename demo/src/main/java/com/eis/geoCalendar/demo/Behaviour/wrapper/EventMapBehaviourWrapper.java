package com.eis.geoCalendar.demo.Behaviour.wrapper;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.eis.geoCalendar.demo.Behaviour.EventMapBehaviour;
import com.eis.geoCalendar.demo.Behaviour.LocationRetriever;
import com.eis.geoCalendar.demo.Behaviour.MapBehaviour;
import com.eis.geoCalendar.demo.Behaviour.listener.OnEventCreatedListener;
import com.eis.geoCalendar.demo.Behaviour.listener.OnEventRemovedListener;
import com.eis.geoCalendar.demo.Behaviour.listener.OnEventTriggeredListener;
import com.eis.geoCalendar.demo.Behaviour.listener.OnMapInitializedListener;
import com.eis.geoCalendar.demo.Bottomsheet.BottomSheetBehaviour;
import com.eis.geoCalendar.demo.Dialogs.AbstractAddEventDialog;
import com.eis.geoCalendar.demo.Dialogs.AbstractRemoveEventDialog;
import com.eis.geoCalendar.demo.Localization.GoToGoogleMapsNavigator;
import com.eis.geoCalendar.events.Event;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Class that wraps public methods for {@link com.eis.geoCalendar.demo.Behaviour.EventMapBehaviour}
 * following interface {@link com.eis.geoCalendar.demo.Behaviour.MapBehaviour}
 * <p>
 * If needed, the class {@link com.eis.geoCalendar.demo.Behaviour.EventMapBehaviour} can be set
 * to package-private and this wrapper can be used outside of the package
 */
public class EventMapBehaviourWrapper implements MapBehaviour<Event<String>> {
    private EventMapBehaviour instance;

    /**
     * Builds a fully operative @link com.eis.geoCalendar.demo.Behaviour.wrapper.EventMapBehaviourWrapper} obj
     */
    public EventMapBehaviourWrapper() {
        instance = new EventMapBehaviour();
    }

    /**
     * @param supportFragmentManager Needed to interact with the user through dialogs
     */
    @Override
    public void setSupportFragmentManager(@NonNull FragmentManager supportFragmentManager) {
        instance.setSupportFragmentManager(supportFragmentManager);
    }

    /**
     * @param dialog An object of a class that extends AbstractRemoveEventDialog
     */
    @Override
    public void setRemoveEventDialog(@NonNull AbstractRemoveEventDialog dialog) {
        instance.setRemoveEventDialog(dialog);
    }

    /**
     * @param locationRetriever An object that implements the LocationRetriever interface
     */
    @Override
    public void setLocationRetriever(@NonNull LocationRetriever locationRetriever) {
        instance.setLocationRetriever(locationRetriever);
    }

    /**
     * @param view A view clickable object that will be used by a MapBehaviour object to receive the command
     */
    @Override
    public void setGoToNavigatorView(@NonNull View view) {
        instance.setGoToNavigatorView(view);
    }

    /**
     * @param googleMapsAccess An instance of an object that can open Google Maps application at a given Location
     */
    @Override
    public void setGoogleMapsAccess(@NonNull GoToGoogleMapsNavigator googleMapsAccess) {
        instance.setGoogleMapsAccess(googleMapsAccess);
    }

    /**
     * @param dialog An object of a class that extends AbstractAddEventDialog
     */
    @Override
    public void setAddEventDialog(@NonNull AbstractAddEventDialog dialog) {
        instance.setAddEventDialog(dialog);
    }

    /**
     * @param bottomSheetBehaviour An object of a class that extends BottomSheetBehaviour
     */
    @Override
    public void setBottomSheetBehaviour(@NonNull BottomSheetBehaviour bottomSheetBehaviour) {
        instance.setBottomSheetBehaviour(bottomSheetBehaviour);
    }

    /**
     * @param listener An object that wait for the trigger of an event
     */
    @Override
    public void subscribeOnEventTriggeredListener(OnEventTriggeredListener<Event<String>> listener) {
        instance.subscribeOnEventTriggeredListener(listener);
    }

    /**
     * @param listener An object that wait for the removal of an event
     */
    @Override
    public void subscribeOnEventRemovedListener(OnEventRemovedListener<Event<String>> listener) {
        instance.subscribeOnEventRemovedListener(listener);
    }

    /**
     * @param listener An object that wait for the creation of an event
     */
    @Override
    public void subscribeOnEventCreatedListener(OnEventCreatedListener<Event<String>> listener) {
        instance.subscribeOnEventCreatedListener(listener);
    }

    /**
     * @param listener An object that wait for the trigger of an event
     */
    @Override
    public void unsubscribeOnEventTriggeredListener(OnEventTriggeredListener<Event<String>> listener) {
        instance.unsubscribeOnEventTriggeredListener(listener);
    }

    /**
     * @param listener An object that wait for the removal of an event
     */
    @Override
    public void unsubscribeOnEventRemovedListener(OnEventRemovedListener<Event<String>> listener) {
        instance.unsubscribeOnEventRemovedListener(listener);
    }

    /**
     * @param listener An object that wait for the creation of an event
     */
    @Override
    public void unsubscribeOnEventCreatedListener(OnEventCreatedListener<Event<String>> listener) {
        instance.unsubscribeOnEventCreatedListener(listener);
    }

    /**
     * @param onMapInitializedListener A listener that will be called when the map has been fully initialized
     */
    @Override
    public void setOnMapInitializedListener(OnMapInitializedListener onMapInitializedListener) {
        instance.setOnMapInitializedListener(onMapInitializedListener);
    }

    /**
     * @param left   The number of pixels of padding to be added on the left of the map. (>= 0)
     * @param top    The number of pixels of padding to be added on the top of the map. (>= 0)
     * @param right  The number of pixels of padding to be added on the right of the map. (>= 0)
     * @param bottom The number of pixels of padding to be added on the bottom of the map. (>= 0)
     */
    @Override
    public void setMapPadding(int left, int top, int right, int bottom) {
        instance.setMapPadding(left, top, right, bottom);
    }

    /**
     * @param events A bunch of events to position in the map (both description and Position must be defined)
     */
    @Override
    public void addEventsToMap(List<Event<String>> events) {
        instance.addEventsToMap(events);
    }

    /**
     * @param data The gps position where the map will be focused
     */
    @Override
    public void moveMap(LatLng data) {
        instance.moveMap(data);
    }
}
