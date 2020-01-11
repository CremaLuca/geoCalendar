package com.eis.geoCalendar.gps;

import android.location.Location;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.easymock.EasyMock.expect;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * GPSPosition tests
 *
 * @author Alberto Ursino
 * @since 12/28/2019
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Location.class)
public class GPSPositionTest {

    private GPSPosition gpsPosition1;
    Location location1 = PowerMockito.mock(Location.class);
    private final double DEF_LATITUDE = 100;
    private final double DEF_LATITUDE_2 = 101;
    private final double DEF_LONGITUDE = 100;
    private final double DEF_LONGITUDE_2 = 101;
    private final double DELTA = 0;
    private final double DEF_PRECISION = 0;
    private final Object GENERIC_OBJ = "ciao";


    @Before
    public void init() {
        //1st constructor
        gpsPosition1 = new GPSPosition(location1);

        PowerMockito.doNothing().when(location1).setLatitude(DEF_LATITUDE);
        PowerMockito.doNothing().when(location1).setLongitude(DEF_LONGITUDE);
        //2nd constructor
        gpsPosition1.updateLocation(DEF_LATITUDE, DEF_LONGITUDE);

        PowerMockito.when(gpsPosition1.getLatitude()).thenReturn(DEF_LATITUDE);
        PowerMockito.when(gpsPosition1.getLongitude()).thenReturn(DEF_LONGITUDE);
    }

    @Test
    public void getLatitude_tests() {
        Assert.assertEquals(DEF_LATITUDE, gpsPosition1.getLatitude(), DELTA);
        Assert.assertNotEquals(DEF_LATITUDE_2, gpsPosition1.getLatitude(), DELTA);
    }

    @Test
    public void getLongitude_tests() {
        Assert.assertEquals(DEF_LONGITUDE, gpsPosition1.getLongitude(), DELTA);
        Assert.assertNotEquals(DEF_LONGITUDE_2, gpsPosition1.getLongitude(), DELTA);
    }

    @Test
    public void equals_tests() {
        Assert.assertFalse(gpsPosition1.equals(null));
        Assert.assertFalse(gpsPosition1.equals(GENERIC_OBJ));
        Assert.assertTrue(gpsPosition1.equals(new GPSPosition(location1), DEF_PRECISION));
        Assert.assertTrue(gpsPosition1.equals(gpsPosition1));
    }

}