package com.eis.geoCalendar.app.network;

import com.eis.communication.Peer;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.network.NetworkEvent;
import com.eis.geoCalendar.network.NetworkUser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Luca Crema
 * @since 26/12/2019
 */
public class GenericEventNetworkTest {

    private GenericEventNetwork<NetworkEvent<String, NetworkUser<Peer<String>>>> testNetwork;

    @Before
    public void setup() {
        testNetwork = new GenericEventNetwork<>();
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