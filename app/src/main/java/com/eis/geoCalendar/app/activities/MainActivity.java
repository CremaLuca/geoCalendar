package com.eis.geoCalendar.app.activities;

import android.os.Bundle;

import com.eis.geoCalendar.R;
import com.eis.geoCalendar.app.GenericTimedEvent;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GenericEventAdapter eventAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<GenericTimedEvent<String>> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        events = new ArrayList<>();
        setupRecyclerView(events);
    }

    private void setupRecyclerView(ArrayList<GenericTimedEvent<String>> events) {
        recyclerView = findViewById(R.id.eventsRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        eventAdapter = new GenericEventAdapter(events);
        recyclerView.setAdapter(eventAdapter);
    }

    public void onAddEventButton() {

    }
}
