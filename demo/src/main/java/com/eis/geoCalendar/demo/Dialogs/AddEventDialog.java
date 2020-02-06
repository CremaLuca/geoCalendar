package com.eis.geoCalendar.demo.Dialogs;

import androidx.fragment.app.DialogFragment;

import com.eis.geoCalendar.demo.Behaviour.ResultEventListener;
import com.google.android.gms.maps.model.LatLng;

/**
 * @author Turcato
 * This class defines a behaviour that a DialogFragment has to implement to define to be used
 * for interacting with the user for adding an Event through MapBehaviour objects
 */
public abstract class AddEventDialog extends DialogFragment {

    /**
     * @param resultEventListener Listener to call when result is available
     */
    public abstract void setResultListener(ResultEventListener resultEventListener);

    /**
     * This method is used to store a gps Position
     *
     * @param latLng The position for an Event that must be created (in this dialog is not canceled)
     */
    public abstract void setEventPosition(LatLng latLng);
}
