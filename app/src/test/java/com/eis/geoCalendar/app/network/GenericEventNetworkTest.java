package com.eis.geoCalendar.app.network;

import android.location.Location;

import com.eis.communication.Peer;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.EventNetworkManager;
import com.eis.geoCalendar.network.NetworkEvent;
import com.eis.geoCalendar.network.NetworkEventUser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author Luca Crema
 * @since 26/12/2019
 */
@RunWith(MockitoJUnitRunner.class)
public class GenericEventNetworkTest {

    private GenericEventNetwork<NetworkEvent<String, NetworkEventUser<Peer<String>>>, Peer<String>> testNetwork;
    @Mock
    private Location mockedLocation1;
    @Mock
    private EventNetworkManager<NetworkEvent<String, NetworkEventUser<Peer<String>>>, Peer<String>> eventNetworkManager;


    @Before
    public void setup() {
        setupMocks();
        testNetwork = new GenericEventNetwork<>(eventNetworkManager);
    }

    private void setupMocks() {
        //when(eventNetworkManager.getResurce(any(GPSPosition)));
    }

    @Test
    public void approximateGPSPosition() {
        GPSPosition p = new GPSPosition(22.12345678f, 15.98765432f);
        GPSPosition approximated = testNetwork.approximateGPSPosition(p);
        GPSPosition result = new GPSPosition(22.123000f, 15.988000f);
        System.out.println("Latitude: " + approximated.getLatitude() + " Longitude: " + approximated.getLongitude());
        Assert.assertEquals(result.getLongitude(), approximated.getLongitude(), 0.0001f);
        Assert.assertEquals(result.getLatitude(), approximated.getLatitude(), 0.0001f);
    }

}