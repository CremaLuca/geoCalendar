package com.eis.geoCalendar.demo;

import android.location.Location;

import com.eis.geoCalendar.demo.Localization.Command;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Used to move the Map to the defined Location, with the max possible zoom
 */
public class MoveMapCommand implements Command<Location> {
    private GoogleMap googleMap;

    public MoveMapCommand(GoogleMap map){
        googleMap = map;
    }

    @Override
    public void execute(Location data) {
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(googleMap.getMaxZoomLevel()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(data.getLatitude(), data.getLongitude())));
    }
}
