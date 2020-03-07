package com.eis.geoCalendar.demo.Behaviour;

import android.view.View;

import com.eis.geoCalendar.demo.Behaviour.listener.RemoveEventListener;
import com.eis.geoCalendar.demo.Behaviour.listener.ResultEventListener;
import com.eis.geoCalendar.demo.Bottomsheet.listener.OnActionViewClickListener;
import com.eis.geoCalendar.demo.Bottomsheet.listener.OnRemoveViewClickListener;
import com.eis.geoCalendar.demo.Localization.OnLocationAvailableListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Interface that defines the callbacks a class needs to implement to be a complete MapBehaviour obj
 * Used alongside {@link MapBehaviour}
 *
 * @author Turcato
 */
public interface MapBehaviourCallbacks extends OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnInfoWindowLongClickListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener,
        ResultEventListener, RemoveEventListener, OnLocationAvailableListener,
        OnActionViewClickListener, OnRemoveViewClickListener, View.OnClickListener {
}
