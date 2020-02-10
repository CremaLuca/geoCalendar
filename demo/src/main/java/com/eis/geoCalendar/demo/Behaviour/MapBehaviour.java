package com.eis.geoCalendar.demo.Behaviour;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.eis.geoCalendar.demo.Dialogs.AbstractAddEventDialog;
import com.eis.geoCalendar.demo.Dialogs.AbstractRemoveEventDialog;
import com.eis.geoCalendar.demo.Localization.OnLocationAvailableListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * @author Turcato
 *
 * This interface can be used to implement the variouse GoogleMap's events, without writing all interfaces' names
 *
 * Defining a class that implements this interface allows to divide the application's logic from the UI activity
 */
public interface MapBehaviour extends OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnInfoWindowLongClickListener,
        GoogleMap.OnMarkerClickListener,
        ResultEventListener, RemoveEventListener, OnLocationAvailableListener {

    /**
     *
     * @param supportFragmentManager    Needed to interact with the user through dialogs
     */
    void setSupportFragmentManager(@NonNull FragmentManager supportFragmentManager);

    /**
     * @param locationRetriever An object that implements the LocationRetriever interface
     */
    void setLocationRetriever(@NonNull LocationRetriever locationRetriever);

    /**
     * @param dialog An object of a class that extends AbstractAddEventDialog
     */
    void setAddEventDialog(@NonNull AbstractAddEventDialog dialog);

    /**
     * @param dialog An object of a class that extends AbstractRemoveEventDialog
     */
    void setRemoveEventDialog(@NonNull AbstractRemoveEventDialog dialog);

}
