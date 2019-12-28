package com.eis.geoCalendar.gps;

import android.location.Location;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * GPSPosition tests
 *
 * @author Alberto Ursino
 * @since 12/28/2019
 */
public class GPSPositionTest {

    private GPSPosition gpsPosition1, gpsPosition2;
    private final double DEF_LATITUDE = 100;
    private final double DEF_LATITUDE_2 = 101;
    private final double DEF_LONGITUDE = 100;
    private final double DEF_LONGITUDE_2 = 101;
    private final double DELTA = 0;
    private final double DEF_PRECISION = 0;
    private Location location1 = mock(Location.class);
    private Location location2 = mock(Location.class);
    private final Object GENERIC_OBJ = "ciao";

    @Before
    public void init() {
        when(location1.getLatitude()).thenReturn(DEF_LATITUDE);
        when(location1.getLongitude()).thenReturn(DEF_LONGITUDE);

        when(location2.getLatitude()).thenReturn(DEF_LATITUDE_2);
        when(location2.getLongitude()).thenReturn(DEF_LONGITUDE_2);

        gpsPosition1 = new GPSPosition(location1);
        gpsPosition2 = new GPSPosition(location2);
    }

    @Test
    public void getLatitude_tests() {
        Assert.assertEquals(DEF_LATITUDE, gpsPosition1.getLatitude(), DELTA);
        Assert.assertNotEquals(DEF_LATITUDE, gpsPosition2.getLatitude(), DELTA);
    }

    @Test
    public void getLongitude_tests() {
        Assert.assertEquals(DEF_LONGITUDE, gpsPosition1.getLongitude(), DELTA);
        Assert.assertNotEquals(DEF_LONGITUDE, gpsPosition2.getLongitude(), DELTA);
    }

    @Test
    public void equals_tests() {
        Assert.assertFalse(gpsPosition1.equals(null));
        Assert.assertFalse(gpsPosition1.equals(GENERIC_OBJ));
        Assert.assertTrue(gpsPosition1.equals(new GPSPosition(location1), DEF_PRECISION));
        Assert.assertTrue(gpsPosition1.equals(gpsPosition1));
    }

}