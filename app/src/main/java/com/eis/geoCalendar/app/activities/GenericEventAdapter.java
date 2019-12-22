package com.eis.geoCalendar.app.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eis.geoCalendar.R;
import com.eis.geoCalendar.events.Event;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collection;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GenericEventAdapter extends RecyclerView.Adapter<GenericEventAdapter.GenericEventViewHolder> {

    private ArrayList<Event> eventsDataset;

    public GenericEventAdapter(Collection<? extends Event> events) {
        this.eventsDataset = new ArrayList<>();
        eventsDataset.addAll(events);
    }

    @NonNull
    @Override
    public GenericEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View eventView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_view, parent, false);
        return new GenericEventViewHolder(eventView);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericEventViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return eventsDataset.size();
    }

    public void addItem(Event event) {
        eventsDataset.add(event);
        notifyDataSetChanged();
    }

    class GenericEventViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        TextView latitudeValueTextView;
        TextView longitudeValueTextView;
        MapView mapView;
        GoogleMap map;
        View layout;
        Context currentContext;

        GenericEventViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            currentContext = itemView.getContext();
            latitudeValueTextView = itemView.findViewById(R.id.latitudeValue);
            longitudeValueTextView = itemView.findViewById(R.id.longitudeValue);
            mapView = itemView.findViewById(R.id.mapView);
            if (mapView != null) {
                // Initialise the MapView
                mapView.onCreate(null);
                // Set the map ready callback to receive the GoogleMap object
                mapView.getMapAsync(this);
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(currentContext);
            map = googleMap;
            setMapLocation();
        }

        /**
         * Displays an {@link Event} on a
         * {@link com.google.android.gms.maps.GoogleMap}.
         * Adds a marker and centers the camera on the NamedLocation with the normal map type.
         */
        private void setMapLocation() {
            if (map == null) return;

            Event data = (Event) mapView.getTag();

            // Add a marker for this item and set the camera
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(data.getPosition().getLatitude(), data.getPosition().getLongitude()), 13f));
            map.addMarker(new MarkerOptions().position(new LatLng(data.getPosition().getLatitude(), data.getPosition().getLongitude())));

            // Set the map type back to normal.
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

        private void bindView(int position) {

            latitudeValueTextView.setText(eventsDataset.get(position).getPosition().getLatitude() + "");
            longitudeValueTextView.setText(eventsDataset.get(position).getPosition().getLongitude() + "");
            // Store a reference of the ViewHolder object in the layout.
            layout.setTag(this);
            // Store a reference to the item in the mapView's tag. We use it to get the
            // coordinate of a location, when setting the map location.
            mapView.setTag(eventsDataset.get(position));
            setMapLocation();
        }
    }


}
