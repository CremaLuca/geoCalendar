package com.eis.geoCalendar.demo.Behaviour;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.eis.geoCalendar.demo.Localization.OnLocationAvailableListener;
import com.eis.geoCalendar.demo.RemoveEventListener;
import com.eis.geoCalendar.demo.ResultEventListener;
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
        ResultEventListener, RemoveEventListener, OnLocationAvailableListener {

    /**
     *
     * @param supportFragmentManager needed to interact with the user through dialogs
     */
    void getSupportFragmentManager(@NonNull FragmentManager supportFragmentManager);

    void getLocationRetriever(@NonNull LocationRetriever locationRetriever);
}
